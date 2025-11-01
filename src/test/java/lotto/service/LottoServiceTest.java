package lotto.service;

import lotto.domain.Lotto;
import lotto.domain.LottoCollection;
import lotto.domain.LottoRank;
import lotto.validate.Validators;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static lotto.domain.LottoRank.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("LottoService 단위 테스트")
class LottoServiceTest {

    LottoCollection purchasedLottos;
    Validators validator;
    LottoService service;

    @BeforeEach
    void setUp() {
        validator = new Validators();
        service = new LottoService(validator);
    }

    @Nested
    @DisplayName("생성 및 파싱 테스트 (깨끗한 상태에서 실행)")
    class CreationAndParsingTests {

        @ParameterizedTest
        @ValueSource(ints = {1, 2, 3, 8})
        @DisplayName("요청한 수량만큼 로또를 생성한다")
        void makeLottoNumbers(int input) {
            // when
            service.makeLottoNumbers(input);

            // then
            assertThat(service.getAllLottos().size()).isEqualTo(input);
        }

        @ParameterizedTest
        @ValueSource(strings = {"1,2,3,4,5,6", "11,22,33,44,5,6"})
        @DisplayName("쉼표로 구분된 문자열을 Set<Integer>로 파싱한다")
        void parseNumbersInput(String input) {
            // when
            Set<Integer> numbers = service.parseNumbersInput(input);

            // then
            assertThat(numbers.size()).isEqualTo(6);
        }
    }

    @Nested
    @DisplayName("보상 계산 테스트 (데이터가 미리 필요한 경우)")
    class RewardTests {

        private Map<LottoRank, Integer> lottoResult;
        private final int PURCHASE_AMOUNT = 8000; // 8개 * 1000원

        @BeforeEach
        void setupRewardData() {
            // given
            List<Lotto> testLottos = List.of(
                    new Lotto(List.of(1, 2, 3, 4, 5, 6)), // 1등
                    new Lotto(List.of(1, 2, 3, 4, 5, 7)), // 2등
                    new Lotto(List.of(1, 2, 3, 4, 5, 8)), // 3등
                    new Lotto(List.of(1, 2, 3, 4, 8, 9)), // 4등
                    new Lotto(List.of(1, 2, 3, 8, 9, 10)), // 5등
                    new Lotto(List.of(1, 2, 8, 9, 10, 11)), // 꽝
                    new Lotto(List.of(1, 2, 8, 9, 10, 11)), // 꽝
                    new Lotto(List.of(1, 2, 8, 9, 10, 11))  // 꽝
            );
            LottoCollection fixedLottos = new LottoCollection(testLottos);

            service.setPurchasedLottos(fixedLottos);

            // when
            Set<Integer> userLottoNumbers = Set.of(1, 2, 3, 4, 5, 6);
            String bonus = "7";
            this.lottoResult = service.calcReward(userLottoNumbers, bonus);
        }

        @Test
        @DisplayName("통계 계산을 올바르게 수행한다")
        void calcReward() {
            // then
            assertThat(lottoResult.get(FIRST)).isEqualTo(1);
            assertThat(lottoResult.get(SECOND)).isEqualTo(1);
            assertThat(lottoResult.get(THIRD)).isEqualTo(1);
            assertThat(lottoResult.get(FOURTH)).isEqualTo(1);
            assertThat(lottoResult.get(FIFTH)).isEqualTo(1);
            assertThat(lottoResult.get(MISS)).isEqualTo(3);
        }

        @Test
        @DisplayName("수익률 계산을 올바르게 수행한다")
        void calculateRateOfReturn() {
            // given
            // 총 8개를 구매했으므로 purchaseAmount는 8000

            // (1등~5등 총 상금)
            long totalPrize = 2_000_000_000L + 30_000_000 + 1_500_000 + 50_000 + 5_000; // 2,031,555,000
            double expectedRate = (double) totalPrize / PURCHASE_AMOUNT * 100.0;

            // when
            double rateOfReturn = service.calculateRateOfReturn(lottoResult, PURCHASE_AMOUNT);

            // then
            assertThat(rateOfReturn).isEqualTo(expectedRate);
        }
    }
}
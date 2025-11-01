package lotto.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LottoCollectionTest {

    LottoCollection lottos = new LottoCollection(List.of());

    static Stream<Arguments> provideWinningNumberWithBonusArguments() {
        return Stream.of(
                // 1. 당첨 번호, 2. 보너스
                Arguments.of( Set.of(1, 2, 3, 4, 5, 6), 7)
        );
    }

    static Stream<Arguments> provideRateOfReturnArguments() {
        // 5등 1개 (상금 5,000)
        Map<LottoRank, Integer> case1 = new EnumMap<>(LottoRank.class);
        case1.put(LottoRank.FIFTH, 1);

        // 4등 1개 (50,000), 5등 2개 (10,000) -> 총 상금 60,000
        Map<LottoRank, Integer> case2 = new EnumMap<>(LottoRank.class);
        case2.put(LottoRank.FOURTH, 1);
        case2.put(LottoRank.FIFTH, 2);

        // 꽝 (상금 0)
        Map<LottoRank, Integer> case3 = new EnumMap<>(LottoRank.class);
        case3.put(LottoRank.MISS, 8);

        return Stream.of(
                // 1. 결과 Map, 2. 구매 금액, 3. 예상 수익률
                // (5,000 / 1,000) * 100 = 500.0%
                Arguments.of(case1, 1000, 500.0),

                // (60,000 / 8,000) * 100 = 750.0%
                Arguments.of(case2, 8000, 750.0),

                // (0 / 8,000) * 100 = 0.0%
                Arguments.of(case3, 8000, 0.0),

                // 구매 금액 0원일 때 (엣지 케이스)
                Arguments.of(case1, 0, 0.0)
        );
    }

    @BeforeEach
    void setUp() {
        Lotto lotto1 = new Lotto(List.of(1,2,3,4,5,6)); // 1등
        Lotto lotto2 = new Lotto(List.of(1,2,3,4,5,7)); // 2등
        Lotto lotto3 = new Lotto(List.of(1,2,3,4,5,9)); // 3등
        Lotto lotto4 = new Lotto(List.of(1,2,3,4,9,15)); // 4등
        Lotto lotto5 = new Lotto(List.of(1,2,3,8,9,15)); // 5등
        Lotto lotto6 = new Lotto(List.of(1,2,7,8,9,15)); // 꽝

        lottos.add(lotto1);
        lottos.add(lotto2);
        lottos.add(lotto3);
        lottos.add(lotto4);
        lottos.add(lotto5);
        lottos.add(lotto6);
    }

    @AfterEach
    void tearDown() {
        lottos.clear();
    }


    @Test
    @DisplayName("로또들이 잘 불러와지는가")
    void getLottos() {
        // when & then
        assertThat(lottos.getLottos().size()).isEqualTo(6);
    }

    @Test
    @DisplayName("로또가 추가되면 리스트 사이즈도 올라야한다")
    void createLottos() {
        // given
        Lotto lotto = new Lotto(List.of(1,2,3,4,5,6));

        // when
        lottos.add(lotto);

        // then
        assertThat(lottos.getLottos().size()).isEqualTo(7);
    }

    @ParameterizedTest
    @MethodSource("lotto.domain.LottoCollectionTest#provideWinningNumberWithBonusArguments")
    @DisplayName("당첨 번호와 내가 산 로또 번호를 비교하여 각각의 당첨등수 개수를 계산")
    void calculateResults(Set<Integer> winningNumbers
            ,int bonus) {
        // when
        Map<LottoRank, Integer> expectedRanks = lottos.calculateResults(winningNumbers, bonus);

        // then
        assertThat(expectedRanks.get(LottoRank.FIRST)).isEqualTo(1);
        assertThat(expectedRanks.get(LottoRank.SECOND)).isEqualTo(1);
        assertThat(expectedRanks.get(LottoRank.THIRD)).isEqualTo(1);
        assertThat(expectedRanks.get(LottoRank.FOURTH)).isEqualTo(1);
        assertThat(expectedRanks.get(LottoRank.FIFTH)).isEqualTo(1);
        assertThat(expectedRanks.get(LottoRank.MISS)).isEqualTo(1);
    }

    @ParameterizedTest
    @MethodSource("provideRateOfReturnArguments")
    @DisplayName("수익률을 정확하게 계산한다")
    void calculateRateOfReturn(Map<LottoRank, Integer> results, int purchaseAmount, double expectedRate) {
        // when
        double actualRate = lottos.calculateRateOfReturn(results, purchaseAmount);

        // then
        assertThat(actualRate).isEqualTo(expectedRate);
    }

}
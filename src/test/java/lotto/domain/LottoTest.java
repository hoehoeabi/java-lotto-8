package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LottoTest {

    static Stream<Arguments> provideLottoRankArguments() {
        return Stream.of(
                // 1. 내 로또 번호, 2. 당첨 번호, 3. 보너스, 4. 예상 등수
                Arguments.of(List.of(1, 2, 3, 4, 5, 6), Set.of(1, 2, 3, 4, 5, 6), 7, LottoRank.FIRST),
                Arguments.of(List.of(1, 2, 3, 4, 5, 7), Set.of(1, 2, 3, 4, 5, 6), 7, LottoRank.SECOND),
                Arguments.of(List.of(1, 2, 3, 4, 5, 8), Set.of(1, 2, 3, 4, 5, 6), 7, LottoRank.THIRD),
                Arguments.of(List.of(1, 2, 3, 4, 8, 9), Set.of(1, 2, 3, 4, 5, 6), 7, LottoRank.FOURTH),
                Arguments.of(List.of(1, 2, 3, 8, 9, 10), Set.of(1, 2, 3, 4, 5, 6), 7, LottoRank.FIFTH),
                Arguments.of(List.of(1, 2, 8, 9, 10, 11), Set.of(1, 2, 3, 4, 5, 6), 7, LottoRank.MISS)
        );
    }

    static Stream<Arguments> bonusNumberArgumentsWillReturnTrue() {
        return Stream.of(
                Arguments.of(List.of(1,2,3,4,5,6),1),
                Arguments.of(List.of(1,2,3,4,5,6),2),
                Arguments.of(List.of(1,2,3,4,5,6),3),
                Arguments.of(List.of(1,2,3,4,5,6),4),
                Arguments.of(List.of(1,2,3,4,5,6),5),
                Arguments.of(List.of(1,2,3,4,5,6),6)
        );
    }

    static Stream<Arguments> bonusNumberArgumentsWillReturnFalse() {
        return Stream.of(
                Arguments.of(List.of(1,2,3,4,5,6),11),
                Arguments.of(List.of(1,2,3,4,5,6),22),
                Arguments.of(List.of(1,2,3,4,5,6),33),
                Arguments.of(List.of(1,2,3,4,5,6),44),
                Arguments.of(List.of(1,2,3,4,5,6),45),
                Arguments.of(List.of(1,2,3,4,5,6),36)
        );
    }

    @Nested
    @DisplayName("로또 생성 테스트")
    class createLotto {

        @Test
        void 로또_번호의_개수가_6개가_넘어가면_예외가_발생한다() {
            assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 로또_번호에_중복된_숫자가_있으면_예외가_발생한다() {
            assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 로또_번호에_경계를_벗어난_숫자가_있으면_예외가_발생한다() {
            assertThatThrownBy(() -> new Lotto(List.of(0, 2, 3, 4, 5, 46)))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 로또_번호의_개수가_6개가_안되면_예외가_발생한다() {
            assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5)))
                    .isInstanceOf(IllegalArgumentException.class);
        }

    }

    @Nested
    @DisplayName("보너스번호 확인 테스트")
    class checkBonus {

        @DisplayName("보너스 번호가 로또번호 안에 있으면 true를 반환한다.")
        @ParameterizedTest
        @MethodSource("lotto.domain.LottoTest#bonusNumberArgumentsWillReturnTrue")
        void Lotto_contains_true(List<Integer> lottoNumbers,int bonus) {
            Lotto lotto = new Lotto(lottoNumbers);
            assertThat(lotto.contains(bonus)).isTrue();
        }

        @DisplayName("보너스 번호가 로또번호 안에 없으면 false를 반환한다.")
        @ParameterizedTest
        @MethodSource("lotto.domain.LottoTest#bonusNumberArgumentsWillReturnFalse")
        void Lotto_contains_false(List<Integer> lottoNumbers,int bonus) {
            Lotto lotto = new Lotto(lottoNumbers);
            assertThat(lotto.contains(bonus)).isFalse();
        }
    }


    @ParameterizedTest
    @MethodSource("lotto.domain.LottoTest#provideLottoRankArguments")
    @DisplayName("로또 등수 계산")
    void determineRank(List<Integer> lottoNumbers
            , Set<Integer> winningNumbers
            ,int bonus
            ,LottoRank expectedRank) {
        Lotto lotto = new Lotto(lottoNumbers);
        assertThat(lotto.determineRank(winningNumbers, bonus)).isEqualTo(expectedRank);
    }

}

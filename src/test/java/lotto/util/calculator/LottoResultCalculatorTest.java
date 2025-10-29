package lotto.util.calculator;

import lotto.domain.Lotto;
import lotto.domain.LottoRank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LottoResultCalculatorTest {

    List<Lotto> purchasedLottos = new ArrayList<>();
    Set<Integer> winningNumbers = new HashSet<>();

    @BeforeEach
    void setUp() {
        purchasedLottos.clear();
        winningNumbers.clear();
    }

    @Test
    void calculateResults() {
        // given
        purchasedLottos.add(new Lotto(List.of(1, 2, 3, 4, 5, 6))); // 1
        purchasedLottos.add(new Lotto(List.of(1, 2, 3, 4, 5, 7))); // 2
        purchasedLottos.add(new Lotto(List.of(1, 2, 3, 4, 5, 8))); // 3
        purchasedLottos.add(new Lotto(List.of(1, 2, 3, 4, 5, 9))); // 3
        purchasedLottos.add(new Lotto(List.of(1, 2, 3, 4, 8, 9))); // 4
        purchasedLottos.add(new Lotto(List.of(1, 2, 3, 8, 9, 11))); // 5
        purchasedLottos.add(new Lotto(List.of(11, 22, 33, 4, 8, 9))); // 6

        winningNumbers.add(1);
        winningNumbers.add(2);
        winningNumbers.add(3);
        winningNumbers.add(4);
        winningNumbers.add(5);
        winningNumbers.add(6);

        int bonusNumber = 7;

        // when
        Map<LottoRank, Integer> results = LottoResultCalculator
                .calculateResults(purchasedLottos, winningNumbers, bonusNumber);

        // then
        assertThat(results.get(LottoRank.FIRST)).isEqualTo(1);
        assertThat(results.get(LottoRank.SECOND)).isEqualTo(1);
        assertThat(results.get(LottoRank.THIRD)).isEqualTo(2);
        assertThat(results.get(LottoRank.FOURTH)).isEqualTo(1);
        assertThat(results.get(LottoRank.FIFTH)).isEqualTo(1);
        assertThat(results.get(LottoRank.MISS)).isEqualTo(0);
    }

}
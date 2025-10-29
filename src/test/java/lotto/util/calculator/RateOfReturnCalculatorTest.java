package lotto.util.calculator;

import lotto.domain.LottoRank;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RateOfReturnCalculatorTest {

    Map<LottoRank, Integer> results;
    int purchaseAmount;

    @Test
    void calculate() {
        // given
        results = new EnumMap<>(LottoRank.class);
        results.put(LottoRank.FIRST, 0);
        results.put(LottoRank.SECOND, 0);
        results.put(LottoRank.THIRD, 0);
        results.put(LottoRank.FOURTH, 0);
        results.put(LottoRank.FIFTH, 1);

        purchaseAmount = 2000;

        // when
        double result = RateOfReturnCalculator.calculate(results, purchaseAmount);

        // then
        assertThat(result).isEqualTo(250.0);
    }
}
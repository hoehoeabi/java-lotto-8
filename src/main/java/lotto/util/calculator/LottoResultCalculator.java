package lotto.util.calculator;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lotto.domain.Lotto;
import lotto.domain.LottoRank;

public final class LottoResultCalculator {

    private LottoResultCalculator() {
    }

    public static Map<LottoRank, Integer> calculateResults(
            List<Lotto> purchasedLottos, Set<Integer> winningNumbers, int bonusNumber) {

        Map<LottoRank, Integer> results = new EnumMap<>(LottoRank.class);
        initResultsMap(results);

        for (Lotto lotto : purchasedLottos) {
            calculateHowManyCorrect(winningNumbers, lotto, bonusNumber, results);
        }

        return results;
    }

    private static void initResultsMap(Map<LottoRank, Integer> results) {
        for (LottoRank rank : LottoRank.values()) {
            results.put(rank, 0);
        }
    }

    private static void calculateHowManyCorrect(
            Set<Integer> winningNumbers, Lotto lotto, int bonus, Map<LottoRank, Integer> results) {

        int matchCount = (int) lotto.getNumbers()
                .stream()
                .filter(winningNumbers::contains)
                .count();

        boolean hasBonus = lotto.contains(bonus);
        LottoRank rank = LottoRank.valueOf(matchCount, hasBonus);

        if (rank != LottoRank.MISS) {
            results.put(rank, results.get(rank) + 1);
        }
    }
}
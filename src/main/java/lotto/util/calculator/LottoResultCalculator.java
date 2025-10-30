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

        boolean isBonus;
        int matches;
        Map<LottoRank, Integer> results = new EnumMap<>(LottoRank.class);

        initResultsMap(results);

        for (Lotto lotto : purchasedLottos) {
            isBonus = hasBonus(lotto,bonusNumber);
            matches = howManyMatches(lotto,winningNumbers);
            calculateLottoRank(isBonus,matches,results);
        }

        return results;
    }

    private static void initResultsMap(Map<LottoRank, Integer> results) {
        for (LottoRank rank : LottoRank.values()) {
            results.put(rank, 0);
        }
    }

    private static boolean hasBonus(Lotto lotto, int bonusNumber) {
        return lotto.contains(bonusNumber);
    }


    private static int howManyMatches(Lotto lotto, Set<Integer> winningNumbers) {
        return (int) lotto.getNumbers()
                .stream()
                .filter(winningNumbers::contains)
                .count();
    }

    private static void calculateLottoRank(boolean isBonus
            , int matches
            , Map<LottoRank, Integer> results) {

        LottoRank rank = LottoRank.valueOf(matches, isBonus);

        if (rank != LottoRank.MISS) {
            results.put(rank, results.get(rank) + 1);
        }
    }

}
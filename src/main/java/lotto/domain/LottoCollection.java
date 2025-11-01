package lotto.domain;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.*;

public class LottoCollection {

    private final List<Lotto> lottos;

    public LottoCollection(List<Lotto> lottos) {
        this.lottos = new ArrayList<Lotto>(lottos);
    }

    public void add(Lotto lotto) {
        lottos.add(lotto);
    }

    public List<Lotto> getLottos() {
        return Collections.unmodifiableList(lottos);
    }

    public static LottoCollection createLottos(int quantity) {
        List<Lotto> generatedLottos = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            generatedLottos.add(makeLotto());
        }

        return new LottoCollection(generatedLottos);
    }

    private static Lotto makeLotto() {
        List<Integer> numbers = Randoms
                .pickUniqueNumbersInRange(1, 45, 6);
        List<Integer> mutableNumbers = new ArrayList<>(numbers);
        Collections.sort(mutableNumbers);

        return new Lotto(mutableNumbers);
    }

    public void clear() {
        lottos.clear();
    }

    public  Map<LottoRank, Integer> calculateResults(Set<Integer> winningNumbers, int bonusNumber) {

        boolean isBonus;
        int matches;
        Map<LottoRank, Integer> results = new EnumMap<>(LottoRank.class);

        initResultsMap(results);

        for (Lotto lotto : lottos) {
            isBonus = hasBonus(lotto,bonusNumber);
            matches = howManyMatches(lotto,winningNumbers);
            calculateAndPutLottoRank(isBonus,matches,results);
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

    private static void calculateAndPutLottoRank(boolean isBonus
            , int matches
            , Map<LottoRank, Integer> results) {

        LottoRank rank = LottoRank.valueOf(matches, isBonus);

        if (rank != LottoRank.MISS) {
            results.put(rank, results.get(rank) + 1);
        }
    }

    public double calculateRateOfReturn(Map<LottoRank, Integer> results, int purchaseAmount) {
        long totalPrize = results.entrySet().stream()
                .mapToLong(entry ->
                        (long) entry.getKey().getPrizeMoney() * entry.getValue())
                .sum();
        if (purchaseAmount == 0) {
            return 0.0;
        }
        return (double) totalPrize / purchaseAmount * 100.0;
    }
}

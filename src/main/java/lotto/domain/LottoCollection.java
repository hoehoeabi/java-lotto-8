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

    public Map<LottoRank, Integer> calculateResults(Set<Integer> winningNumbers, int bonusNumber) {

        Map<LottoRank, Integer> results = new EnumMap<>(LottoRank.class);

        initResultsMap(results);

        for (Lotto lotto : lottos) {
            LottoRank rank = lotto.determineRank(winningNumbers,bonusNumber);
            results.put(rank, results.get(rank) + 1);
        }

        return results;
    }

    private static void initResultsMap(Map<LottoRank, Integer> results) {
        for (LottoRank rank : LottoRank.values()) {
            results.put(rank, 0);
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

    public void clear() {
        lottos.clear();
    }
}

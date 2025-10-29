package lotto.util.calculator;

import java.util.Map;
import lotto.domain.LottoRank;

public final class RateOfReturnCalculator {

    private RateOfReturnCalculator() {
        // 유틸 클래스는 인스턴스화 방지
    }

    /**
     * 총 수익률을 계산합니다.
     * @param results 통계 결과 Map
     * @param purchaseAmount 총 구매 금액
     * @return 수익률 (e.g., 62.5)
     */
    public static double calculate(Map<LottoRank, Integer> results, int purchaseAmount) {
        long totalPrize = results.entrySet().stream()
                .mapToLong(entry ->
                        (long) entry.getKey().getPrizeMoney() * entry.getValue())
                .sum();

        // 0으로 나누는 경우 방지 (선택 사항)
        if (purchaseAmount == 0) {
            return 0.0;
        }

        return (double) totalPrize / purchaseAmount * 100.0;
    }
}

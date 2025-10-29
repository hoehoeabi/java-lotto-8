package lotto.domain;

import java.util.Arrays;

public enum LottoRank {
    FIRST(6, 2_000_000_000), // 6개 일치
    SECOND(5, 30_000_000), // 5개 + 보너스
    THIRD(5, 1_500_000),  // 5개 일치
    FOURTH(4, 50_000),   // 4개 일치
    FIFTH(3, 5_000),    // 3개 일치
    MISS(0, 0);          // 꽝

    private final int matchCount;
    private final int prizeMoney;

    LottoRank(int matchCount, int prizeMoney) {
        this.matchCount = matchCount;
        this.prizeMoney = prizeMoney;
    }

    /**
     * 일치 개수와 보너스 여부를 받아 적절한 LottoRank를 반환합니다.
     */
    public static LottoRank valueOf(int matchCount, boolean hasBonus) {
        // 6개 일치
        if (matchCount == FIRST.matchCount) {
            return FIRST;
        }

        // 5개 일치
        if (matchCount == SECOND.matchCount) {
            return hasBonus ? SECOND : THIRD;
        }

        // 3, 4개 일치 (MISS는 0, 1, 2)
        return Arrays.stream(values())
                .filter(rank ->
                        rank.matchCount == matchCount
                                && rank != SECOND)
                .findFirst()
                .orElse(MISS);
    }

    // --- View에서 사용할 Getter ---
    public int getPrizeMoney() {
        return prizeMoney;
    }

    public int getMatchCount() {
        return matchCount;
    }
}

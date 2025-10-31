package lotto.message;

public final class OutputMessage {

    private OutputMessage() {
        throw new IllegalStateException("Utility class");
    }

    public static final String PURCHASE_COUNT_FORMAT = "\n%d개를 구매했습니다.";
    public static final String STATISTICS_HEADER = "\n당첨 통계\n---";
    public static final String STATISTICS_RESULT_FORMAT = "%d개 일치 (%s원) - %d개\n";
    public static final String STATISTICS_BONUS_FORMAT = "%d개 일치, 보너스 볼 일치 (%s원) - %d개\n";
    public static final String RATE_OF_RETURN_FORMAT = "총 수익률은 %.1f%%입니다.";

}

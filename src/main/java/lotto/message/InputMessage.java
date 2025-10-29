package lotto.message;

public final class InputMessage {
    private InputMessage() {
        throw new IllegalStateException("Utility class");
    }
    public static final String PROMPT_PURCHASE_AMOUNT = "구입금액을 입력해 주세요.";
    public static final String PROMPT_WINNING_NUMBERS = "당첨 번호를 입력해 주세요.";
    public static final String PROMPT_BONUS_NUMBER = "보너스 번호를 입력해 주세요.";
}

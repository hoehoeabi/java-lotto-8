package lotto.message;

import lotto.config.LottoConstants;

public final class ErrorMessage {

    private static LottoConstants GameConstants;

    private ErrorMessage() {
        throw new IllegalStateException("Utility class");
    }

    private static final String ERROR_PREFIX = "[ERROR] ";

    public static final String INVALID_PURCHASE_AMOUNT_UNIT = ERROR_PREFIX + "구입 금액은 " + GameConstants.LOTTO_PRICE + "원 단위여야 합니다.";
    public static final String INVALID_NUMBER_FORMAT = ERROR_PREFIX + "숫자를 입력해야 합니다.";
    public static final String INVALID_LOTTO_NUMERS_FORMART = ERROR_PREFIX + "양식은 숫자사이에 ,만 와야하며 공백은 불가합니다.";
    public static final String DUPLICATE_LOTTO_NUMBERS = ERROR_PREFIX + "로또 번호는 중복될 수 없습니다.";
    public static final String LOTTO_NUMBER_BOUNDRY_EXCEPTION = ERROR_PREFIX + "숫자는 1~45만 가능합니다";
    public static final String LOTTO_NUMBER_MAX_COUNT_EXCEPTION = ERROR_PREFIX + "로또 번호는 6개여야 합니다.";

}

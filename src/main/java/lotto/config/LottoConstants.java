package lotto.config;

import java.util.regex.Pattern;

public final class LottoConstants {

    private LottoConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final int LOTTO_PRICE = 1_000;
    public static final int LOTTO_NUMBER_COUNT = 6;
    public static final int MIN_LOTTO_NUMBER = 1;
    public static final int MAX_LOTTO_NUMBER = 45;
    public static final String USER_LOTTO_NUMBERS_REGEX = "^(\\d+,){5}\\d+$";

    public static final Pattern USER_LOTTO_NUMBERS_PATTERN = Pattern.compile(USER_LOTTO_NUMBERS_REGEX);
}

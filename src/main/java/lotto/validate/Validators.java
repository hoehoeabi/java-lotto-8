package lotto.validate;

import lotto.message.ErrorMessage;

import static lotto.config.LottoConstants.*;

public class Validators {

    public int validateAndParseNumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_NUMBER_FORMAT);
        }
    }

    public int validateAndGetQuantity(int parsedAmount) {
        if (parsedAmount % 1000 != 0){
            throw new IllegalArgumentException(ErrorMessage.INVALID_PURCHASE_AMOUNT_UNIT);
        }
        return parsedAmount / 1000;
    }

    public void validateNumbersInput(String userLottoNumberInput) {
        if(!USER_LOTTO_NUMBERS_PATTERN.matcher(userLottoNumberInput).matches()){
            throw new IllegalArgumentException(ErrorMessage.INVALID_LOTTO_NUMERS_FORMART);
        }
    }

}

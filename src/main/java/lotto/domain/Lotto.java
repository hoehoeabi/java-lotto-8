package lotto.domain;

import lotto.message.ErrorMessage;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static lotto.config.LottoConstants.*;
import static lotto.message.ErrorMessage.DUPLICATE_LOTTO_NUMBERS;
import static lotto.message.ErrorMessage.LOTTO_NUMBER_MAX_COUNT_EXCEPTION;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != LOTTO_NUMBER_COUNT) {
            throw new IllegalArgumentException(LOTTO_NUMBER_MAX_COUNT_EXCEPTION);
        }

        Set<Integer> uniqueNumbers = new HashSet<>(numbers);
        if (uniqueNumbers.size() != LOTTO_NUMBER_COUNT) {
            throw new IllegalArgumentException(DUPLICATE_LOTTO_NUMBERS);
        }

        if(numbers.getLast() > MAX_LOTTO_NUMBER
                || numbers.getFirst() < MIN_LOTTO_NUMBER){
            throw new IllegalArgumentException(ErrorMessage.LOTTO_NUMBER_BOUNDRY_EXCEPTION);
        }
    }

    // TODO: 추가 기능 구현
    public List<Integer> getNumbers(){
        return Collections.unmodifiableList(this.numbers);
    }

    public boolean contains(Integer number) {
        return numbers.contains(number);
    }
}

package lotto.domain;

import java.util.Collections;
import java.util.List;

import static lotto.config.LottoConstants.LOTTO_NUMBER_COUNT;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != LOTTO_NUMBER_COUNT) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
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

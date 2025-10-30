package lotto.validate;

import lotto.message.ErrorMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("Validators 단위 테스트")
class ValidatorsTest {

    private static Validators validator;

    @BeforeAll
    static void initAll() {
        validator = new Validators();
    }

    @Nested
    @DisplayName("성공 케이스")
    class Success {

        @ParameterizedTest
        @ValueSource(strings = {"1", "24", "1000","2400"})
        @DisplayName("유효한 숫자 문자열은 해당 숫자를 반환한다")
        void validateAndParseNumber(String input) {
            // given
            int expected = Integer.parseInt(input);

            // when (실행)
            int actual = validator.validateAndParseNumber(input);

            // then (검증)
            assertThat(actual).isEqualTo(expected);
        }

        @ParameterizedTest
        @ValueSource(ints = {1000, 2000, 10000,30000})
        @DisplayName("1000의 배수면 1000으로 나눈 몫을 반환한다.")
        void validateAndGetQuantity(int input) {
            int expected = input/1000;
            int actual;
            // when
            actual = validator.validateAndGetQuantity(input);

            // then
            assertThat(expected).isEqualTo(actual);
        }

        @ParameterizedTest
        @ValueSource(strings = {"1,2,3,4,5,6","78,5,823,7456,34,11"})
        @DisplayName("입력 정규식은 숫자 여섯개와 ,다섯개이며 숫자,숫자,...숫자 형태다")
        void validateNumbersInput(String input) {
            // when & then
            assertDoesNotThrow(() -> validator.validateNumbersInput(input));
        }

        @ParameterizedTest
        @ValueSource(ints = {1, 2, 3,4})
        @DisplayName("보너스 숫자와 로또 당첨번호 사이에 중복은 없다")
        void validateBonusDuplication(int bonus){
            // given
            Set<Integer> lottoNumbers = new HashSet<>(List.of(5,6,7,8,9,10));

            // when
            validator.validateBonusDuplication(bonus,lottoNumbers);

            // then
            assertDoesNotThrow(() -> validator.validateBonusDuplication(bonus,lottoNumbers));

        }



    }

    @Nested
    @DisplayName("실패 케이스")
    class Failure {

        @ParameterizedTest
        @ValueSource(strings = {"a", " ", "", "1.5", "1,2", "숫자아님"})
        @DisplayName("숫자 형식이 아니면 IllegalArgumentException을 던진다")
        void validateFormatFailure(String input) {
            // when & then
            assertThatThrownBy(() -> validator.validateAndParseNumber(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ErrorMessage.INVALID_NUMBER_FORMAT);
        }

        @ParameterizedTest
        @ValueSource(ints = {1,10,100,1001,10010})
        @DisplayName(("1000의 배수가 아니면 예외를 반환한다."))
        void validateAndGetQuantity(int input) {
            // when & then
            assertThatThrownBy(() -> validator.validateAndGetQuantity(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ErrorMessage.INVALID_PURCHASE_AMOUNT_UNIT);
        }

        @ParameterizedTest
        @ValueSource(strings = {"1,2,3,4,5,6,","1,2,3,4,5",",1,2,3,4,5,6","1,2,3,4,5,6,7"})
        @DisplayName("입력 정규식과 다르다")
        void validateNumbersInput(String input) {
            // when & then
            assertThatThrownBy(()-> validator.validateNumbersInput(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ErrorMessage.INVALID_LOTTO_NUMERS_FORMART);
        }

        @ParameterizedTest
        @ValueSource(ints = {1, 2, 3,4})
        @DisplayName("보너스 숫자와 로또 당첨번호 사이에 중복이 있다")
        void validateBonusDuplication(int bonus){
            // given
            Set<Integer> lottoNumbers = new HashSet<>(List.of(1,2,3,4,5,6));

            // when & then
            assertThatThrownBy(()-> validator.validateBonusDuplication(bonus,lottoNumbers))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ErrorMessage.BONUS_NUMBER_CONTAINS_IN_LOTTO);

        }

    }

}
package lotto.service;

import lotto.domain.Lotto;
import lotto.domain.LottoRank;
import lotto.domain.LottoCollection;
import lotto.validate.Validators;

import java.util.*;
import java.util.stream.Collectors;


public class LottoService {

    private final Validators validator;
    private LottoCollection purchasedLottos;

    public LottoService(Validators validator) {
        this.validator = validator;
    }

    public int calcQuantity(String amount) {
        int parsedAmount = validator.validateAndParseNumber(amount);
        validator.validateIsZero(parsedAmount);
        return validator.validateAndGetQuantity(parsedAmount);
    }

    public void makeLottoNumbers(int quantity) {
        this.purchasedLottos = LottoCollection.createLottos(quantity);
    }

    public List<Lotto> getAllLottos() {
        return purchasedLottos.getLottos();
    }

    public Set<Integer> parseNumbersInput(String lottoNumberInput) {
        validator.validateNumbersInput(lottoNumberInput);

        List<String> numberStrings = List.of(lottoNumberInput.split(","));

        return numberStrings.stream()
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }

    public Map<LottoRank, Integer> calcReward(Set<Integer> lottoNumber, String bounusNumber) {
        int bonus = validator.validateAndParseNumber(bounusNumber);
        validator.validateIsZero(bonus);
        validator.validateBonusDuplication(bonus,lottoNumber);

        return purchasedLottos.calculateResults(lottoNumber, bonus);
    }

    public double calculateRateOfReturn(Map<LottoRank, Integer> results, int purchaseAmount) {

        return purchasedLottos.calculateRateOfReturn(results, purchaseAmount);
    }

    // 테스트를 위한 setter 메서드
    // Mockito 없이 서비스 단위테스트 하려니까 너무 힘드네요.
    // 다른분들은 어떻게 하셨는지 참 궁금합니다.
    // 더 나은 방법이 있었다면 리뷰때 적어주시면 감사하겠습니다!
    public void setPurchasedLottos(LottoCollection purchasedLottos) {
        this.purchasedLottos = purchasedLottos;
    }

}

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

}

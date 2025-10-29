package lotto.service;

import camp.nextstep.edu.missionutils.Randoms;
import lotto.domain.Lotto;
import lotto.domain.LottoRank;
import lotto.repository.LottoRepository;
import lotto.util.calculator.LottoResultCalculator;
import lotto.util.calculator.RateOfReturnCalculator;
import lotto.validate.Validators;

import java.util.*;
import java.util.stream.Collectors;

public class LottoService {

    private final Validators validator;
    private final LottoRepository repository;

    public LottoService(Validators validator, LottoRepository repository) {
        this.validator = validator;
        this.repository = repository;
    }

    public int calcQuantity(String amount) {
        int parsedAmount = validator.validateAndParseNumber(amount);
        return validator.validateAndGetQuantity(parsedAmount);
    }

    public void makeLottoNumbers(int quantity) {
        for(int i = 0; i < quantity; i++) {
            repository.add(makeLotto());
        }
    }

    private Lotto makeLotto(){
        List<Integer> numbers = Randoms
                .pickUniqueNumbersInRange(
                        1, 45, 6);

        List<Integer> mutableNumbers = new ArrayList<>(numbers);

        Collections.sort(mutableNumbers);

        return new Lotto(mutableNumbers);
    }

    public List<Lotto> getAllLottos() {
        return repository.getLottos();
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
        List<Lotto> lottos = repository.getLottos();

        return LottoResultCalculator.calculateResults(lottos, lottoNumber, bonus);
    }

    public double calculateRateOfReturn(Map<LottoRank, Integer> results, int purchaseAmount) {

        return RateOfReturnCalculator.calculate(results, purchaseAmount);
    }

}

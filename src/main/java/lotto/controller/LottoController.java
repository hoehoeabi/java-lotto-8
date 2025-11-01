package lotto.controller;

import lotto.controller.view.LottoView;
import lotto.domain.Lotto;
import lotto.domain.LottoCollection;
import lotto.domain.LottoRank;
import lotto.service.LottoService;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class LottoController {

    private final LottoView view;
    private final LottoService service;

    public LottoController(LottoView view, LottoService service) {
        this.view = view;
        this.service = service;
    }

    public void lotteryStart() {

        String amountInput = view.inputAmountOfMoney();
        LottoCollection purchaseLottos = buyLottos(amountInput);

        showUserLottos(purchaseLottos);

        Set<Integer> winningNumbers = getWinningNumbers();
        String bonusNumberInput = view.inputBonusNumber();

        Map<LottoRank,Integer> results = service.calcReward(winningNumbers, bonusNumberInput,purchaseLottos);

        showFinalResults(results, amountInput,purchaseLottos);
    }

    private LottoCollection buyLottos(String amountInput) {
        int quantity = service.calcQuantity(amountInput);
        view.outputLottoAmount(quantity);

        return service.makeLottoNumbers(quantity);
    }

    private void showUserLottos(LottoCollection lottos){
        List<Lotto> userLottos = service.getAllLottos(lottos);
        view.outputUserLottoNumbers(userLottos);
    }

    private Set<Integer> getWinningNumbers() {
        String lottoNumberInput = view.inputLottoNumber();
        return service.parseNumbersInput(lottoNumberInput);
    }

    private void showFinalResults(Map<LottoRank,Integer> results, String amountInput,LottoCollection lottos) {
        int purchaseAmount = Integer.parseInt(amountInput);
        double rewardRate = service.calculateRateOfReturn(results, purchaseAmount, lottos);
        view.outputwinningLottery(results, rewardRate);
    }
}

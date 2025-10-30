package lotto.controller;

import lotto.controller.view.LottoView;
import lotto.domain.Lotto;
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
        String amount = view.inputAmountOfMoney();

        int quantity = service.calcQuantity(amount);
        view.outputLottoAmount(quantity);

        service.makeLottoNumbers(quantity);
        List<Lotto> userLottos = service.getAllLottos();
        view.outputUserLottoNumbers(userLottos);

        String lottoNumberInput = view.inputLottoNumber();
        Set<Integer> lottoNumber = service.parseNumbersInput(lottoNumberInput);

        String bounusNumber = view.inputBonusNumber();

        Map<LottoRank,Integer> results = service.calcReward(lottoNumber,bounusNumber);
        double rewardRate = service.calculateRateOfReturn(results,Integer.parseInt(amount));
        view.outputwinningLottery(results,rewardRate);

    }
}

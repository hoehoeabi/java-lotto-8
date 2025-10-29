package lotto.controller.view;

import lotto.domain.Lotto;
import lotto.domain.LottoRank;

import java.util.List;
import java.util.Map;

public interface OutPutView {

    void outputLottoAmount(int lottoAmount);

    void outputUserLottoNumbers(List<Lotto> userLottos);

    void outputwinningLottery(Map<LottoRank,Integer> results, double rewardRate);

}

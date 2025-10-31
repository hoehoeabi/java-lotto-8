package lotto.controller.view;

import camp.nextstep.edu.missionutils.Console;
import lotto.domain.Lotto;
import lotto.domain.LottoRank;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static lotto.message.InputMessage.*;
import static lotto.message.OutputMessage.*;

public class LottoView {

    public String inputAmountOfMoney() {
        System.out.println(PROMPT_PURCHASE_AMOUNT);
        return Console.readLine();
    }


    public void outputLottoAmount(int lottoAmount) {
        System.out.printf(PURCHASE_COUNT_FORMAT, lottoAmount);
        System.out.println();

    }


    public void outputUserLottoNumbers(List<Lotto> userLottos) {
        for (Lotto lotto : userLottos) {
            getNumbersByLotto(lotto);
        }

    }

    private static void getNumbersByLotto(Lotto lotto) {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");

        for (Integer number : lotto.getNumbers()) {
            joiner.add(String.valueOf(number));
        }

        System.out.println(joiner);
    }


    public String inputLottoNumber() {
        System.out.println();
        System.out.println(PROMPT_WINNING_NUMBERS);
        return Console.readLine();
    }


    public String inputBonusNumber() {
        System.out.println();
        System.out.println(PROMPT_BONUS_NUMBER);
        return Console.readLine();
    }


    public void outputwinningLottery(Map<LottoRank,Integer> results, double rewardRate) {
        System.out.println(STATISTICS_HEADER);

        List<LottoRank> ranksToPrint = List.of(
                LottoRank.FIFTH,  // 5등 (3개)
                LottoRank.FOURTH, // 4등 (4개)
                LottoRank.THIRD,  // 3등 (5개)
                LottoRank.SECOND, // 2등 (5개+보너스)
                LottoRank.FIRST   // 1등 (6개)
        );

        for (LottoRank rank : ranksToPrint) {
            showResult(results, rank);
        }

        System.out.printf(RATE_OF_RETURN_FORMAT, rewardRate);
    }

    private static void showResult(Map<LottoRank, Integer> results, LottoRank rank) {
        int matchCount = rank.getMatchCount();

        String prizeMoney = String.format("%,d", rank.getPrizeMoney());

        int winCount = results.get(rank);

        if (rank == LottoRank.SECOND) {
            System.out.printf(STATISTICS_BONUS_FORMAT, matchCount, prizeMoney, winCount);
            return;
        }
        System.out.printf(STATISTICS_RESULT_FORMAT, matchCount, prizeMoney, winCount);
    }

}

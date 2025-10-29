package lotto;

import camp.nextstep.edu.missionutils.Console;
import lotto.config.AppConfig;

public class Application {
    public static void main(String[] args) {
        try {
            new AppConfig()
                    .controller()
                    .lotteryStart();
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            Console.close();
        }
    }
}

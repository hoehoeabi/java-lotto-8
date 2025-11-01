package lotto.config;

import lotto.controller.LottoController;
import lotto.controller.view.LottoView;
import lotto.service.LottoService;
import lotto.validate.Validators;

public class AppConfig {


    private final Validators validator = new Validators();
    private final LottoService service = new LottoService(validator);
    private final LottoView view = new LottoView();
    private final LottoController controller = new LottoController(view,service);

    public LottoController controller() {
        return controller;
    }
}
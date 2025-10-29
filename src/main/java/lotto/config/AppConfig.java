package lotto.config;

import lotto.controller.LottoController;
import lotto.controller.view.LottoView;
import lotto.controller.view.LottoViewImpl;
import lotto.repository.LottoRepository;
import lotto.service.LottoService;
import lotto.validate.Validators;

public class AppConfig {


    private final Validators validator = new Validators();
    private final LottoRepository repository = new LottoRepository();
    private final LottoService service = new LottoService(validator,repository);
    private final LottoView view = new LottoViewImpl();
    private final LottoController controller = new LottoController(view,service);

    public LottoController controller() {
        return controller;
    }
}
package lotto.repository;

import lotto.domain.Lotto;

import java.util.ArrayList;
import java.util.List;

public class LottoRepository {

    private final List<Lotto> lottos = new ArrayList<>();

    public void add(Lotto entity) {
        lottos.add(entity);
    }

    public List<Lotto> getLottos() {
        return lottos;
    }
}

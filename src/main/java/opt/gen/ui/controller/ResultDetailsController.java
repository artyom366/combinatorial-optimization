package opt.gen.ui.controller;

import opt.gen.alg.domain.GASolution;

public interface ResultDetailsController {
    void openDetails(GASolution<Long, String, Double> selectedItem);
}

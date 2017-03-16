package opt.gen.ui.controller.impl;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import opt.gen.alg.domain.GADataEntry;
import opt.gen.alg.domain.impl.PickLocationViewDO;
import opt.gen.ui.controller.MainController;
import opt.gen.ui.controller.TableController;
import opt.gen.ui.service.PickLocationsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainControllerImpl implements MainController {

    @Autowired
    private PickLocationsService pickLocationsService;

    @Autowired
    private TableController tableController;

    @Override
    public Scene buildMainScene() {
        final Pane root = new Pane();

        final Iterable<PickLocationViewDO> pickLocations = pickLocationsService.findAll();

        final Scene scene = new Scene(root, 1200, 800);
        return scene;
    }

}

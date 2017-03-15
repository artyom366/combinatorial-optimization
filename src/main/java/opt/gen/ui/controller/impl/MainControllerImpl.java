package opt.gen.ui.controller.impl;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import opt.gen.ui.controller.MainController;
import opt.gen.ui.controller.TableController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainControllerImpl implements MainController {

    @Autowired
    private TableController tableController;

    @Override
    public Scene buildMainScene() {
        final Pane root = new Pane();









        final Scene scene = new Scene(root, 1200, 800);
        return scene;
    }

}

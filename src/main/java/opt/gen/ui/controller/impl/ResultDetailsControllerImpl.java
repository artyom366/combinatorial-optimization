package opt.gen.ui.controller.impl;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import opt.gen.alg.domain.GASolution;
import opt.gen.ui.component.ButtonFactory;
import opt.gen.ui.component.TextFieldFactory;
import opt.gen.ui.controller.ResultDetailsController;
import org.springframework.stereotype.Service;

@Service
public class ResultDetailsControllerImpl implements ResultDetailsController {

    private final static double WIDTH = 500d;
    private final static double HEIGHT = 500d;

    @Override
    public void createAndOpenModel(final GASolution<Long, String, Double> selectedItem) {

        final Stage addModal = new Stage();
        final Pane root = new Pane();

        final Button closeButton = ButtonFactory.getButton(80d, 220d, 25d, 50d, "Close");
        closeButton.setOnAction(event -> addModal.close());

        root.getChildren().addAll(closeButton);

        setModalComponents(root);
        setModalData(selectedItem, root);

        Scene myDialogScene = new Scene(root, WIDTH, HEIGHT);

        addModal.initModality(Modality.APPLICATION_MODAL);
        addModal.setScene(myDialogScene);
        addModal.show();
    }

    private void setModalComponents(final Pane root) {

        final TextField flightNumber = TextFieldFactory.getTextField(250, 20, 300, 25, "FLIGHT NUMBER");
        final TextField departure = TextFieldFactory.getTextField(280, 20, 300, 25, "DEPARTURE");
        final TextField departureTime = TextFieldFactory.getTextField(320, 20, 300, 25, "DEPARTURE TIME");
        final TextField destination = TextFieldFactory.getTextField(360, 20, 300, 25, "DESTINATION");
        final TextField destinationTime = TextFieldFactory.getTextField(400, 20, 300, 25, "DESTINATION TIME");
        final TextField status = TextFieldFactory.getTextField(440, 20, 300, 25, "STATUS");
        final TextField delay = TextFieldFactory.getTextField(480, 20, 300, 25, "DELAY");
        root.getChildren().addAll(flightNumber, departure, departureTime, destination, destinationTime, status, delay);
    }

    private void setModalData(final GASolution<Long, String, Double> selectedItem, final Pane root) {
    }
}

package opt.gen.ui.controller.impl;

import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import opt.gen.alg.domain.GASolution;
import opt.gen.ui.component.ButtonFactory;
import opt.gen.ui.component.LabelFactory;
import opt.gen.ui.component.TextAreaFactory;
import opt.gen.ui.controller.ChartController;
import opt.gen.ui.controller.ResultDetailsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultDetailsControllerImpl implements ResultDetailsController {

    private final static double WIDTH = 500d;
    private final static double HEIGHT = 650d;

    private final static String LOCATIONS_DATA = "Locations";
    private final static String NEIGHBOURS_DATA = "Neighbours";
    private final static String COUNT = "Count: ";

    @Autowired
    private ChartController chartController;

    @Override
    public void openDetails(final GASolution<Long, String, Double> selectedItem) {

        final Stage detailsModal = new Stage();
        final Pane root = new Pane();

        final Button closeButton = ButtonFactory.getButton(430d, 610d, 25d, 50d, "Close");
        closeButton.setOnAction(event -> detailsModal.close());

        root.getChildren().addAll(closeButton);

        setModalComponentsAndData(root, selectedItem);
        setScatterChartAndData(root, selectedItem);

        Scene myDialogScene = new Scene(root, WIDTH, HEIGHT);

        detailsModal.setTitle(String.format("%s - %d", selectedItem.getHash(), selectedItem.getTotalLocations()));
        detailsModal.initModality(Modality.APPLICATION_MODAL);
        detailsModal.setScene(myDialogScene);
        detailsModal.show();
    }

    private void setModalComponentsAndData(final Pane root, final GASolution<Long, String, Double> selectedItem) {

        final Label locationsCountLabel =  LabelFactory.getLabel(COUNT + selectedItem.getLocationsCount(), 420d, 290d, 100d, 30d);
        final Label locationsLabel = LabelFactory.getLabel(LOCATIONS_DATA, 20d, 290d, 100d, 30d);
        final TextArea locationsText = TextAreaFactory.getTextArea(selectedItem.getLocations(), 20d, 320d, 460d, 120d);

        final Label neighboursCountLabel =  LabelFactory.getLabel(COUNT + selectedItem.getNeighboursCount(), 420d, 450d, 100d, 30d);
        final Label neighboursLabel = LabelFactory.getLabel(NEIGHBOURS_DATA, 20d, 450d, 100d, 30d);
        final TextArea neighboursText = TextAreaFactory.getTextArea(selectedItem.getNeighbours(), 20d, 480d, 460d, 120d);

        root.getChildren().addAll(locationsCountLabel, locationsLabel, locationsText, neighboursCountLabel, neighboursLabel, neighboursText);
    }

    private void setScatterChartAndData(final Pane root, final GASolution<Long, String, Double> selectedItem) {

        final ScatterChart<Number, Number> xyScatterChart = chartController.getXYScatterChart();

        final XYChart.Series<Number, Number> locationSeries = new XYChart.Series<>();
        locationSeries.setName(LOCATIONS_DATA);
        selectedItem.getLocationsCoordinates().forEach(data -> locationSeries.getData().add(new XYChart.Data<>(data.getLeft(), data.getRight())));

        final XYChart.Series<Number, Number> neighboursSeries = new XYChart.Series<>();
        neighboursSeries.setName(NEIGHBOURS_DATA);
        selectedItem.getNeighboursCoordinates().forEach(data -> neighboursSeries.getData().add(new XYChart.Data<>(data.getLeft(), data.getRight())));

        xyScatterChart.getData().addAll(locationSeries, neighboursSeries);
        root.getChildren().add(xyScatterChart);
    }

}

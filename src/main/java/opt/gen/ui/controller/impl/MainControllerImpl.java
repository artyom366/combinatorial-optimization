package opt.gen.ui.controller.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import opt.gen.alg.domain.GADataEntry;
import opt.gen.alg.domain.GAPopulation;
import opt.gen.alg.domain.GASolution;
import opt.gen.alg.domain.GAStatistics;
import opt.gen.alg.domain.impl.Info;
import opt.gen.alg.service.helper.GAService;
import opt.gen.alg.service.runner.GARunnerService;
import opt.gen.alg.service.runner.impl.GARunnerServiceImpl;
import opt.gen.alg.service.strategy.GAStrategy;
import opt.gen.nn.serive.NeighboursService;
import opt.gen.ui.component.ButtonFactory;
import opt.gen.ui.component.TextFieldFactory;
import opt.gen.ui.controller.*;
import opt.gen.ui.service.PickLocationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

@Service
public class MainControllerImpl implements MainController {

    private final static float WIDTH = 1400f;
    private final static float HEIGHT = 800f;

    private final static String CONVERGENCE_SERIES_NAME = "Newly discovered combinations";
    private final static String START_BUTTON_CAPTION = "Start";
    private final static String CLEAR_BUTTON_CAPTION = "Clear";

//    @Autowired
//    private GAStrategy<Long, String, Double> mostDiversePopulationStrategy;

//    @Autowired
//    private GAStrategy<Long, String, Double> partialPopulationSelectionStrategy;

//    @Autowired
//    private GAStrategy<Long, String, Double> rouletteWheelSelectionStrategy;

    @Autowired
    private GAStrategy<Long, String, Double> tournamentSelectionStrategy;

    @Autowired
    private GAService<Long, String, Double> gaService;

    @Autowired
    private PickLocationsService pickLocationsService;

    @Autowired
    private NeighboursService neighboursService;

    @Autowired
    private ResultTableController resultTableController;

    @Autowired
    private InfoTableController infoTableController;

    @Autowired
    private ChartController chartController;

    @Autowired
    private ResultDetailsController resultDetailsController;

    @Override
    public Scene buildMainScene() {

        final LineChart<Number, Number> lineChart = getLineChart();
        final TableView<Info> infoTable = getInfoTable();
        final TableView<GASolution<Long, String, Double>> resultTable = getResultTable();

        final Button startButton = ButtonFactory.getButton(35, 10, 30, 75, START_BUTTON_CAPTION);
        startButton.setOnAction(event -> {

            final List<GASolution<Long, String, Double>> result = getResult();
            final GAStatistics statistics = tournamentSelectionStrategy.getStatistics();

            addDataToChart(lineChart, statistics);
            addDataToResultTable(resultTable, result);
            setResultTableMouseEvent(resultTable);
            addDataToInfoTable(infoTable, statistics);
        });

        final Button clearButton = ButtonFactory.getButton(120, 10, 30, 75, CLEAR_BUTTON_CAPTION);
        clearButton.setOnAction(event -> {
            clearLineChart(lineChart);
            clearTableView(resultTable);
            clearTableView(infoTable);
        });

        final Pane root = new Pane();
        root.getChildren().addAll(startButton, clearButton, lineChart, infoTable, resultTable);

        return new Scene(root, WIDTH, HEIGHT);
    }

    private List<GASolution<Long, String, Double>> getResult() {
        final List<GADataEntry<Long, String>> realPopulation = pickLocationsService.findAll();

        final Set<Long> geneDictionary = gaService.getGeneDictionary(realPopulation);
        final List<GAPopulation<Long, String, Double>> realPopulationGroups = gaService.getRealPopulationGrouped(realPopulation);

        final GARunnerService<Long, String, Double> gaRunnerService = new GARunnerServiceImpl(tournamentSelectionStrategy);
        final Map<String, GASolution<Long, String, Double>> result = gaRunnerService.run(geneDictionary, realPopulationGroups);

        neighboursService.searchForLocationPossibleNeighbours(result, tournamentSelectionStrategy.getInfo().getNeighboursDistance());

        return gaService.getResultAsList(result);
    }

    private TableView<GASolution<Long, String, Double>> getResultTable() {
        return (TableView<GASolution<Long, String, Double>>) resultTableController.getResultTable();
    }

    private TableView<Info> getInfoTable() {
        return infoTableController.getInfoTable();
    }

    private LineChart<Number, Number> getLineChart() {
        return chartController.getXYChart();
    }

    private void addDataToChart(final LineChart<Number, Number> lineChart, final GAStatistics statistics) {
        final XYChart.Series<Number, Number> convergence = new XYChart.Series<>();
        convergence.setName(CONVERGENCE_SERIES_NAME);

        IntStream.range(0, statistics.getConvergences().size())
                .forEach(index -> convergence.getData().add(new XYChart.Data<>(index, statistics.getConvergences().get(index))));

        clearLineChart(lineChart);
        lineChart.getData().add(convergence);
    }

    private void addDataToResultTable(final TableView<GASolution<Long, String, Double>> resultTable, final List<GASolution<Long, String, Double>> result) {
        final ObservableList<GASolution<Long, String, Double>> tableData = FXCollections.observableList(result);
        clearTableView(resultTable);
        resultTable.setItems(tableData);
    }

    private void setResultTableMouseEvent(final TableView<GASolution<Long, String, Double>> resultTable) {
        resultTable.setOnMouseClicked(event -> {
            final GASolution<Long, String, Double> selectedItem = resultTable.getSelectionModel().getSelectedItem();
            resultDetailsController.createAndOpenModel(selectedItem);
        });
    }

    private void addDataToInfoTable(final TableView<Info> infoTable, final GAStatistics statistics) {
        final List<Info> result = statistics.getRunnerInfo();
        final ObservableList<Info> tableData = FXCollections.observableList(result);
        clearTableView(infoTable);
        infoTable.setItems(tableData);
    }

    private void clearLineChart(final LineChart<Number, Number> lineChart) {
        lineChart.getData().clear();
    }

    private void clearTableView(final TableView tableView) {
        tableView.getItems().clear();
    }
}

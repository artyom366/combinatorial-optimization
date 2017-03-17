package opt.gen.ui.controller.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import opt.gen.alg.domain.GADataEntry;
import opt.gen.alg.domain.GASolution;
import opt.gen.alg.domain.GAStatistics;
import opt.gen.alg.service.helper.GAService;
import opt.gen.alg.service.runner.GARunnerService;
import opt.gen.alg.service.runner.impl.GARunnerServiceImpl;
import opt.gen.alg.service.strategy.GAStrategy;
import opt.gen.ui.controller.ChartController;
import opt.gen.ui.controller.InfoTableController;
import opt.gen.ui.controller.MainController;
import opt.gen.ui.controller.ResultTableController;
import opt.gen.ui.service.PickLocationsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

@Service
public class MainControllerImpl implements MainController {

    private final static float WIDTH = 1200f;
    private final static float HEIGHT = 800f;

    private final static String CONVERGENCE_SERIES_NAME = "Newly discovered combinations";
    private final static String COMBINATION_SERIES_NAME = "Total combinations";

    @Autowired
    private GAStrategy<Long, String> mostDiversePopulationStrategy;

    @Autowired
    private GAService<Long, String> gaService;

    @Autowired
    private PickLocationsService pickLocationsService;

    @Autowired
    private ResultTableController resultTableController;

    @Autowired
    private InfoTableController infoTableController;

    @Autowired
    private ChartController chartController;

    @Override
    public Scene buildMainScene() {

        final List<GASolution<Long, String>> result = getResult();
        final GAStatistics statistics = mostDiversePopulationStrategy.getStatistics();

        final LineChart<Number, Number> lineChart = getLineChartWithData(statistics);
        final TableView<GAStatistics> infoTable = getInfoTableWithData(statistics);
        final TableView<GASolution<Long, String>> resultTable = getResultTableWithData(result);

        final Pane root = new Pane();
        root.getChildren().addAll(lineChart, infoTable, resultTable);

        final Scene scene = new Scene(root, WIDTH, HEIGHT);
        return scene;
    }

    private List<GASolution<Long, String>> getResult() {
        final List<GADataEntry<Long, String>> realPopulation = pickLocationsService.findAll();

        final Set<Long> geneDictionary = gaService.getGeneDictionary(realPopulation);
        final Map<String, List<Long>> realPopulationGrouped = gaService.getRealPopulationAsGroupedMap(realPopulation);

        final GARunnerService<Long, String> gaRunnerService = new GARunnerServiceImpl(mostDiversePopulationStrategy);
        final Map<String, GASolution<Long, String>> result = gaRunnerService.run(geneDictionary, realPopulationGrouped);

        return gaService.getResultAsList(result);
    }

    private TableView<GASolution<Long, String>> getResultTableWithData(final List<GASolution<Long, String>> result) {
        final TableView<GASolution<Long, String>> resultTable = resultTableController.getResultTable();
        final ObservableList<GASolution<Long, String>> tableData = FXCollections.observableList(result);
        resultTable.setItems(tableData);
        return resultTable;
    }

    private TableView<GAStatistics> getInfoTableWithData(final GAStatistics statistics) {
        final TableView<GAStatistics> infoTable = infoTableController.getInfoTable();

        return infoTable;
    }

    private LineChart<Number, Number> getLineChartWithData(final GAStatistics statistics) {
        final LineChart<Number, Number> lineChart = chartController.getXYChart();
        final XYChart.Series<Number, Number> convergence = new XYChart.Series<>();
        convergence.setName(CONVERGENCE_SERIES_NAME);

        IntStream.range(0, statistics.getConvergences().size())
                .forEach(index -> convergence.getData().add(new XYChart.Data<>(index, statistics.getConvergences().get(index))));

        lineChart.getData().add(convergence);
        return lineChart;
    }
}

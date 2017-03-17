package opt.gen.ui.controller.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import opt.gen.alg.domain.GADataEntry;
import opt.gen.alg.domain.GASolution;
import opt.gen.alg.domain.GAStatistics;
import opt.gen.alg.service.helper.GAService;
import opt.gen.alg.service.runner.GARunnerService;
import opt.gen.alg.service.runner.impl.GARunnerServiceImpl;
import opt.gen.alg.service.strategy.GAStrategy;
import opt.gen.alg.service.strategy.impl.MostDiversePopulationStrategyImpl;
import opt.gen.ui.controller.ChartController;
import opt.gen.ui.controller.MainController;
import opt.gen.ui.controller.TableController;
import opt.gen.ui.service.PickLocationsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class MainControllerImpl implements MainController {

    private final static float WIDTH = 1200f;
    private final static float HEIGHT = 800f;

    @Autowired
    private GAStrategy<Long, String> mostDiversePopulationStrategy;

    @Autowired
    private GAService<Long, String> gaService;

    @Autowired
    private PickLocationsService pickLocationsService;

    @Autowired
    private TableController tableController;

    @Autowired
    private ChartController chartController;

    @Override
    public Scene buildMainScene() {

        final List<GASolution<Long, String>> result = getResult();
        final GAStatistics statistics = mostDiversePopulationStrategy.getStatistics();

        final TableView resultTable = getResultTableWithData(result);
        final LineChart<Number, Number> lineChart = getLineChartWithData(result);

        final Pane root = new Pane();
        root.getChildren().addAll(lineChart, resultTable);

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

    private TableView getResultTableWithData(final List<GASolution<Long, String>> result) {
        final TableView<GASolution<Long, String>> resultTable = tableController.getResultTable();
//        final ObservableList<GASolution<Long, String>> tableData = FXCollections.observableList(result);
//        resultTable.setItems(tableData);
        return resultTable;
    }

    private LineChart<Number, Number> getLineChartWithData(final List<GASolution<Long, String>> result) {
        final LineChart<Number, Number> lineChart = chartController.getXYChart();

        return lineChart;
    }
}

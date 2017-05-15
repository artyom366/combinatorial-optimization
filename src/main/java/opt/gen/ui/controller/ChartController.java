package opt.gen.ui.controller;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;

public interface ChartController {

    LineChart<Number,Number> getXYLineChart();

    ScatterChart<Number, Number> getXYScatterChart();
}

package opt.gen.ui.controller.impl;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import opt.gen.ui.controller.ChartController;
import org.springframework.stereotype.Service;

@Service
public class ChartControllerImpl implements ChartController {

    private final static float LAYOUT_X = 0f;
    private final static float LAYOUT_Y = 75f;

    private final static float WIDTH = 675f;
    private final static float HEIGHT = 400f;

    private final static String X_AXIS_LABEL = "Iterations";
    private final static String Y_AXIS_LABEL = "Combinations";
    private final static String CHART_TITLE = "GA Convergence";

    @Override
    public LineChart<Number,Number> getXYLineChart() {

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel(X_AXIS_LABEL);
        yAxis.setLabel(Y_AXIS_LABEL);

        final LineChart<Number,Number> lineChart = new LineChart<>(xAxis,yAxis);
        lineChart.setTitle(CHART_TITLE);
        lineChart.setLayoutX(LAYOUT_X);
        lineChart.setLayoutY(LAYOUT_Y);
        lineChart.setPrefHeight(HEIGHT);
        lineChart.setPrefWidth(WIDTH);

        return lineChart;
    }

    @Override
    public ScatterChart<Number, Number> getXYScatterChart() {
        final NumberAxis xAxis = new NumberAxis(0, 200, 50);
        final NumberAxis yAxis = new NumberAxis(0, 100, 20);

        final ScatterChart<Number, Number> chart = new ScatterChart<>(xAxis, yAxis);
        chart.setMinHeight(300f);
        chart.setPrefHeight(200f);
        chart.setLegendVisible(true);

        return chart;
    }
}

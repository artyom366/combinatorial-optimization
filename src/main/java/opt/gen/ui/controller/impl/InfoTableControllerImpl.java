package opt.gen.ui.controller.impl;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import opt.gen.alg.domain.GAStatistics;
import opt.gen.ui.component.TableColumnFactory;
import opt.gen.ui.controller.InfoTableController;
import org.springframework.stereotype.Service;

@Service
public class InfoTableControllerImpl implements InfoTableController {

    private final static float LAYOUT_X = 0f;
    private final static float LAYOUT_Y = 500f;

    private final static float WIDTH = 700f;
    private final static float HEIGHT = 300f;

    private final static float COLUMN_WIDTH_SMALL = 75f;
    private final static float COLUMN_WIDTH_NORMAL = 100f;
    private final static float COLUMN_WIDTH_LARGE = 250f;

    private final static String ITERATION = "ITERATION";
    private final static String NEW_COMBINATIONS = "NEW_C";
    private final static String MUTATED_COUNT = "MUTATED_C";
    private final static String CORRECTED_COUNT = "CORRECTED_C";
    private final static String RETRIES_COUNT = "RETRIES_C";
    private final static String TOTAL_LOCATIONS_COUNT = "TOTAL_C";
    private final static String TOTAL_NON_UNIQUE_COUNT = "TOTAL_U_C";

    @Override
    public TableView<GAStatistics> getInfoTable() {

        final TableView<GAStatistics> tableView = new TableView<>();
        tableView.setLayoutX(LAYOUT_X);
        tableView.setLayoutY(LAYOUT_Y);
        tableView.setPrefWidth(WIDTH);
        tableView.setPrefHeight(HEIGHT);

        final TableColumn iteration = TableColumnFactory.<GAStatistics, String>getColumn(ITERATION, "iteration", COLUMN_WIDTH_NORMAL);
        final TableColumn newCombinations = TableColumnFactory.<GAStatistics, String>getColumn(NEW_COMBINATIONS, "newCombinationsCount", COLUMN_WIDTH_NORMAL);
        final TableColumn mutatedCount = TableColumnFactory.<GAStatistics, String>getColumn(MUTATED_COUNT, "mutatedCount", COLUMN_WIDTH_NORMAL);
        final TableColumn correctedCount = TableColumnFactory.<GAStatistics, String>getColumn(CORRECTED_COUNT, "correctedCount", COLUMN_WIDTH_NORMAL);
        final TableColumn retriesCount = TableColumnFactory.<GAStatistics, String>getColumn(RETRIES_COUNT, "retriesCount", COLUMN_WIDTH_NORMAL);
        final TableColumn totalLocationsCount = TableColumnFactory.<GAStatistics, String>getColumn(TOTAL_LOCATIONS_COUNT, "totalCount", COLUMN_WIDTH_NORMAL);
        final TableColumn totalNonUniqueCount = TableColumnFactory.<GAStatistics, String>getColumn(TOTAL_NON_UNIQUE_COUNT, "totalNonUniqueCount", COLUMN_WIDTH_NORMAL);
        tableView.getColumns().addAll(iteration, newCombinations, mutatedCount, correctedCount, retriesCount, totalLocationsCount, totalNonUniqueCount);

        return tableView;
    }

}

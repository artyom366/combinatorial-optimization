package opt.gen.ui.controller.impl;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import opt.gen.alg.domain.GASolution;
import opt.gen.ui.component.TableColumnFactory;
import opt.gen.ui.controller.ResultTableController;
import org.springframework.stereotype.Service;

@Service
public class ResultTableControllerImpl implements ResultTableController<GASolution<Long, String, Double>> {

    private final static float LAYOUT_X = 700;
    private final static float LAYOUT_Y = 0f;

    private final static float WIDTH = 700f;
    private final static float HEIGHT = 800f;

    private final static float COLUMN_WIDTH_SMALL = 75f;
    private final static float COLUMN_WIDTH_NORMAL = 100f;
    private final static float COLUMN_WIDTH_LARGE = 200f;

    private final static String GENES = "GENES";
    private final static String LOCATIONS = "LOCATIONS";
    private final static String GENES_COUNT = "GENES_C";
    private final static String LOCATIONS_COUNT = "LOC_C";
    private final static String NEIGHBOURS = "NEIGHBOURS";
    private final static String NEIGHBOURS_COUNT = "NG_C";
    private final static String TOTAL = "TOTAL";

    @Override
    public TableView<GASolution<Long, String, Double>> getResultTable() {

        final TableView<GASolution<Long, String, Double>> tableView = new TableView<>();
        tableView.setLayoutX(LAYOUT_X);
        tableView.setLayoutY(LAYOUT_Y);
        tableView.setPrefWidth(WIDTH);
        tableView.setPrefHeight(HEIGHT);

        final TableColumn hash = TableColumnFactory.<GASolution<Long, String, Double>, String>getColumn(GENES, "hash", COLUMN_WIDTH_NORMAL);
        final TableColumn locations = TableColumnFactory.<GASolution<Long, String, Double>, String>getColumn(LOCATIONS, "locations", COLUMN_WIDTH_LARGE);
        final TableColumn genesCount = TableColumnFactory.<GASolution<Long, String, Double>, String>getColumn(GENES_COUNT, "genesCount", COLUMN_WIDTH_SMALL);
        final TableColumn locationsCount = TableColumnFactory.<GASolution<Long, String, Double>, String>getColumn(LOCATIONS_COUNT, "locationsCount", COLUMN_WIDTH_SMALL);
        final TableColumn neighbours = TableColumnFactory.<GASolution<Long, String, Double>, String>getColumn(NEIGHBOURS, "neighbours", COLUMN_WIDTH_NORMAL);
        final TableColumn neighboursCount = TableColumnFactory.<GASolution<Long, String, Double>, String>getColumn(NEIGHBOURS_COUNT, "neighboursCount", COLUMN_WIDTH_SMALL);
        final TableColumn totalLocationsCount = TableColumnFactory.<GASolution<Long, String, Double>, String>getColumn(TOTAL, "totalLocations", COLUMN_WIDTH_SMALL);
        tableView.getColumns().addAll(hash, locations, genesCount, locationsCount, neighbours, neighboursCount, totalLocationsCount);

        return tableView;
    }


}

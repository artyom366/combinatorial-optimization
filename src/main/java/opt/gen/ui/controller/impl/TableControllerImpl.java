package opt.gen.ui.controller.impl;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import opt.gen.alg.domain.GASolution;
import opt.gen.ui.component.TableColumnFactory;
import opt.gen.ui.controller.TableController;
import org.springframework.stereotype.Service;

@Service
public class TableControllerImpl implements TableController<GASolution<Long, String>> {

    private final static float LAYOUT_X = 700;
    private final static float LAYOUT_Y = 0;

    private final static float WIDTH = 500f;
    private final static float HEIGHT = 800f;

    private final static float COLUMN_WIDTH_SMALL = 50f;
    private final static float COLUMN_WIDTH_NORMAL = 100f;
    private final static float COLUMN_WIDTH_LARGE = 200f;

    private final static String HASH = "HASH";
    private final static String LOCATIONS = "LOCATIONS";
    private final static String COUNT = "COUNT";

    @Override
    public TableView<GASolution<Long, String>> getResultTable() {

        final TableView<GASolution<Long, String>> tableView = new TableView<>();
        tableView.setLayoutX(LAYOUT_X);
        tableView.setLayoutY(LAYOUT_Y);
        tableView.setPrefWidth(WIDTH);
        tableView.setPrefHeight(HEIGHT);

        final TableColumn hash = TableColumnFactory.<GASolution<Long, String>, String>getColumn(HASH, "hash", COLUMN_WIDTH_LARGE);
        final TableColumn locations = TableColumnFactory.<GASolution<Long, String>, String>getColumn(LOCATIONS, "locations", COLUMN_WIDTH_LARGE);
        final TableColumn count = TableColumnFactory.<GASolution<Long, String>, String>getColumn(COUNT, "count", COLUMN_WIDTH_NORMAL);
        tableView.getColumns().addAll(hash, locations, count);

        return tableView;
    }


}

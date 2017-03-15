package opt.gen.ui.controller;

import javafx.scene.control.TableView;

public interface TableController<TABLE_DATA> {

    TableView<TABLE_DATA> getResultTable();
}

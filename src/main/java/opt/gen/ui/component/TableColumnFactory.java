package opt.gen.ui.component;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableColumnFactory {

    public static <S, T> TableColumn getColumn(final String caption, final String parameter, final float width) {
        final TableColumn<S, T> column = new TableColumn<>(caption);
        column.setCellValueFactory(new PropertyValueFactory<>(parameter));
        column.setPrefWidth(width);
        return column;
    }
}

package opt.gen.ui.component;

import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class LabelFactory {

    public static Label getLabel(final String text, final double x, final double y, final double width, final double height) {
        final Label label = new Label(text);
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setPrefWidth(width);
        label.setPrefHeight(height);
        return label;
    }

    public static Text getHeaderText(final String text, final double x, final double y) {
        final Text caption = new Text(text);
        caption.setLayoutX(x);
        caption.setLayoutY(y);
        caption.setStyle("-fx-font: 12 arial;");
        return caption;
    }




}

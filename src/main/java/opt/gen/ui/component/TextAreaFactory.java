package opt.gen.ui.component;

import javafx.scene.control.TextArea;

public class TextAreaFactory {

    public static TextArea getTextArea(final String text, final double x, final double y, final double width, final double height) {
        final TextArea textArea = new TextArea(text);
        textArea.setLayoutX(x);
        textArea.setLayoutY(y);
        textArea.setPrefWidth(width);
        textArea.setPrefHeight(height);
        textArea.setWrapText(true);
        textArea.setEditable(false);
        return textArea;
    }
}

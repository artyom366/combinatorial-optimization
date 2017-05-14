package opt.gen.ui.component;

import javafx.scene.control.TextField;

public class TextFieldFactory {

    public static TextField getTextField(final double y, final double x, final double width, final double height, final String prompt) {
        final TextField textField = new TextField();
        textField.setLayoutY(y);
        textField.setLayoutX(x);
        textField.setPrefSize(width, height);
        textField.setPromptText(prompt);
        textField.setId(prompt);
        return textField;
    }
}

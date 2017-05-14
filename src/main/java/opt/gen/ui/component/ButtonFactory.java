package opt.gen.ui.component;


import javafx.scene.control.Button;

public class ButtonFactory {

    public static Button getButton(final double x, final double y, final double height, final double width, final String caption) {
        final Button button = new Button();
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setMinHeight(height);
        button.setMinWidth(width);
        button.setText(caption);
        return button;
    }
}
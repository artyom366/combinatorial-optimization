package opt.gen.ui.component;


import javafx.scene.control.Button;

public class ButtonFactory {

    public static Button getButton(final float x, final float y, final float height, final float width, final String caption) {
        final Button button = new Button();
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setMinHeight(height);
        button.setMinWidth(width);
        button.setText(caption);
        return button;
    }
}
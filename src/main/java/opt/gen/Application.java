package opt.gen;

import javafx.stage.Stage;
import opt.gen.ui.controller.MainController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@EnableAutoConfiguration
@ComponentScan("opt.gen")
public class Application extends AbstractJavaFxApplicationSupport {

	private final static String APP_TITLE = "GA";

	@Autowired
	private MainController mainController;

	public static void main(final String[] args) {
		launchApp(Application.class, args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		primaryStage.setScene(mainController.buildMainScene());
		primaryStage.setTitle(APP_TITLE);
		primaryStage.show();
	}
}

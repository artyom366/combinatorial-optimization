package opt.gen;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public abstract class AbstractJavaFxApplicationSupport extends javafx.application.Application {

    private static String[] ARGS;
    private ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {
        context = SpringApplication.run(getClass(), ARGS);
        context.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        context.close();
    }

    protected static void launchApp(final Class<? extends AbstractJavaFxApplicationSupport> clazz, final String[] args) {
        AbstractJavaFxApplicationSupport.ARGS = args;
        javafx.application.Application.launch(clazz, args);
    }
}

package localdbms;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import localdbms.controller.Controller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.io.IOException;
import java.io.InputStream;

public class SpringFxmlLoader {

    private static final ApplicationContext APPLICATION_CONTEXT = new AnnotationConfigApplicationContext(AppConfig.class);

    public static Controller load(String url) {
        try(InputStream fxmlStream = SpringFxmlLoader.class.getResourceAsStream(url)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(APPLICATION_CONTEXT::getBean);
            Node view = loader.load(fxmlStream);
            Controller controller = loader.getController();
            controller.setView(view);
            return controller;
        } catch (IOException e) {
            throw new RuntimeException("Can't load resource", e);
        }
    }
}

package localdbms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import localdbms.controller.Controller;
import localdbms.controller.DatabaseSelectionController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Bootstrap extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Controller controller = SpringFxmlLoader.load("/view/databaseSelection.fxml");
        Parent root = (Parent) controller.getView();
        stage.setTitle("Local DBMS");
        stage.setMinHeight(300);
        stage.setMinWidth(520);
        stage.setScene(new Scene(root, 540, 300));
        stage.show();
    }
}

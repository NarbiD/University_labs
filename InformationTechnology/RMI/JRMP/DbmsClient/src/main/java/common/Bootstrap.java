package common;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Bootstrap extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Controller controller = SpringFxmlLoader.load("/view/databaseOverview.fxml");
        Parent root = (Parent) controller.getView();
        stage.setTitle("Local DBMS");
        stage.setMinHeight(300);
        stage.setMinWidth(520);
        stage.setScene(new Scene(root, 540, 300));
        stage.show();
    }
}

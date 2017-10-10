package localdbms.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    public void createTable_onClick() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/createTable.fxml"));
        Stage stage = new Stage();
        stage.setTitle("");
        stage.setScene(new Scene(root, 426, 71));
        stage.show();
    }

    public void createDatabase_onClick() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/createDatabase.fxml"));
        Stage stage = new Stage();
        stage.setTitle("");
        stage.setScene(new Scene(root, 426, 71));
        stage.show();
    }

    public void deleteTable_onClick() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/deleteTable.fxml"));
        Stage stage = new Stage();
        stage.setTitle("");
        stage.setScene(new Scene(root, 426, 71));
        stage.show();
    }

    public void deleteDatabase_onClick() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/deleteDatabase.fxml"));
        Stage stage = new Stage();
        stage.setTitle("");
        stage.setScene(new Scene(root, 426, 71));
        stage.show();
    }

    public void createRowInTable_onClick() {

    }

    public void sortTable_onClick() {

    }
}

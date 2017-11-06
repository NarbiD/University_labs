package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import service.DatabaseService;

public class CreateDatabaseController extends Controller {

    private DatabaseService databaseService;

    @FXML
    public TextField textField;

    @FXML
    public void submit(MouseEvent mouseEvent) throws Exception {
        try {
            databaseService.createDatabase(textField.getCharacters().toString());
        } catch (Exception e) {
            Warning.show(e);
        }
        close(mouseEvent);
    }

    @FXML
    public void close(MouseEvent mouseEvent) {
        hide(mouseEvent);
    }

    public void setDatabaseService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }
}
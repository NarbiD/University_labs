package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import service.DatabaseService;
import java.io.IOException;

public class CreateDatabaseController extends AbstractController {

    private DatabaseService databaseService;

    @FXML
    public TextField textField;

    @FXML
    public void submit(MouseEvent mouseEvent) throws IOException {
        try {
            databaseService.createDatabase(textField.getCharacters().toString());
        } catch (IOException e) {
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
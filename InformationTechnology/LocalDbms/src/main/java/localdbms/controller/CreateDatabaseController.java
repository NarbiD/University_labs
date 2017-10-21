package localdbms.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import localdbms.DBMS.exception.StorageException;
import localdbms.service.DatabaseService;

public class CreateDatabaseController extends AbstractController {

    private DatabaseService databaseService;

    @FXML
    public Button btnCancel;

    @FXML
    public Button btnOk;

    @FXML
    public TextField textField;

    @FXML
    public void submit(MouseEvent mouseEvent) throws StorageException {
        try {
            databaseService.createDatabase(textField.getCharacters().toString());
        } catch (StorageException e) {
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
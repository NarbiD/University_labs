package localdbms.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import localdbms.database.Database;
import localdbms.database.Dbms;
import localdbms.database.exception.StorageException;

public class CreateDatabaseController extends AbstractController {

    private Dbms dbms;
    private ObservableList<Database> databases;

    @FXML
    public Button btnCancel;

    @FXML
    public Button btnOk;

    @FXML
    public TextField textField;

    @FXML
    public void initialize() throws StorageException {
        this.databases = DatabaseSelectionController.databases;
    }

    @FXML
    public void submit(MouseEvent mouseEvent) throws StorageException {
        databases.add(dbms.createDatabase(textField.getCharacters().toString()));
        close(mouseEvent);
    }

    @FXML
    public void close(MouseEvent mouseEvent) {
        hide(mouseEvent);
    }

    public void setDbms(Dbms dbms) {
        this.dbms = dbms;
    }
}

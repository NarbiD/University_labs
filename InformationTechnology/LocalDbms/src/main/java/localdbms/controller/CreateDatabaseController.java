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
    public void submit(MouseEvent mouseEvent) throws StorageException {
        try {
            databases.add(dbms.createDatabase(textField.getCharacters().toString()));
        } catch (StorageException e) {
            Warning.show(e);
        }
        close(mouseEvent);
    }

    @FXML
    public void close(MouseEvent mouseEvent) {
        hide(mouseEvent);
    }

    public void setDatabases(ObservableList<Database> databases) {
        this.databases = databases;
    }

    public void setDbms(Dbms dbms) {
        this.dbms = dbms;
    }
}
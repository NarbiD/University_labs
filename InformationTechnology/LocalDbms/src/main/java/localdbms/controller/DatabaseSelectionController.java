package localdbms.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import localdbms.SpringFxmlLoader;
import localdbms.database.Database;
import localdbms.database.Dbms;
import localdbms.database.exception.StorageException;


public class DatabaseSelectionController extends AbstractController {

    private Dbms dbms;
    static ObservableList<Database> databases;

    @FXML
    public Button btnCreateDatabase;

    @FXML
    public Button btnDeleteDatabase;

    @FXML
    public Button btnSelect;

    @FXML
    public TableColumn<Database, String> Databases;

    @FXML
    public TableView<Database> DatabaseSelectionTable;

    @FXML
    public void initialize() throws StorageException {
        Databases.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        databases = FXCollections.observableArrayList(dbms.getAllDatabases());
        DatabaseSelectionTable.setItems(databases);
    }

    public void createDatabase_onClick(MouseEvent mouseEvent) {
        Stage stage = new Stage();
            Controller controller = SpringFxmlLoader.load("/view/createDatabase.fxml");
            Parent root = (Parent) controller.getView();
            stage.setTitle("Create database");
            stage.setMinHeight(80);
            stage.setMinWidth(440);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)mouseEvent.getSource()).getScene().getWindow());
            stage.show();
    }

    public void deleteDatabase_onClick(MouseEvent mouseEvent) throws StorageException {
        int selectedIndex = DatabaseSelectionTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            ObservableList<Database> databases = DatabaseSelectionTable.getItems();
            databases.get(selectedIndex).delete();  // delete from storage
            databases.remove(selectedIndex);        // delete from list
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No selection");
            alert.setHeaderText("No database selected");
            alert.setContentText("Please select a database in the table.");
            alert.showAndWait();
        }
    }

    public void selectDatabase_onClick(MouseEvent mouseEvent) {
    }

    public void setDbms(Dbms dbms) {
        this.dbms = dbms;
    }
}

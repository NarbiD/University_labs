package localdbms.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import localdbms.SpringFxmlLoader;
import localdbms.DBMS.database.Database;
import localdbms.DBMS.exception.StorageException;
import localdbms.service.DatabaseService;


public class DatabaseOverviewController extends AbstractController {

    private IntegerProperty selectedIndex;
    private DatabaseService databaseService;

    @FXML
    public TableColumn<Database, String> databases;

    @FXML
    public TableView<Database> databaseView;

    @FXML
    public void initialize() throws StorageException {
        databases.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        databaseView.setItems(databaseService.getDatabases());
    }

    public void createDatabase_onClick(MouseEvent mouseEvent) {
        Stage stage = new Stage();
        Controller controller = SpringFxmlLoader.load("/view/createDatabase.fxml");
        Parent root = (Parent) controller.getView();
        stage.setTitle("Create database");
        stage.setMinHeight(80);
        stage.setMinWidth(300);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)mouseEvent.getSource()).getScene().getWindow());
        stage.show();
    }

    public void deleteDatabase_onClick() {
        selectedIndex.set(databaseView.getSelectionModel().getSelectedIndex());
        if (selectedIndex.get() >= 0) {
            try {
                databaseService.deleteDatabase(selectedIndex.get());
            } catch (StorageException e) {
                Warning.show(e);
            }
        } else {
            noDatabaseSelectedMessage();
        }
    }

    public void selectDatabase_onClick(MouseEvent mouseEvent) {
        this.selectedIndex.set(databaseView.getSelectionModel().getSelectedIndex());
        if (this.selectedIndex.get() >= 0) {
            Controller controller = SpringFxmlLoader.load("/view/tableOverview.fxml");
            Parent root = (Parent) controller.getView();
            Stage stage = new Stage();
            stage.setTitle("Tables");
            stage.setMinHeight(440);
            stage.setMinWidth(840);
            stage.setScene(new Scene(root, 840, 440));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)mouseEvent.getSource()).getScene().getWindow());
            stage.show();
        } else {
            noDatabaseSelectedMessage();
        }
    }

    private void noDatabaseSelectedMessage() {
        Warning.show("No database selected. Please select a database in the table.");
    }

    public IntegerProperty getSelectedIndex() {
        return this.selectedIndex;
    }

    public ObservableList<Database> getDatabases() {
        return databaseService.getDatabases();
    }

    public void setSelectedIndex(IntegerProperty index) {
        this.selectedIndex = index;
    }

    public void setDatabaseService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }
}

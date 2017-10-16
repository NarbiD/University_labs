package localdbms.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
import localdbms.database.*;
import localdbms.database.exception.StorageException;
import localdbms.database.exception.TableException;

public class TableOverviewController extends AbstractController {
    @FXML
    public TableView<Object> EntryOverview;
    private ObservableList<Table> tables;
    private ObservableList<Database> databases;
    private IntegerProperty dbIndex;
    private IntegerProperty tableSelectedIndex;

    @FXML
    public Button btnDelete;

    @FXML
    public Button btnCreate;

    @FXML
    public TableColumn<Table, String> TablesCol;

    @FXML
    public TableView<Table> TableSelection;

    @FXML
    public void initialize() throws StorageException {
        TablesCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        tables.clear();
        tables.addAll(databases.get(dbIndex.getValue()).getTables());
        TableSelection.setItems(tables);
    }

    public void createTable_onClick(MouseEvent mouseEvent) {
        Stage stage = new Stage();
        Controller controller = SpringFxmlLoader.load("/view/createTable.fxml");
        Parent root = (Parent) controller.getView();
        stage.setTitle("Create table");
        stage.setMinHeight(80);
        stage.setMinWidth(440);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)mouseEvent.getSource()).getScene().getWindow());
        stage.show();
    }

    public void deleteTable_onClick(MouseEvent mouseEvent) throws TableException {
        tableSelectedIndex.set(TableSelection.getSelectionModel().getSelectedIndex());
        if (tableSelectedIndex.get() >= 0) {
            ObservableList<Table> tables = TableSelection.getItems();
            Table table = tables.get(tableSelectedIndex.get());
            Tables.delete(table.getName(), table.getLocation());  // delete from storage
            tables.remove(table);                                 // delete from list
        } else {
            noTableSelectedMessage();
        }
    }

    private void noTableSelectedMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No selection");
        alert.setHeaderText("No table selected");
        alert.setContentText("Please select a table in the list.");
        alert.showAndWait();
    }

    void setColumns(String... names) {
        EntryOverview.setEditable(true);
        for (String name : names) {
            EntryOverview.getColumns().add(new TableColumn<Object, String>(name));
        }
    }

    public ObservableList<Table> getTables() {
        return tables;
    }

    public IntegerProperty getDbIndex() {
        return dbIndex;
    }

    public ObservableList<Database> getDatabases() {
        return databases;
    }

    public void setDatabases(ObservableList<Database> databases) {
        this.databases = databases;
    }

    public void setDbIndex(IntegerProperty dbIndex) {
        this.dbIndex = dbIndex;
    }

    public void setTables(ObservableList<Table> tables) {
        this.tables = tables;
    }


    public void setTableSelectedIndex(IntegerProperty index) {
        this.tableSelectedIndex = index;
    }
}

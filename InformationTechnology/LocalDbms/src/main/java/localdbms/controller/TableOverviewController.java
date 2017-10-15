package localdbms.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import localdbms.database.Database;
import localdbms.database.Table;
import localdbms.database.exception.StorageException;


public class TableOverviewController extends AbstractController {

    private ObservableList<Table> tables;
    private ObservableList<Database> databases;
    private IntegerProperty dbIndex;

    @FXML
    public TableColumn<Table, String> Tables;

    @FXML
    public TableView<Table> TableSelection;

    @FXML
    public void initialize() throws StorageException {
        Tables.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        tables.clear();
        tables.addAll(databases.get(dbIndex.getValue()).getTables());
        TableSelection.setItems(tables);
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
}

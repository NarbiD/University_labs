package localdbms.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import localdbms.SpringFxmlLoader;
import localdbms.database.Database;
import localdbms.database.Table;
import localdbms.database.exception.StorageException;


public class TableOverviewController extends AbstractController {

    private ObservableList<Table> tables;

    private ObservableList<Database> databases;

    private IntegerProperty dbIndex;

    @FXML
    public Button btnDelete;

    @FXML
    public Button btnCreate;

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
}

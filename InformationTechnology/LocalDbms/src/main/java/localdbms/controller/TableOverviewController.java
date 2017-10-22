package localdbms.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import localdbms.SpringFxmlLoader;
import localdbms.DBMS.entry.Entry;
import localdbms.DBMS.exception.StorageException;
import localdbms.DBMS.table.Table;
import localdbms.service.TableService;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TableOverviewController extends AbstractController {

    private ObservableList<Table> tables;

    private IntegerProperty tableSelectedIndex;

    private TableService tableService;

    @FXML
    private ImageView imageView;

    @FXML
    public TableView<Object> EntryOverview;

    @FXML
    public Button btnSelect;

    @FXML
    public Button btnAddRow;

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
        tableService.initTables();
        tables = tableService.getTables();
        TableSelection.setItems(tables);
    }

    public void createTable_onClick(MouseEvent mouseEvent) {
        Stage stage = new Stage();
        Controller controller = SpringFxmlLoader.load("/view/createTable.fxml");
        Parent root = (Parent) controller.getView();
        stage.setTitle("Create table");
        stage.setMinWidth(360);
        stage.setMinHeight(310);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)mouseEvent.getSource()).getScene().getWindow());
        stage.show();
    }

    public void deleteTable_onClick()  {
        tableSelectedIndex.set(TableSelection.getSelectionModel().getSelectedIndex());
        if (tableSelectedIndex.get() >= 0) {
            try {
                tableService.deleteTable(tableSelectedIndex.get());
            } catch (StorageException e) {
                Warning.show(e);
            }
        } else {
            noTableSelectedMessage();
        }
    }

    private void noTableSelectedMessage() {
        Warning.show("No table selected. Please select a table in the list.");
    }

    public void select_onClick() {
        tableSelectedIndex.set(TableSelection.getSelectionModel().getSelectedIndex());
        if (tableSelectedIndex.get() >= 0) {
            showEntries(tableService.getTable(tableSelectedIndex.get()));
        } else {
            noTableSelectedMessage();
        }
    }

    private void showEntries(Table table) {
        EntryOverview.getColumns().clear();
        table.getColumnNames().forEach(this::addColumn);
        setValueFactories();

        List<Entry> entries = table.getEntries();
        ObservableList<Object> values = FXCollections.observableArrayList();
        entries.forEach(entry -> values.add(FXCollections.observableArrayList(entry.getValues())));
        EntryOverview.setItems(values);
    }

    private void addColumn(String name) {
        EntryOverview.setEditable(true);
        EntryOverview.getColumns().add(new TableColumn<Object, String>(name));
    }

    @SuppressWarnings("unchecked")
    private void setValueFactories() {
        for (int columnNum = 0; columnNum < EntryOverview.getColumns().size(); columnNum++) {
            final int finalColumnNum = columnNum;
            EntryOverview.getColumns().get(finalColumnNum).setCellValueFactory(cell ->
                    new SimpleObjectProperty(((List<Object>)cell.getValue()).get(finalColumnNum)));
        }
    }

    public void addRow_onClick(MouseEvent mouseEvent) {
        tableSelectedIndex.set(TableSelection.getSelectionModel().getSelectedIndex());
        if (tableSelectedIndex.get() >= 0) {
            Stage stage = new Stage();
            Controller controller = SpringFxmlLoader.load("/view/createRowInTable.fxml");
            Parent root = (Parent) controller.getView();
            stage.setTitle("Create row in table");
            int columnsAmount = tables.get(TableSelection.getSelectionModel().getSelectedIndex()).getColumnNames().size();
            stage.setHeight(160.0 + columnsAmount*30.0);
            stage.setMinWidth(370);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)mouseEvent.getSource()).getScene().getWindow());
            stage.show();
        } else {
            noTableSelectedMessage();
        }
    }

    public void setTables(ObservableList<Table> tables) {
        this.tables = tables;
    }

    public IntegerProperty getTableSelectedIndex() {
        return tableSelectedIndex;
    }

    public void setTableSelectedIndex(IntegerProperty index) {
        this.tableSelectedIndex = index;
    }

    public void setTableService(TableService tableService) {
        this.tableService = tableService;
    }

    public void loadImage(MouseEvent mouseEvent) throws IOException {
        Table t = TableSelection.getItems().get(TableSelection.getSelectionModel().getSelectedIndex());
        Entry entry = t.getEntries().get(EntryOverview.getSelectionModel().getSelectedIndex());
        Optional<BufferedImage> bufferedImage = Optional.ofNullable(entry.getImage());
        if (bufferedImage.isPresent()) {
            Image image = SwingFXUtils.toFXImage(bufferedImage.get(), null);
            this.imageView.setImage(image);
        }
    }
}

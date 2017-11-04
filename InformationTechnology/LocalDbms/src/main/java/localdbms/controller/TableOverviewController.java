package localdbms.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import localdbms.DBMS.Entry;
import localdbms.DBMS.Table;
import localdbms.SpringFxmlLoader;
import localdbms.service.TableService;
import java.util.List;

public class TableOverviewController extends Controller {

    private IntegerProperty tableSelectedIndex;
    private IntegerProperty entrySelectedIndex;
    private TableService tableService;

    @FXML
    public TableView<Table> tableOverview;

    @FXML
    public TableColumn<Table, String> tableColumn;

    @FXML
    public TableView<Object> entryOverview;

    @FXML
    public void initialize() throws Exception {
        initTableView();
    }

    private void initTableView() {
        tableOverview.getItems().clear();
        tableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        tableService.initTables();
        tableOverview.setItems(tableService.getTables());
        initDynamicEntryView();
        initTextView();
    }

    private void initDynamicEntryView() {
        tableOverview.setRowFactory(tv -> {
            TableRow<Table> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    select();
                }
            });
            return row;
        });
    }

    private void select() {
        tableSelectedIndex.set(tableOverview.getSelectionModel().getSelectedIndex());
        if (tableSelectedIndex.get() >= 0) {
            showEntries(tableService.getTable(tableSelectedIndex.get()));
        } else {
            noSelectedMessage();
        }
    }

    private void showEntries(Table table) {
        entryOverview.getSelectionModel().clearSelection();
        entryOverview.getColumns().clear();
        if (entryOverview.getColumns().isEmpty()) {
            table.getColumnNames().forEach(this::addColumn);
            setValueFactories();
        }
        List<Entry> entries = table.getEntries();
        ObservableList<Object> values = FXCollections.observableArrayList();
        entries.forEach(entry -> values.add(FXCollections.observableArrayList(entry.getValues())));
        entryOverview.setItems(values);
    }

    private void addColumn(String name) {
        entryOverview.setEditable(true);
        entryOverview.getColumns().add(new TableColumn<Object, String>(name));
    }

    @SuppressWarnings("unchecked")
    private void setValueFactories() {
        for (int columnNum = 0; columnNum < entryOverview.getColumns().size(); columnNum++) {
            final int finalColumnNum = columnNum;
            entryOverview.getColumns().get(finalColumnNum).setCellValueFactory(cell ->
                    new SimpleObjectProperty(((List<Object>)cell.getValue()).get(finalColumnNum)));
        }
    }

    private void initTextView() {
        entryOverview.setRowFactory( tv -> {
            TableRow<Object> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Table table = tableService.getTable(tableOverview.getSelectionModel().getSelectedIndex());
                    int selectedEntryIndex = entryOverview.getSelectionModel().getSelectedIndex();
                    if (selectedEntryIndex >= 0) {
                        Entry selectedEntry = table.getEntries().get(selectedEntryIndex);
                        try {
                            showFile(selectedEntry.getFile());
                        } catch (Exception e) {
                            Warning.show(e);
                        }
                    }
                }
            });
            return row;
        });
    }

    private void showFile(String file) throws Exception {
        if (file == null || file.equals("")) {
            throw new Exception("No attachments");
        }
        tableSelectedIndex.set(entryOverview.getSelectionModel().getSelectedIndex());
        if (tableSelectedIndex.get() >= 0) {
            Stage stage = new Stage();
            Scene scene = new Scene(new Group());
            stage.setTitle("File");
            stage.setWidth(500);
            stage.setHeight(500);
            stage.resizableProperty().setValue(false);

            TextArea textArea = new TextArea(file);
            textArea.setPrefSize(490, 470);
            textArea.setEditable(false);
            ((Group) scene.getRoot()).getChildren().add(textArea);

            stage.setScene(scene);
            stage.toFront();
            stage.show();
        } else {
            noSelectedMessage();
        }
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
        tableSelectedIndex.set(tableOverview.getSelectionModel().getSelectedIndex());
        if (tableSelectedIndex.get() >= 0) {
            try {
                entryOverview.getColumns().clear();
                tableService.deleteTable(tableSelectedIndex.get());
            } catch (Exception e) {
                Warning.show(e);
            }
        } else {
            noSelectedMessage();
        }
    }

    private void noSelectedMessage() {
        Warning.show("Nothing is selected.");
    }

    public void addRow_onClick(MouseEvent mouseEvent) {
        tableSelectedIndex.set(tableOverview.getSelectionModel().getSelectedIndex());
        if (tableSelectedIndex.get() >= 0) {
            Stage stage = new Stage();
            Controller controller = SpringFxmlLoader.load("/view/createRowInTable.fxml");
            Parent root = (Parent) controller.getView();
            stage.setTitle("Create row in table");
            int columnsAmount = tableService.getTables().get(tableOverview.getSelectionModel().getSelectedIndex()).getColumnNames().size();
            stage.setHeight(160.0 + columnsAmount*30.0);
            stage.setMinWidth(370);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)mouseEvent.getSource()).getScene().getWindow());
            stage.setOnHidden(we -> showEntries(tableService.getTable(tableSelectedIndex.get())));
            stage.show();
        } else {
            noSelectedMessage();
        }
    }

    public void editRow(Event event) {
        tableSelectedIndex.set(tableOverview.getSelectionModel().getSelectedIndex());
        entrySelectedIndex.set(entryOverview.getSelectionModel().getSelectedIndex());
        if (tableSelectedIndex.get() >= 0 && entrySelectedIndex.get() >=0) {
            Stage stage = new Stage();
            Controller controller = SpringFxmlLoader.load("/view/editRow.fxml");
            Parent root = (Parent) controller.getView();
            stage.setTitle("Edit row in table");
            int columnsAmount = tableService.getTables().get(tableOverview.getSelectionModel().getSelectedIndex()).getColumnNames().size();
            stage.setHeight(160.0 + columnsAmount*30.0);
            stage.setMinWidth(370);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.setOnHidden(we -> showEntries(tableService.getTable(tableSelectedIndex.get())));
            stage.show();
        } else {
            noSelectedMessage();
        }
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

    public void search_onClick(MouseEvent mouseEvent) {
        tableSelectedIndex.set(tableOverview.getSelectionModel().getSelectedIndex());
        if (tableSelectedIndex.get() >= 0) {
            Stage stage = new Stage();
            Controller controller = SpringFxmlLoader.load("/view/search.fxml");
            Parent root = (Parent) controller.getView();
            stage.setTitle("Search");
            int columnsAmount = tableService.getTables().get(tableOverview.getSelectionModel().getSelectedIndex()).getColumnNames().size();
            stage.setHeight(160.0 + columnsAmount*30.0);
            stage.setMinWidth(370);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)mouseEvent.getSource()).getScene().getWindow());
            stage.show();
        } else {
            noSelectedMessage();
        }
    }

    public IntegerProperty getEntrySelectedIndex() {
        return entrySelectedIndex;
    }

    public void setEntrySelectedIndex(IntegerProperty entrySelectedIndex) {
        this.entrySelectedIndex = entrySelectedIndex;
    }
}
package localdbms.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import localdbms.DBMS.Entry;
import localdbms.DBMS.Table;
import localdbms.DBMS.TypeManager;
import localdbms.DataType;
import localdbms.service.TableService;

import java.util.ArrayList;
import java.util.List;

public class SearchController extends Controller {

    private TableService tableService;
    private IntegerProperty tableIndex;

    @FXML
    public VBox fields;

    public void submit(MouseEvent mouseEvent) {
        List<String> textDataFromFields = getDataFromForm();
        try {
            List<Object> values = getObjectsByText(textDataFromFields);
            List<Integer> entryNums = tableService.search(tableIndex.get(), values);
            List<Entry> entriesForShow = new ArrayList<>();
            for (Integer entryNum : entryNums) {
                entriesForShow.add(tableService.getTable(tableIndex.get()).getEntries().get(entryNum));
            }
            ObservableList<Object> valuesForShow = FXCollections.observableArrayList();
            entriesForShow.forEach(entry -> valuesForShow.add(FXCollections.observableArrayList(entry.getValues())));
            showResult(valuesForShow);
        } catch (Exception e) {
            Warning.show(e);
        }
    }

    private void showResult(ObservableList<Object> result) {
        Stage stage = new Stage();
        Scene scene = new Scene(new Group());
        stage.setTitle("Results");
        stage.setWidth(500);
        stage.setHeight(500);
        stage.resizableProperty().setValue(false);

        TableView<Object> table = new TableView<>();
        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setMinWidth(500);
        table.setMinHeight(500);
        tableService.getTable(tableIndex.get()).getColumnNames().forEach(name -> addColumn(name, table));
        setValueFactories(table);
        table.setItems(result);

        ((Group) scene.getRoot()).getChildren().addAll(table);

        stage.setScene(scene);
        scene.getStylesheets().add((getClass().getResource("/view/styles.css")).toExternalForm());
        stage.show();
    }

    @SuppressWarnings("unchecked")
    private void setValueFactories(TableView<Object> table) {
        for (int columnNum = 0; columnNum < table.getColumns().size(); columnNum++) {
            final int finalColumnNum = columnNum;
            table.getColumns().get(finalColumnNum).setCellValueFactory(cell ->
                    new SimpleObjectProperty(((List<Object>)cell.getValue()).get(finalColumnNum)));
        }
    }

    private void addColumn(String name, TableView<Object> table) {
        table.setEditable(true);
        table.getColumns().add(new TableColumn<Object, String>(name));
    }

    private List<String> getDataFromForm() {
        List<String> dataFromTextFields = new ArrayList<>();
        for (Node node : fields.getChildren()) {
            List<Node> hBox = ((HBox) node).getChildren();
            if (!hBox.isEmpty()) {
                dataFromTextFields.add(((TextField) hBox.get(1)).getCharacters().toString());
            }
        }
        return dataFromTextFields;
    }

    private List<Object> getObjectsByText(List<String> data) throws NumberFormatException {
        List<Object> parsedObjects = new ArrayList<>();
        List<DataType> types = tableService.getTable(tableIndex.get()).getTypes();
        for (int column = 0; column < data.size(); column++) {
            DataType cellType = types.get(column);
            String cellData = data.get(column);
            if ("".equals(cellData)){
                parsedObjects.add(null);
            } else {
                try {
                    parsedObjects.add(TypeManager.parseObjectByType(cellData, cellType));
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Expected " + cellType + " value in " +
                            column + " column but " + cellData + " found");
                }
            }
        }
        return parsedObjects;
    }

    @FXML
    public void initialize() {
        Table table = tableService.getTable(tableIndex.get());
        for (int i = 0; i < table.getColumnNames().size(); i++) {
            addField(table.getColumnNames().get(i), table.getTypes().get(i));
        }
    }

    private void addField(String name, DataType type) {
        Label fieldName = new Label(name);
        fieldName.setPrefSize(180.0, 25.0);

        TextField textField = new TextField();
        textField.setPromptText(type.toString());
        textField.setPrefSize(195.0, 25.0);

        HBox hbox = new HBox();
        hbox.setSpacing(30.0);
        hbox.getChildren().addAll(fieldName, textField);

        fields.getChildren().add(hbox);
    }

    public void close(MouseEvent mouseEvent) {
        hide(mouseEvent);
    }

    public void setTableService(TableService tableService) {
        this.tableService = tableService;
    }

    public void setTableIndex(IntegerProperty tableIndex) {
        this.tableIndex = tableIndex;
    }
}

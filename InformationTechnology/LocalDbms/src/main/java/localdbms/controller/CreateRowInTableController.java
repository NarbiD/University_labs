package localdbms.controller;

import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import localdbms.database.DataType;
import localdbms.database.Database;
import localdbms.database.Table;
import localdbms.database.TypeChecker;
import localdbms.database.exception.StorageException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CreateRowInTableController extends AbstractController{

    @FXML
    public VBox Fields;

    @FXML
    public Button btnOk;

    @FXML
    public Button btnCancel;

    @FXML
    public HBox SubmissionButtons;

    @FXML
    public Button btnLoadPic;

    private ObservableList<Table> tables;
    private IntegerProperty tableIndex;
    private ObservableList<Database> databases;
    private File image;

    public void setDbIndex(IntegerProperty dbIndex) {
        this.dbIndex = dbIndex;
    }

    private IntegerProperty dbIndex;

    @FXML
    public void initialize() {
        Table table = tables.get(tableIndex.get());
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

        Fields.getChildren().add(hbox);
    }

    public void submit(MouseEvent mouseEvent)  {
        Table table = tables.get(tableIndex.get());
        List<String> dataFromFields = getDataFromForm();
        try {
            List<Object> values = getObjectsByText(dataFromFields);
            put(table, values);
            close(mouseEvent);
        } catch (NumberFormatException | StorageException e) {
            Warning.show(e);
        }
    }

    private List<String> getDataFromForm() {
        List<String> dataFromTextFields = new ArrayList<>();
        for (Node node : Fields.getChildren()) {
            List<Node> hBox = ((HBox) node).getChildren();
            if (!hBox.isEmpty()) {
                dataFromTextFields.add(((TextField) hBox.get(1)).getCharacters().toString());
            }
        }
        return dataFromTextFields;
    }

    private List<Object> getObjectsByText(List<String> data) throws NumberFormatException {
        List<Object> parsedObjects = new ArrayList<>();
        List<DataType> types = tables.get(tableIndex.get()).getTypes();
        for (int column = 0; column < data.size(); column++) {
            DataType cellType = types.get(column);
            String cellData = data.get(column);
            try {
                parsedObjects.add(TypeChecker.parseObjectByType(cellData, cellType));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Expected " + cellType + " value in " +
                        column + " column but " + cellData + " found");
            }
        }
        return parsedObjects;
    }

    private void put(Table table, List<Object> values) throws StorageException{
        if (image != null) {
            table.addRow(values, image);
        } else {
            table.addRow(values);
        }
        databases.get(dbIndex.get()).getTables().clear();
        databases.get(dbIndex.get()).getTables().addAll(tables);
        databases.get(dbIndex.get()).save();
    }

    public void close(MouseEvent mouseEvent) {
        hide(mouseEvent);
    }

    public void setTables(ObservableList<Table> tables) {
        this.tables = tables;
    }

    public void setTableIndex(IntegerProperty tableIndex) {
        this.tableIndex = tableIndex;
    }

    public void setDatabases(ObservableList<Database> databases) {
        this.databases = databases;
    }

    public void loadPic(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open image...");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*.png"));
        image = fileChooser.showOpenDialog(((Node) mouseEvent.getSource()).getScene().getWindow());
    }
}

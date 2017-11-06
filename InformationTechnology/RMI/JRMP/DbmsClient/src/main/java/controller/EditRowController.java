package controller;

import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import DBMS.Entry;
import DBMS.Table;
import DBMS.TypeManager;
import DBMS.DataType;
import service.TableService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class EditRowController extends Controller{

    private String file;
    private TableService tableService;
    private IntegerProperty tableIndex;
    private IntegerProperty entryIndex;

    @FXML
    public VBox fields;

    @FXML
    public void initialize() throws RemoteException {
        Table table = tableService.getTable(tableIndex.get());
        Entry entry = table.getEntries().get(entryIndex.get());
        for (int i = 0; i < table.getColumnNames().size(); i++) {
            addField(table.getColumnNames().get(i), table.getTypes().get(i), entry.getValues().get(i));
        }
    }

    private void addField(String name, DataType type, Object value) {
        Label fieldName = new Label(name);
        fieldName.setPrefSize(180.0, 25.0);

        TextField textField = new TextField();
        textField.setPromptText(type.toString());
        textField.textProperty().setValue(value.toString());
        textField.setPrefSize(195.0, 25.0);

        HBox hbox = new HBox();
        hbox.setSpacing(30.0);
        hbox.getChildren().addAll(fieldName, textField);

        fields.getChildren().add(hbox);
    }

    public void submit(MouseEvent mouseEvent)  {
        List<String> textDataFromFields = getDataFromForm();
        try {
            List<Object> values = getObjectsByText(textDataFromFields);
            String oldFile = tableService.getTable(tableIndex.get()).getEntries().get(entryIndex.get()).getFile();
            tableService.setRow(entryIndex.get(), tableIndex.get(), values, file != null ? file : oldFile);
            file = null;
            close(mouseEvent);
        } catch (Exception e) {
            Warning.show(e);
        }
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

    private List<Object> getObjectsByText(List<String> data) throws NumberFormatException, RemoteException {
        List<Object> parsedObjects = new ArrayList<>();
        List<DataType> types = tableService.getTable(tableIndex.get()).getTypes();
        for (int column = 0; column < data.size(); column++) {
            DataType cellType = types.get(column);
            String cellData = data.get(column);
            try {
                parsedObjects.add(TypeManager.parseObjectByType(cellData, cellType));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Expected " + cellType + " value in " +
                        column + " column but " + cellData + " found");
            }
        }
        return parsedObjects;
    }

    public void loadFile_onClick(MouseEvent mouseEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file...");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text File", "*.txt"));
        File filePath = fileChooser.showOpenDialog(((Node) mouseEvent.getSource()).getScene().getWindow());
        if (filePath != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                StringBuilder fileBuilder = new StringBuilder();
                reader.lines().forEach(str -> fileBuilder.append(str).append("\n"));
                file = fileBuilder.toString();
            }
        }
    }

    public void close(MouseEvent mouseEvent) {
        hide(mouseEvent);
    }

    public void setTableIndex(IntegerProperty tableIndex) {
        this.tableIndex = tableIndex;
    }

    public void setTableService(TableService tableService) {
        this.tableService = tableService;
    }

    public void setEntryIndex(IntegerProperty entryIndex) {
        this.entryIndex = entryIndex;
    }
}

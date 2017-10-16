package localdbms.controller;

import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import javafx.util.Pair;
import localdbms.database.*;
import localdbms.database.exception.EntryException;
import localdbms.database.exception.StorageException;
import java.util.List;


public class CreateTableController extends AbstractController{
    private IntegerProperty dbIndex;
    private ObservableList<Database> databases;
    private ObservableList<Table> tables;

    @FXML
    public VBox Fields;

    @FXML
    public TextField tableNameField;

    @FXML
    public Button btnAddField;

    @FXML
    public HBox SubmissionButtons;

    @FXML
    public Button btnCancel;

    @FXML
    public Button btnOk;

    public void submit(MouseEvent mouseEvent) {
        Database database = databases.get(dbIndex.get());
        try {
            Table table = database.createTable(tableNameField.getCharacters().toString());
            if (fillTableHeaderFromForm(table)) {
                tables.add(table);
                database.save();
                close(mouseEvent);
            } else {
                database.getTables().remove(table);
            }
        } catch (StorageException e) {
            Warning.show("Storage error!", e);
        }
    }

    private boolean fillTableHeaderFromForm(Table table) {
        try {
            Pair<List<String>, List<DataType>> tableData = getDataFromForm();
            table.setTypes(tableData.getValue());
            table.setColumnNames(tableData.getKey());
        } catch (IllegalArgumentException | EntryException e) {
            Warning.show(e);
            return false;
        }
        return true;
    }

    private Pair<List<String>, List<DataType>> getDataFromForm() throws EntryException {
        List<DataType> types = FXCollections.observableArrayList();
        List<String> names = FXCollections.observableArrayList();

        for (int i = 0; i < Fields.getChildren().size(); i++) {
            HBox hBox = (HBox)Fields.getChildren().get(i);
            DataType type = null;
            String name = "";
            for (Node node : hBox.getChildren()) {
                if (node instanceof ComboBox) {
                    type = (DataType)((ComboBox)node).getValue();
                } else if (node instanceof TextField) {
                    name = ((TextField)node).getCharacters().toString();
                }
            }
            boolean nameIsUndefined = name.equals("");
            boolean typeIsNull = type == null;
            if (nameIsUndefined && typeIsNull) {
                continue;
            } else if (nameIsUndefined || typeIsNull) {
                throw new IllegalArgumentException("It is necessary to fill in either both fields in the line, or none");
            } else {
                names.add(name);
                types.add(type);
            }
        }
        return new Pair<>(names, types);
    }

    public void addField(MouseEvent mouseEvent) {
        resizeWindow(mouseEvent);

        TextField textField = new TextField();
        textField.setPrefWidth(150.0);

        ComboBox<DataType> comboBox = new ComboBox<>(FXCollections.observableArrayList(DataType.values()));
        comboBox.setPrefWidth(150.0);

        HBox hbox = new HBox();
        hbox.setSpacing(30.0);
        hbox.getChildren().addAll(textField, comboBox);

        Fields.getChildren().add(hbox);
    }

    private void resizeWindow(MouseEvent mouseEvent) {
        SubmissionButtons.setLayoutY(SubmissionButtons.getLayoutY());
        Window window = ((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setHeight(window.getHeight() + 30);
    }

    @FXML
    public void close(MouseEvent mouseEvent) {
        hide(mouseEvent);
    }

    public void setDbIndex(IntegerProperty dbIndex) {
        this.dbIndex = dbIndex;
    }

    public void setTables(ObservableList<Table> tables) {
        this.tables = tables;
    }

    public void setDatabases(ObservableList<Database> databases) {
        this.databases = databases;
    }
}

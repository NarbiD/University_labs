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
import localdbms.database.*;
import localdbms.database.exception.EntryException;
import localdbms.database.exception.StorageException;

import java.util.ArrayList;
import java.util.List;


public class CreateTableController extends AbstractController{
    @FXML
    public VBox Fields;

    @FXML
    public HBox SubmissionButtons;

    private IntegerProperty dbIndex;
    private ObservableList<Database> databases;
    private ObservableList<Table> tables;

    @FXML
    public Button btnCancel;

    @FXML
    public Button btnOk;

    @FXML
    public TextField textField;

    @FXML
    public Button btn;

    @FXML
    public void submit(MouseEvent mouseEvent) throws StorageException {
        Database database = databases.get(dbIndex.get());
        Table table = database.createTable(textField.getCharacters().toString());
        List[] tableData = getData();
        table.setTypes(tableData[1]);
        table.setColumnNames(tableData[0]);
        tables.add(table);
        database.save();
        close(mouseEvent);
    }

    private List[] getData() throws EntryException {
        List<DataType> types = FXCollections.observableArrayList();
        List<Object> names = FXCollections.observableArrayList();
        for (int i = 0; i < Fields.getChildren().size(); i++) {
            HBox hBox = (HBox)Fields.getChildren().get(i);
            for (Node node : hBox.getChildren()) {
                if (node instanceof ComboBox) {
                    types.add((DataType)((ComboBox)node).getValue());
                } else if (node instanceof TextField) {
                    names.add(((TextField)node).getCharacters().toString());
                }
            }
        }
        return new List[]{names, types};
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

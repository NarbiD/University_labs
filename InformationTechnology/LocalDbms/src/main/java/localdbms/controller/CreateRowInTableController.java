package localdbms.controller;

import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import localdbms.database.DataType;
import localdbms.database.Table;

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
    public AnchorPane Stage;

    private ObservableList<Table> tables;
    private IntegerProperty tableIndex;

    @FXML
    public void initialize() {
        Table table = tables.get(tableIndex.get());
        for (int i = 0; i < table.getColumnNames().size(); i++) {
            addField(table.getColumnNames().get(i), table.getTypes().get(i));
        }
    }

    private void addField(String name, DataType type) {
        resizeWindow();

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

    private void resizeWindow() {
        SubmissionButtons.setLayoutY(SubmissionButtons.getLayoutY());
//        Window window = Stage.getScene().getWindow();
//        window.setHeight(window.getHeight() + 30);
    }

    public void submit(MouseEvent mouseEvent) {
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
}

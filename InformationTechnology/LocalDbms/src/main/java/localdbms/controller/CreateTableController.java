package localdbms.controller;

import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import localdbms.SpringFxmlLoader;
import localdbms.database.DataType;
import localdbms.database.Database;
import localdbms.database.Table;
import localdbms.database.exception.StorageException;


public class CreateTableController extends AbstractController{
    @FXML
    public VBox Fields;
    public HBox layoutBtns;

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


    public void btnClick(MouseEvent mouseEvent) {
        addField();
        double layoutY = layoutBtns.getLayoutY();
        layoutBtns.setLayoutY(layoutY);
        Double winHeight = ((Node)mouseEvent.getSource()).getScene().getWindow().getHeight();
        ((Node) mouseEvent.getSource()).getScene().getWindow().setHeight(winHeight + 30);
    }

    @FXML
    public void submit(MouseEvent mouseEvent) throws StorageException {
        Database database = databases.get(dbIndex.get());
        Table g = database.createTable(textField.getCharacters().toString());
        tables.add(g);
        database.save();
        close(mouseEvent);
    }

    private void addField() {
        ComboBox<DataType> comboBox = new ComboBox<>();
        comboBox.setPrefWidth(150.0);
        TextField textField = new TextField();
        textField.prefWidth(150.0);
        HBox hbox = new HBox();
        hbox.setSpacing(30.0);
        hbox.getChildren().addAll(textField, comboBox);
        Fields.getChildren().add(hbox);
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

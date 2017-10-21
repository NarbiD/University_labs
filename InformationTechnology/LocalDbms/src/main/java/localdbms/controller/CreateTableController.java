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
import localdbms.database.exception.TableException;

import java.util.List;


public class CreateTableController extends AbstractController{

    private IntegerProperty dbIndex;
    private ObservableList<Database> databases;
    private ObservableList<Table> tables;

    @FXML
    public HBox Constraints;

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
            Table table = createTableByForm(database);
            tables.add(table);
            database.save();
            close(mouseEvent);
        } catch (StorageException | IllegalArgumentException e) {
            Warning.show(e);
        }
    }

    private Table createTableByForm(Database database) throws IllegalArgumentException, StorageException {
        String tableName = tableNameField.getCharacters().toString();
        if (tableName.equals("")) {
            throw new IllegalArgumentException("Required name for the table");
        }
        Table table = database.createTable(tableName);
        FromData formData = getDataFromForm();
        table.setTypes(formData.types);
        table.setColumnNames(formData.names);
        table.setConstraint(formData.constraint);
        return table;
    }

    private FromData getDataFromForm() throws IllegalArgumentException {
        List<DataType> types = FXCollections.observableArrayList();
        List<String> names = FXCollections.observableArrayList();
        for (int i = 0; i < Fields.getChildren().size(); i++) {
            Pair<String, DataType> dataFromLine = getDataFromHBox((HBox)Fields.getChildren().get(i));
            String name = dataFromLine.getKey();
            DataType type = dataFromLine.getValue();
            if (!shouldBeIgnored(name, type)){
                names.add(name);
                types.add(type);
            }
        }
        if (names.isEmpty()) {
            throw new IllegalArgumentException("You must create at least one column");
        }
        RealConstraint constraint = getConstraintsFromForm();
        return new FromData(names, types, constraint);
    }

    private static class FromData {
        List<DataType> types;
        List<String> names;
        RealConstraint constraint;

        FromData(List<String> names, List<DataType> types, RealConstraint constraint) {
            this.names = names;
            this.types = types;
            this.constraint = constraint;
        }
    }

    private Pair<String, DataType> getDataFromHBox(HBox hBox) {
        DataType type = null;
        String name = "";
        for (Node node : hBox.getChildren()) {
            if (node instanceof ComboBox) {
                type = (DataType)((ComboBox)node).getValue();
            } else if (node instanceof TextField) {
                name = ((TextField)node).getCharacters().toString();
            }
        }
        return new Pair<>(name, type);
    }

    private boolean shouldBeIgnored (String name, DataType type) throws IllegalArgumentException {
        boolean nameIsDefined = !name.equals("");
        boolean typeIsNotNull = type != null;
        if (nameIsDefined && typeIsNotNull) {
            return false;
        } else if (nameIsDefined || typeIsNotNull) {
            throw new IllegalArgumentException("It is necessary to fill in either both fields in the line, or none");
        } else {
            return true;
        }
    }

    private RealConstraint getConstraintsFromForm() throws IllegalArgumentException{
        RealConstraint constraint;
        String minValueString = ((TextField) Constraints.getChildren().get(0)).getCharacters().toString();
        String maxValueString = ((TextField) Constraints.getChildren().get(1)).getCharacters().toString();
        if (maxValueString.equals(minValueString) && maxValueString.equals("")) {
            constraint = new RealConstraint();
        } else if (maxValueString.equals("") || minValueString.equals("")){
            throw new IllegalArgumentException("It is necessary to fill in either both constraint fields in the line, or none");
        } else {
            Double minValue = Double.valueOf(minValueString);
            Double maxValue = Double.valueOf(maxValueString);
            constraint = new RealConstraint(minValue, maxValue);
        }
        return constraint;
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

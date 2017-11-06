package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import javafx.util.Pair;
import DBMS.IntegerInvlConstraint;
import DBMS.DataType;
import service.TableService;

import java.util.List;


public class CreateTableController extends Controller{

    public HBox constraints;
    private TableService tableService;

    @FXML
    public VBox fields;

    @FXML
    public TextField tableNameField;

    @FXML
    public HBox submissionButtons;

    public void submit(MouseEvent mouseEvent) {
        try {
            createTableByForm();
            close(mouseEvent);
        } catch (Exception e) {
            Warning.show(e);
        }
    }

    private void createTableByForm() throws Exception {
        FromData formData = getDataFromForm();
        tableService.createTable(formData.tableName, formData.types, formData.names, formData.constraint);
    }

    private FromData getDataFromForm() throws IllegalArgumentException {
        String tableName = tableNameField.getCharacters().toString();
        List<DataType> types = FXCollections.observableArrayList();
        List<String> names = FXCollections.observableArrayList();
        for (int i = 0; i < fields.getChildren().size(); i++) {
            Pair<String, DataType> dataFromLine = getDataFromHBox((HBox) fields.getChildren().get(i));
            String name = dataFromLine.getKey();
            DataType type = dataFromLine.getValue();
            if (!shouldBeIgnored(name, type)){
                names.add(name);
                types.add(type);
            }
        }
        IntegerInvlConstraint constraint = getConstraintsFromForm();
        return new FromData(tableName, names, types, constraint);
    }

    private IntegerInvlConstraint getConstraintsFromForm() throws IllegalArgumentException{
        IntegerInvlConstraint constraint;
        String minValueString = ((TextField) constraints.getChildren().get(0)).getCharacters().toString();
        String maxValueString = ((TextField) constraints.getChildren().get(1)).getCharacters().toString();
        if (maxValueString.equals(minValueString) && maxValueString.equals("")) {
            constraint = new IntegerInvlConstraint();
        } else if (maxValueString.equals("") || minValueString.equals("")){
            throw new IllegalArgumentException("It is necessary to fill in either both constraint fields in the line, or none");
        } else {
            Integer minValue = Integer.valueOf(minValueString);
            Integer maxValue = Integer.valueOf(maxValueString);
            constraint = new IntegerInvlConstraint(minValue, maxValue);
        }
        return constraint;
    }

    private static class FromData {
        String tableName;
        List<DataType> types;
        List<String> names;
        IntegerInvlConstraint constraint;

        FromData(String tableName, List<String> names, List<DataType> types, IntegerInvlConstraint constraint) {
            this.tableName = tableName;
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

    public void addField(MouseEvent mouseEvent) {
        resizeWindow(mouseEvent);

        TextField textField = new TextField();
        textField.setPrefWidth(150.0);

        ComboBox<DataType> comboBox = new ComboBox<>(FXCollections.observableArrayList(DataType.values()));
        comboBox.setPrefWidth(150.0);

        HBox hbox = new HBox();
        hbox.setSpacing(30.0);
        hbox.getChildren().addAll(textField, comboBox);

        fields.getChildren().add(hbox);
    }

    private void resizeWindow(MouseEvent mouseEvent) {
        submissionButtons.setLayoutY(submissionButtons.getLayoutY());
        Window window = ((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setHeight(window.getHeight() + 30);
    }

    @FXML
    public void close(MouseEvent mouseEvent) {
        hide(mouseEvent);
    }

    public void setTableService(TableService tableService) {
        this.tableService = tableService;
    }
}

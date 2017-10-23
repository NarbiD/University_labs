package localdbms.controller;

import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import localdbms.DataType;
import localdbms.DBMS.table.Table;
import localdbms.DBMS.datatype.TypeManager;
import localdbms.DBMS.exception.StorageException;
import localdbms.service.TableService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public void setTableService(TableService tableService) {
        this.tableService = tableService;
    }

    private BufferedImage image;
    private TableService tableService;
    private IntegerProperty tableIndex;

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

        Fields.getChildren().add(hbox);
    }

    public void submit(MouseEvent mouseEvent)  {
        List<String> textDataFromFields = getDataFromForm();
        try {
            List<Object> values = getObjectsByText(textDataFromFields);
            tableService.addRow(tableIndex.get(), values, image);
            image = null;
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

    public void close(MouseEvent mouseEvent) {
        hide(mouseEvent);
    }

    public void setTableIndex(IntegerProperty tableIndex) {
        this.tableIndex = tableIndex;
    }

    public void loadImage(MouseEvent mouseEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open image...");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*.png"));
        File imagePath = fileChooser.showOpenDialog(((Node) mouseEvent.getSource()).getScene().getWindow());
        image = ImageIO.read(imagePath);
    }
}

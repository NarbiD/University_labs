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
import common.DataType;
import DBMS.table.Table;
import DBMS.datatype.TypeManager;
import service.TableService;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class CreateRowInTableController extends AbstractController{

    private BufferedImage image;
    private TableService tableService;
    private IntegerProperty tableIndex;

    @FXML
    public VBox fields;

    @FXML
    public void initialize() throws RemoteException {
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

    public void submit(MouseEvent mouseEvent)  {
        List<String> textDataFromFields = getDataFromForm();
        try {
            List<Object> values = getObjectsByText(textDataFromFields);
            String encodedImage = "";
            if (image != null) {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(image, "png", os);
                encodedImage = Base64.getEncoder().encodeToString(os.toByteArray());
            }
            tableService.addRow(tableIndex.get(), values, encodedImage);
            image = null;
            close(mouseEvent);
        } catch (NumberFormatException | IOException e) {
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

    public void loadImage_onClick(MouseEvent mouseEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open image...");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*.png"));
        File imagePath = fileChooser.showOpenDialog(((Node) mouseEvent.getSource()).getScene().getWindow());
        image = ImageIO.read(imagePath);
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
}

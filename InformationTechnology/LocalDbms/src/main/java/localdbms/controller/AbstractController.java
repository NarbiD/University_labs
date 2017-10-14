package localdbms.controller;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public abstract class AbstractController implements Controller {
    private Node view;

    public Node getView() {
        return view;
    }

    public void setView (Node view){
        this.view = view;
    }

    void hide(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }
}
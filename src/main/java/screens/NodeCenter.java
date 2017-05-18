package screens;

import datastructure.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Implementation of the state that handles click events regarding the center query of selected node.
 */
public class NodeCenter implements INodeHandler {
    private GraphScene graphScene;


    public NodeCenter(GraphScene sc) {
        this.graphScene = sc;

    }

    public void handle(Node node) {
        Stage stage = new Stage();
        stage.setTitle("Select the radius");
        VBox box = new VBox();
        TextField textField = new TextField();
        Button btn = new Button("Submit");
        btn.setOnAction(
                event -> {
                    Window.getInfoScreen().getTextArea().appendText("Node id: " + node.getId() + "\n");
                    Window.getInfoScreen().getTextArea().appendText("Radius: " + textField.getText() + "\n");
                    graphScene.switchToInfo();
                    stage.close();
                }
        );
        box.getChildren().addAll(textField, btn);
        Scene scene = new Scene(box, 70.0, 50.0);
        stage.setScene(scene);
        stage.show();
    }

    public void createStage() {

    }
}

package screens;

import datastructure.DrawNode;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Implementation of the state that handles click events regarding the center query of selected node.
 */
public class NodeCenter implements INodeHandler {

    /**
     * Screen that displays the graph.
     */
    private GraphScene graphScene;

    private FXElementsFactory fxElementsFactory;

    /**
     * Constructor.
     * @param sc Scene for displaying graphs.
     */
    public NodeCenter(GraphScene sc, FXElementsFactory fact) {
        this.graphScene = sc;
        this.fxElementsFactory = fact;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(DrawNode node) {
        Stage stage = fxElementsFactory.createStage();
        VBox box = new VBox();
        Label label = fxElementsFactory.createLabel("Enter radius:");
        TextField textField = new TextField();
        Button btn = new Button("Submit");
        btn.setOnAction(
                event -> {
                    graphScene.drawGraph(node.getIndex(), Integer.parseInt(textField.getText()));
                    graphScene.switchToInfo();
                    stage.close();
                }
        );
        box.getChildren().addAll(label, textField, btn);
        Group group = fxElementsFactory.createGroup();
        group.getChildren().add(box);
        Scene scene = fxElementsFactory.createScene(group, 100, 100);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}

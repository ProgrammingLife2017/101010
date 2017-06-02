package screens.scenehandler;

import datastructure.DrawNode;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import screens.FXElementsFactory;
import screens.GraphScene;

/**
 * Implementation of the state that handles click events regarding the center query of selected node.
 */
public class NodeCenter implements INodeHandler {

    /**
     * Screen that displays the graph.
     */
    private GraphScene graphScene;

    private FXElementsFactory fxElementsFactory;

    private VBox box;

    private Stage stage;

    private Group group;

    /**
     * Constructor.
     * @param sc Scene for displaying graphs.
     * @param fact factory for creating JavaFX components.
     */
    public NodeCenter(GraphScene sc, FXElementsFactory fact) {
        this.graphScene = sc;
        this.fxElementsFactory = fact;
        this.stage = fxElementsFactory.createStage();
        this.box = new VBox();
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(DrawNode node) {
        Group group = fxElementsFactory.createGroup();
        resetSubContainer(stage, node);
        group.getChildren().add(box);
        Scene scene = fxElementsFactory.createScene(group, 100, 100);
        fxElementsFactory.setScene(stage, scene);
        stage.setResizable(false);
        fxElementsFactory.show(stage);
    }


    private void resetSubContainer(Stage stage, DrawNode drawNode) {
        box.getChildren().clear();
        Label label = fxElementsFactory.createLabel("Enter radius:");
        TextField textField = new TextField();
        Button btn = new Button("Submit");
        btn.setOnAction(
                event -> {
                    graphScene.drawGraph(drawNode.getIndex(), Integer.parseInt(textField.getText()));
                    graphScene.switchToInfo();
                    stage.close();
                }
        );
        box.getChildren().addAll(label, textField, btn);
    }

}

package screens;

import datastructure.DrawNode;
import datastructure.Node;
import datastructure.NodeGraph;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Implementation of the window that handles graph visualization.
 */
 /*package*/ class GraphScene extends Pane {

    private final INodeHandler center;
    private final INodeHandler info;
    private INodeHandler state;
     /**
     * Event handler for when a node or edge is clicked.
     */
     private EventHandler<MouseEvent> click = event -> {

        if (event.getSource() instanceof DrawNode) {
            DrawNode rect = (DrawNode) (event.getSource());
            state.handle(rect);
        } else if (event.getSource() instanceof Line) {
            Line l = (Line) (event.getSource());
            String edgeNodes = l.getId();
            System.out.println(edgeNodes);
            Window.getInfoScreen().getTextArea().appendText("Edge from node " + edgeNodes.substring(0, edgeNodes.indexOf("-")) + " to " + edgeNodes.substring(edgeNodes.indexOf("-") + 1, edgeNodes.length()) + "\n");
        }
     };

     /*package*/ GraphScene() {
         center = new NodeCenter(this);
         info = new NodeInfo(this);
         state = info;
     }

    public void drawGraph(final int id, final int radius) {
        if (radius < 5 || radius > 500) {
            Stage newStage = new Stage();
            Group group = new Group();
            Label label = new Label("Radius is out of bounds");
            group.getChildren().add(label);
            Scene scene = new Scene(group, 150, 100);
            newStage.setScene(scene);
            newStage.show();
            return;
        }
        this.getChildren().clear();
        drawGraphUtil(NodeGraph.getCurrentInstance().getNode(id), radius);
    }

    /**
     * The recursive method used to draw all nodes within the radius from the center node.
     * @param center The node to take as center.
     * @param radius The maximum depth we want to go.
     */
    private void drawGraphUtil(Node center, int radius) {
        NodeGraph.getCurrentInstance().generateDrawNodes(5, radius);
    }

    public void switchToCenter() { state = center; }

    public void switchToInfo() {
        state = info;
    }

}

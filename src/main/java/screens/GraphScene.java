package screens;

import datastructure.DrawNode;
import datastructure.Node;
import datastructure.NodeGraph;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.HashSet;
import java.util.Set;

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

    public void drawGraph() {
        this.getChildren().clear();
        Set<Node> visited = new HashSet<>();
        int depth = 0;

        drawGraphUtil(NodeGraph.getCurrentInstance().getNode(0), 200);
    }

    /**
     * The recursive method used to draw all nodes within the radius from the center node.
     * @param center The node to take as center.
     * @param radius The maximum depth we want to go.
     */
    private void drawGraphUtil(Node center, int radius) {
        double x = center.getX();
        Node test = NodeGraph.getCurrentInstance().getNode(1);
        for (int i = 0; i < NodeGraph.getCurrentInstance().getSize(); i++) {
            Node current = NodeGraph.getCurrentInstance().getNode(i);
            if (current.getX() >= x - 40 * radius && current.getX() <= x + 40 * radius) {
                DrawNode newRect = new DrawNode(i);
                newRect.setId(Integer.toString(i));
                newRect.setOnMousePressed(click);
                newRect.setX(current.getX());
                newRect.setY(current.getY());
                newRect.setWidth(20);
                newRect.setHeight(10);
                this.getChildren().add(newRect);
                for (Integer j: current.getOutgoingEdges()) {
                    Node out = NodeGraph.getCurrentInstance().getNode(j);
                    Line l = new Line();
                    l.setId(i + "-" + j);
                    l.setStrokeWidth(2);
                    l.setStartX(newRect.getBoundsInLocal().getMaxX());
                    l.setStartY(newRect.getBoundsInLocal().getMinY() + 5);
                    l.setEndX(out.getX());
                    l.setEndY(out.getY() + 5);
                    l.setOnMousePressed(click);
                    this.getChildren().add(l);
                }
            }
        }
    }

    public void switchToCenter() { state = center; }

    public void switchToInfo() {
        state = info;
    }

}

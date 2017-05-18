package screens;

import datastructure.Node;
import datastructure.NodeGraph;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Pair;

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

        if (event.getSource() instanceof Node) {
            Node rect = (Node) (event.getSource());
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

        drawGraphUtil(visited, NodeGraph.getCurrentInstance().getNode(4), 4, depth, new Pair<>((this.getWidth() / 2) - 115, (this.getHeight() / 2)), true, 0);
    }

    /**
     * The recursive method used to draw all nodes within the radius from the center node.
     * @param visited Set of visited nodes we do not need to visit again.
     * @param current The current node we want to draw.
     * @param radius The maximum depth we want to go.
     * @param depth The current depth we are on.
     * @param location The current location we are drawing on.
     * @param direction True if we went from parent to child and false visa versa.
     * @param child Child counter.
     */
    private void drawGraphUtil(Set<Node> visited, Node current, int radius, int depth, Pair<Double, Double> location, boolean direction, int child) {
        if (depth <= radius && !visited.contains(current)) {
            if (direction) {
                location = new Pair<>(location.getKey() + 100, location.getValue() + child * 40);
            } else {
                location = new Pair<>(location.getKey() - 100, location.getValue() + child * 40);
            }
            NodeGraph ng = NodeGraph.getCurrentInstance();
            System.out.println(Integer.toString(NodeGraph.getCurrentInstance().indexOf(current)));
            current.setId(Integer.toString(NodeGraph.getCurrentInstance().indexOf(current)));
            current.setOnMousePressed(click);
            current.setX(location.getKey());
            current.setY(location.getValue());
            current.setWidth(50);
            current.setHeight(10);

            this.getChildren().add(current);
            visited.add(current);

            this.applyCss();
            this.layout();
            child = 0;
            for (Integer i : current.getOutgoingEdges()) {
                drawGraphUtil(visited, NodeGraph.getCurrentInstance().getNode(i), radius, depth + 1, location, true, child);
                if (depth != radius) {
                    Line l = new Line();
                    l.setOnMousePressed(click);
                    l.setId(Integer.toString(NodeGraph.getCurrentInstance().indexOf(current)) + "-" + Integer.toString(i));
                    l.setStartX(location.getKey() + 25);
                    l.setStartY(location.getValue() + 5);
                    l.setEndX(this.lookup("#" + Integer.toString(i)).getBoundsInLocal().getMinX() + 25);
                    l.setEndY(this.lookup("#" + Integer.toString(i)).getBoundsInLocal().getMinY() + 5);
                    this.getChildren().add(l);
                    child += 1;
                }
            }
            child = 0;
            for (Integer i : current.getIncomingEdges()) {
                drawGraphUtil(visited, NodeGraph.getCurrentInstance().getNode(i), radius, depth + 1, location, false, child);
                child += 1;
            }
        }
    }

//    public void switchHandler(INodeHandler handler) {
//        if (handler == radius) {
//            state = info;
//        } else {
//            state = radius;
//        }
//    }

    public void switchToCenter() {
        state = center;
    }

    public void switchToInfo() {
        state = info;
    }

   // public INodeHandler getState() { return state; }
}

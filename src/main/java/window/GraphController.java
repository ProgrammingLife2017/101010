package window;

import datastructure.Node;

import datastructure.NodeGraph;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.Set;
import java.util.HashSet;

/**
 * Created by 101010 on 14-5-2017.
 */
public class GraphController {
    /**
     * NodeGraph containing all nodes.
     */
    private NodeGraph graph;

    /**
     * Pane upon we draw the nodes.
     */
    private Pane pane;

    /**
     * Constructor for creating an GraphController object.
     * @param newGraph The graph we want to draw.
     * @param mainPane The pain upon we want to draw.
     */
    public GraphController(NodeGraph newGraph, Pane mainPane) {
        this.graph = newGraph;
        this.pane = mainPane;
    }

    /**
     * Draw method for drawing the graph.
     * @param center The node that will be the center of the drawn graph.
     * @param radius The maximum depth we want to draw.
     */
    public void drawGraph(int center, int radius) {
        Set<Node> visited = new HashSet<>();
        int depth = 0;

        drawGraphUtil(visited, graph.getNode(center), radius, depth, new Pair<>((pane.getWidth() / 2) - 115, (pane.getHeight() / 2)), true);
    }

    /**
     * The recursive method used to draw all nodes within the radius from the center node.
     * @param visited Set of visited nodes we do not need to visit again.
     * @param current The current node we want to draw.
     * @param radius The maximum depth we want to go.
     * @param depth The current depth we are on.
     * @param location The current location we are drawing on.
     * @param direction True if we went from parent to child and false visa versa.
     */
    private void drawGraphUtil(Set<Node> visited, Node current, int radius, int depth, Pair<Double, Double> location, boolean direction) {
        if (depth <= radius && !visited.contains(current)) {
            if (direction) {
                location = new Pair<>(location.getKey() + 100, location.getValue());
            } else {
                location = new Pair<>(location.getKey() - 100, location.getValue());
            }

            current.setX(location.getKey());
            current.setY(location.getValue());

            pane.getChildren().add(current);

            visited.add(current);

            for (Integer i : current.getOutgoingEdges()) {
                drawGraphUtil(visited, graph.getNode(i), radius, depth + 1, location, true);
            }
            for (Integer i : current.getIncomingEdges()) {
                drawGraphUtil(visited, graph.getNode(i), radius, depth + 1, location, false);
            }
        }
    }
}

package window;

import datastructure.Node;

import datastructure.NodeGraph;
import javafx.scene.layout.Pane;

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
        Set<Integer> visited = new HashSet<>();
        int depth = 0;

        drawGraphUtil(visited, graph.getNode(center), radius, depth);
    }

    /**
     * The recursive method used to draw all nodes within the radius from the center node.
     * @param visited Set of visited nodes we do not need to visit again.
     * @param current The current node we want to draw.
     * @param radius The maximum depth we want to go.
     * @param depth The current depth we are on.
     */
    private void drawGraphUtil(Set<Integer> visited, Node current, int radius, int depth) {
        if (depth <= radius && !visited.contains(Integer.parseInt(current.getId()))) {
            //TODO draw current on its location.

            visited.add(Integer.parseInt(current.getId()));

            for (Integer i : current.getOutgoingEdges()) {
                drawGraphUtil(visited, graph.getNode(i), radius, depth + 1);
            }
            for (Integer i : current.getIncomingEdges()) {
                drawGraphUtil(visited, graph.getNode(i), radius, depth + 1);
            }
        }
    }
}

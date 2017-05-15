package Window;

import datastructure.Graph;
import datastructure.Node;

import javafx.scene.layout.Pane;

import java.util.*;


/**
 * Created by 101010 on 14-5-2017.
 */
public class GraphController {
    private Graph graph;
    private Pane pane;

    public GraphController(Graph graph, Pane mainPane) {
        this.graph = graph;
        this.pane = mainPane;
    }

    public void drawGraph(int center, int radius) {
        Set<Integer> visited = new HashSet<>();
        int depth = 0;

        drawGraphUtil(visited, graph.getNodes().get(center), radius, depth);
    }

    private void drawGraphUtil(Set<Integer> visited, Node current, int radius, int depth) {
        if (depth <= radius && !visited.contains(current)) {
            //TODO draw current on its location.

            visited.add(Integer.parseInt(current.getId()));

            for(Integer i : current.getOutgoingEdges()) {
                drawGraphUtil(visited, graph.getNodes().get(i), radius, depth + 1);
            }
            for(Integer i : current.getIncomingEdges()) {
                drawGraphUtil(visited, graph.getNodes().get(i), radius, depth + 1);
            }
        }
    }
}

package Window;

import datastructure.Graph;
import datastructure.Node;

import java.util.*;


/**
 * Created by 101010 on 14-5-2017.
 */
public class GraphController {
    private Graph graph;

    public GraphController(Graph graph) {
        this.graph = graph;
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

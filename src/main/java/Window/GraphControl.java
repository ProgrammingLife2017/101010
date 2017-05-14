package Window;

import datastructure.Node;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.Set;
import java.util.HashSet;


/**
 * Created by Martijn on 14-5-2017.
 */
public class GraphControl {
    private Stack<Node> sortedNodes;

    protected List<Node> depthFirstSort(Queue<Node> nodes) {
        Node current;
        while (!nodes.isEmpty()) {
            current = nodes.poll();
            visit(current, new HashSet<Node>(), new HashSet<Node>());
        }
        return null;
    }

    private void visit(Node visiting, Set<Node> tempMarks, Set<Node> visited) {
        if (tempMarks.contains(visiting)) {
            return;
        }
        if (!visited.contains(visited)) {
            tempMarks.add(visiting);
            for (Node n : visiting.getOutgoingEdges()) { //getOutgoingEdges moet nodes ipv int gaan returnen
                visit(n, tempMarks, visited);
            }
            tempMarks.remove(visiting);
            visited.add(visiting);
            sortedNodes.push(visiting);
        }
    }
}

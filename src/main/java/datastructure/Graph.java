package datastructure;

import java.util.*;

/**
 * Created by 101010 on 14-5-2017.
 */
public class Graph {
    private HashMap<Integer, Node> nodes;

    public Graph() {
        nodes = new HashMap<>();
    }

    public void addNode(Node node) {
        nodes.put(Integer.parseInt(node.getId()), node);
    }

    public HashMap<Integer, Node> getNodes() {
        return this.nodes;
    }
}

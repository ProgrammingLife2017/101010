package datastructure;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by 101010.
 */
public class NodeGraph {
    /**
     * List of nodes.
     */
    private ArrayList<Integer> nodes;

    /**
     * Set of edges.
     */
    private HashSet<Edge> edges;

    /**
     * Database containing the segments of the nodes.
     */
    private SegmentDB segmentDB;


    /**
     * Standard constructor for NodeGraph.
     * @param nodes The set of nodes.
     * @param edges The set of edges.
     * @param segmentDB The database containing all segments of the nodes.
     */
    public NodeGraph(ArrayList<Integer> nodes, HashSet<Edge> edges, SegmentDB segmentDB) {
        this.nodes = nodes;
        this.edges = edges;
        this.segmentDB = segmentDB;
    }

    /**
     * Retrieve the length of a node.
     * @param id The id of the node.
     * @return The length of the node.
     */
    public Integer getNodeLength(int id) {
        return nodes.get(id);
    }
}

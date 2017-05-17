package datastructure;

import java.util.ArrayList;

/**
 * Created by 101010.
 */
public class NodeGraph {
    /**
     * List of nodes.
     */
    private ArrayList<Node> nodes;

    /**
     * Database containing the segments of the nodes.
     */
    private SegmentDB segments;

    /**
     * Empty constructor for NodeGraph.
     */
    public NodeGraph() {
        this.nodes = new ArrayList<Node>(0);
        segments = new SegmentDB();
    }

    /**
     * The amount of nodes in the graph.
     */
    private int size;

    /**
     * Constructor for NodeGraph
     * @param nodes
     *        The list of nodes for the graph.
     * @param segments
     *        The database containing the segments of the nodes.
     */
    public NodeGraph(final ArrayList<Node> nodes, final SegmentDB segments) {
        this.nodes = nodes;
        this.segments = segments;
        size = 0;
    }

    /**
     * Adds a node to the graph.
     * @param id The id of the node.
     * @param node The node that gets added.
     */
    public void addNode(int id, Node node) {
        while (nodes.size() <= id) {
            nodes.add(new Node());
        }

        size++;
        int[] temp = nodes.get(id).getIncomingEdges();
        nodes.set(id, node);
        nodes.get(id).setIncomingEdges(temp);
    }

    public void addNodeCache(int id, Node node) {
        while (nodes.size() <= id) {
            nodes.add(new Node());
        }
        nodes.set(id, node);
        size++;
    }

    /**
     * Adds an edge to the graph.
     * @param from Origin node of the edge.
     * @param to Destination node of the edge.
     */
    public void addEdge(final int from, final int to) {
        while (nodes.size() <= from || nodes.size() <= to) {
            nodes.add(new Node());
        }

        nodes.get(from).addOutgoingEdge(to);
        nodes.get(to).addIncomingEdge(from);
    }

    /**
     * Retrieves a segment of a node
     * from the database.
     * @param id The id of the node.
     * @return The segment of the node.
     */
    public String getSegment(int id) {
        return segments.getSegment(id);
    }

    /**
     * Returns the node corresponding to the provided id.
     * @param id The id of the node.
     * @return The node.
     */
    public Node getNode(int id) {
        return nodes.get(id);
    }

    /**
     * Returns the amount of nodes.
     * @return the size of the graph.
     */
    public int getSize() { return size; }

    public void setSegmentDB(SegmentDB db) {
        segments = db;
    }
}

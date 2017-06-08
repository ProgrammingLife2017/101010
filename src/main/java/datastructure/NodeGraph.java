package datastructure;

import java.util.HashSet;

/**
 * Created by 101010.
 */
public class NodeGraph {
    /**
     * List of nodes.
     */
    private int[] nodes;

    /**
     * Set of edges.
     */
    private HashSet<Edge> edges;

    /**
     * Database containing the segments of the nodes.
     */
    private SegmentDB segmentDB;

    /**
     * Empty constructor for NodeGraph.
     */
    public NodeGraph() {
        this.nodes = new int[0];
        this.edges = new HashSet<>();
        this.segmentDB = new SegmentDB();
    }

    /**
     * Standard constructor for NodeGraph.
     * @param nodes The set of nodes.
     * @param edges The set of edges.
     * @param segmentDB The database containing all segments of the nodes.
     */
    public NodeGraph(int[] nodes, HashSet<Edge> edges, SegmentDB segmentDB) {
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
        try {
            return nodes[id];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Tried getting the length of non existent node: " + id);
        } finally {
            return -1;
        }
    }

    /**
     * Sets the length of a node.
     * @param id The id of the node.
     * @param length The length of the node.
     */
    public void setNodeLength(int id, int length) {
        try {
            nodes[id] = length;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Tried setting the length of non existent node: " + id);
        }
    }

    /**
     * Retrieve the segment of a node.
     * @param id The id of the node.
     * @return The segment of the node.
     */
    public String getNodeSegment(int id) {
        return segmentDB.getSegment(id);
    }

    /**
     * Sets the segmentDB of the NodeGraph.
     * @param newDB The new SegmentDB.
     */
    public void setSegmentDB(SegmentDB newDB) {
        segmentDB = newDB;
    }

    /**
     * Retrieve the amount of nodes in the NodeGraph.
     * @return The amount of nodes in the NodeGraph.
     */
    public int getNodesSize() {
        return nodes.length;
    }

    /**
     * Retrieve the amount of edges in the NodeGraph.
     * @return The amount of edges in the NodeGraph.
     */
    public int getEdgesSize() {
        return edges.size();
    }


    /**
     * Adds a node to the NodeGraph.
     * @param id The id of the node.
     */
    public void addNode(int id) {
        if (id >= nodes.length) {
            int[] newNodes = new int[id + 1];
            for (int i = 0; i < nodes.length; i++) {
                newNodes[i] = nodes[i];
            }
            nodes = newNodes;
        }
    }

    /**
     * Adds a node to the NodeGraph.
     * @param id The id of the node.
     * @param length The length of the node.
     */
    public void addNode(int id, int length) {
        addNode(id);
        setNodeLength(id, length);
    }

    /**
     * Retrieve the set of all edges in this NodeGraph.
     * @return The set of all edges in this NodeGraph.
     */
    public HashSet<Edge> getEdges() {
        return edges;
    }

    /**
     * Add an edge to the NodeGraph.
     * @param e The edge that gets added.
     */
    public void addEdge(Edge e) {
        edges.add(e);
    }

    /**
     * Add an edge to the NodeGraph.
     * @param parent The id of the parent node.
     * @param child The id of the child node.
     */
    public void addEdge(int parent, int child) {
        edges.add(new Edge(parent, child));
    }

    /**
     * Retrieves the incoming edges of a node.
     * @param id The id of the node.
     * @return A set of the incoming edges of the node.
     */
    public HashSet<Edge> getIncomingEdges(int id) {
        HashSet<Edge> res = new HashSet<>();
        for (Edge e : edges) {
            if (e.getChild() == id) {
                res.add(e);
            }
        }
        return res;
    }

    /**
     * Retrieves the outgoing edges of a node.
     * @param id The id of the node.
     * @return A set of the outgoing edges of the node.
     */
    public HashSet<Edge> getOutgoingEdges(int id) {
        HashSet<Edge> res = new HashSet<>();
        for (Edge e : edges) {
            if (e.getParent() == id) {
                res.add(e);
            }
        }
        return res;
    }
}

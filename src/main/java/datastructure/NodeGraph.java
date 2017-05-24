package datastructure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;

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
     * LinkedList of the nodes that need to be drawn.
     */
    private LinkedList<DrawNode> drawNodes;

    /**
     * Instance of the current graph.
     */
    private static NodeGraph currentNodeGraph;

    /**
     * Empty constructor for NodeGraph.
     */
    public NodeGraph() {
        this.nodes = new ArrayList<>(0);
        segments = new SegmentDB();
        drawNodes = new LinkedList<DrawNode>();
    }

    /**
     * Constructor for NodeGraph.
     * @param nodes The list of nodes for the graph.
     * @param segments The database containing the segments of the nodes.
     * @param drawNodes The LinkedList of nodes to be drawn.
     */
    public NodeGraph(final ArrayList<Node> nodes, final SegmentDB segments, final LinkedList<DrawNode> drawNodes) {
        this.nodes = nodes;
        this.segments = segments;
        this.drawNodes = drawNodes;
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

        int[] temp = nodes.get(id).getIncomingEdges();
        nodes.set(id, node);
        nodes.get(id).setIncomingEdges(temp);
    }

    /**
     * Adds a node to the graph from the cached file.
     * @param id The id of the node.
     * @param node The node that gets added.
     */
    public void addNodeCache(int id, Node node) {
        while (nodes.size() <= id) {
            nodes.add(new Node());
        }
        nodes.set(id, node);
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
     * Getter for the instance of this graph.
     * @return The graph we are workig with at this moment.
     */
    public static NodeGraph getCurrentInstance() {
        return currentNodeGraph;
    }

    /**
     * Setter for the current instance of graph.
     * @param g the graph we want to be the current graph.
     */
    public static void setCurrentInstance(NodeGraph g) {
        currentNodeGraph = g;
    }

    /**
     * Returns the index of a node.
     * @param n the node we want the index of.
     * @return the index of this node.
     */
    public int indexOf(Node n) {
        for (int i = 0; i < this.nodes.size(); i++) {
            if (this.nodes.get(i) == n) {
                return i;
            }
        }
        return -1;
    }

     /**
     * Returns the amount of nodes.
     * @return the size of the graph.
     */
    public int getSize() {
        return nodes.size();
    }

    /**
     * Sets the segmentdb to a new segmentDB.
     * @param db The new segmentDB.
     */
    public void setSegmentDB(SegmentDB db) {
        segments = db;
    }

    /**
     * Getter for the list of nodes.
     * @return the list of all nodes in the graph.
     */
    public ArrayList<Node> getNodes() { return this.nodes; }

    /**
     * Generates the list of DrawNodes based on center node id and radius.
     * @param center Id of the center nodes.
     * @param radius Radius (amount of nodes required).
     */
    public void generateDrawNodes(int center, int radius) {
        TreeSet<Integer> visited = new TreeSet<>();
        Queue<Integer> q = new LinkedList<Integer>();
        int r = Math.min(radius, nodes.size());
        visited.add(center);
        q.offer(center);
        int current;

        while (drawNodes.size() < r) {
            current = q.poll();
            addEdges(current, q, visited);
            drawNodes.addLast(new DrawNode(current));
        }
    }

    /**
     * Adds nodes based on edges of the id node to the queue and the set.
     * @param id The origin node.
     * @param q The queue the edges are put into.
     * @param visited The set of nodes already visited.
     */
    private void addEdges(int id, Queue<Integer> q, TreeSet<Integer> visited) {
        int[] tempEdges = nodes.get(id).getIncomingEdges();

        for (int i = 0; i < tempEdges.length; i++) {
            if (!visited.contains(tempEdges[i])) {
                visited.add(tempEdges[i]);
                q.add(tempEdges[i]);
            }
        }

        tempEdges = nodes.get(id).getOutgoingEdges();

        for (int i = 0; i < tempEdges.length; i++) {
            if (!visited.contains(tempEdges[i])) {
                visited.add(tempEdges[i]);
                q.add(tempEdges[i]);
            }
        }
    }
}

package datastructure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.Queue;
import java.util.Iterator;

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
        drawNodes = new LinkedList<>();
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

        node.computeLength();
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
    public ArrayList<Node> getNodes() {
        return this.nodes;
    }

    /**
     * Generates the list of DrawNodes based on center node id and radius.
     * @param center Id of the center nodes.
     * @param radius Radius (amount of nodes required).
     */
    public void generateDrawNodes(int center, int radius) {
        TreeSet<Integer> visited = new TreeSet<>();
        Queue<Integer> q = new LinkedList<>();
        int r = Math.min(radius, nodes.size());
        visited.add(center);
        q.offer(center);
        int current;

        while (drawNodes.size() < r) {
            current = q.poll();
            addEdges(current, q, visited);
            drawNodes.addLast(new DrawNode(current));
            drawNodes.peekLast().setWidth(nodes.get(current).getLength());
        }
        topoSort();
        assignLayers();
        verticalSpacing();
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

    /**
     * Sort the drawNodes.
     */
    private void topoSort() {
        LinkedList<DrawNode> sorted = new LinkedList<>();
        while (!drawNodes.isEmpty()) {
            topoSortUtil(drawNodes.getFirst(), sorted);
        }
        drawNodes = sorted;
    }

    /**
     * Recursive part of topoSort.
     * @param current the current node.
     * @param sorted the list which holds all sorted nodes.
     */
    private void topoSortUtil(DrawNode current, LinkedList<DrawNode> sorted) {
        if (!sorted.contains(current)) {
            for (int i : nodes.get(current.getIndex()).getOutgoingEdges()) {
                for (DrawNode temp : drawNodes) {
                    if (temp.getIndex() == i) {
                        topoSortUtil(temp, sorted);
                        break;
                    }
                }
            }
            sorted.addLast(current);
            drawNodes.remove(current);
        }
    }

    /**
     * Assigns a layer to all drawNodes in the subgraph.
     */
    private void assignLayers() {
        Iterator<DrawNode> it = drawNodes.iterator();
        double layer = 1600;
        DrawNode current;

        while (it.hasNext()) {
             current = it.next();

             for (int i : nodes.get(current.getIndex()).getOutgoingEdges()) {
                 for (DrawNode temp : drawNodes) {
                     if (temp.getIndex() == i && temp.getX() < layer) {
                         layer = temp.getX();
                     }
                 }
             }

             current.setX(layer - 100);
        }
    }

    /**
     * Computes the Y coordinate of the drawNodes
     * by looping over all nodes and adding to their Y coordinate
     * when the X coordinate is the same.
     */
    private void verticalSpacing() {
        for (int i = 1; i < drawNodes.size(); i++) {
            if (drawNodes.get(i - 1).getX() == drawNodes.get(i).getX()) {
                drawNodes.get(i).setY(drawNodes.get(i - 1).getY() + 50);
            }
        }
    }

    /**
     * Get the LinkedList of DrawNodes that need to be drawn.
     * @return LinkedList of DrawNodes.
     */
    public LinkedList<DrawNode> getDrawNodes() {
        return drawNodes;
    }
}

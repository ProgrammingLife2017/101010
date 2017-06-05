package datastructure;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
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
     * LinkedList of the dummynodes.
     */
    private LinkedList<DummyEdge> dummyEdges;

    /**
     * LinkedList of the nodes that are in the first layer.
     */
    private LinkedList<Integer> rootNodes;

    /**
     * LinkedList of the nodes that are in the last layer.
     */
    private LinkedList<Integer> leafNodes;

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
        dummyEdges = new LinkedList<>();
    }

    /**
     * Constructor for NodeGraph.
     * @param nodes The list of nodes for the graph.
     * @param segments The database containing the segments of the nodes.
     * @param drawNodes The LinkedList of nodes to be drawn.
     * @param dummyEdges The LinkedList of DummyEdges.
     */
    public NodeGraph(final ArrayList<Node> nodes, final SegmentDB segments, final LinkedList<DrawNode> drawNodes, final LinkedList<DummyEdge> dummyEdges) {
        this.nodes = nodes;
        this.segments = segments;
        this.drawNodes = drawNodes;
        this.dummyEdges = dummyEdges;
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
        drawNodes = new LinkedList<>();
        dummyEdges = new LinkedList<>();
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
        }
        topologicalSort();
        assignLayers();
        computeDummyEdges();
        updateDummyEdges();
        dummyEdgeSpacing();
        verticalSpacing();
        retrieveEdgeNodes();
    }

    /**
     * Adds nodes based on edges of the id node to the queue and the set.
     * @param id The origin node.
     * @param q The queue the edges are put into.
     * @param visited The set of nodes already visited.
     */
    private void addEdges(int id, Queue<Integer> q, TreeSet<Integer> visited) {
        int[] tempEdges = nodes.get(id).getIncomingEdges();

        for (int i : tempEdges) {
            if (!visited.contains(i)) {
                visited.add(i);
                q.add(i);
            }
        }

        tempEdges = nodes.get(id).getOutgoingEdges();

        for (int i : tempEdges) {
            if (!visited.contains(i)) {
                visited.add(i);
                q.add(i);
            }
        }
    }

    /**
     * Sort the drawNodes.
     */
    private void topologicalSort() {
        LinkedList<DrawNode> sorted = new LinkedList<>();
        while (!drawNodes.isEmpty()) {
            topologicalSortUtil(drawNodes.getFirst(), sorted);
        }
        drawNodes = sorted;
    }

    /**
     * Recursive part of topoSort.
     * @param current the current node.
     * @param sorted the list which holds all sorted nodes.
     */
    private void topologicalSortUtil(DrawNode current, LinkedList<DrawNode> sorted) {
        if (!sorted.contains(current)) {
            for (int i : getNode(current.getIndex()).getOutgoingEdges()) {
                for (DrawNode temp : drawNodes) {
                    if (temp.getIndex() == i) {
                        topologicalSortUtil(temp, sorted);
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
        int layer = 1200;
        DrawNode current;

        while (it.hasNext()) {
            current = it.next();

             int size = drawNodes.size();
             for (int i : getNode(current.getIndex()).getOutgoingEdges()) {
                 for (int j = 0; j < size; j++) {
                     DrawNode temp = drawNodes.get(j);
                     if (temp.getIndex() == i && temp.getLayer() < layer) {
                         layer = temp.getLayer();
                     }
                 }
             }

            current.setLayer(layer - 100);
        }
    }

    /**
     * Adds dummy edges to make the graph more readable.
     */
    private void computeDummyEdges() {
        Node currentNode;
        DrawNode otherDrawNode;
        DummyEdge edge;
        for (DrawNode currentDrawNode : drawNodes) {
            currentNode = getNode(currentDrawNode.getIndex());
            for (int i : currentNode.getIncomingEdges()) {
                otherDrawNode = getDrawNode(i);
                if (otherDrawNode == null || (otherDrawNode != null && Math.abs(currentDrawNode.getLayer() - otherDrawNode.getLayer()) > 100)) {
                    edge = new DummyEdge(i, currentDrawNode.getIndex());
                    if (!dummyEdges.contains(edge)) {
                        edge.addLast(currentDrawNode.getLayer() - 100, (int) currentDrawNode.getY());
                        dummyEdges.add(edge);
                    }
                }
            }
            for (int i : currentNode.getOutgoingEdges()) {
                otherDrawNode = getDrawNode(i);
                if (otherDrawNode == null || (otherDrawNode != null && Math.abs(currentDrawNode.getLayer() - otherDrawNode.getLayer()) > 100)) {
                    edge = new DummyEdge(currentDrawNode.getIndex(), i);
                    if (!dummyEdges.contains(edge)) {
                        edge.addLast(currentDrawNode.getLayer() + 100, (int) currentDrawNode.getY());
                        dummyEdges.add(edge);
                    }
                }
            }
        }
    }

    /**
     * Fills Dummy Edges with Dummy Nodes to fit the subgraph's size.
     */
    private void updateDummyEdges() {
        Iterator<DummyEdge> it = dummyEdges.iterator();
        DummyEdge currentDummyEdge;
        DrawNode currentParent;
        DrawNode currentChild;

        while (it.hasNext()) {
            currentDummyEdge = it.next();
            currentParent = getDrawNode(currentDummyEdge.getParent());
            currentChild = getDrawNode(currentDummyEdge.getChild());
            if (currentParent == null && currentChild == null) {
               dummyEdges.remove(currentDummyEdge);
            } else {
                if (currentParent == null) {
                    while (currentDummyEdge.getFirstX() >= drawNodes.getFirst().getLayer()) {
                        currentDummyEdge.addFirst();
                    }
                } else {
                    while (currentDummyEdge.getFirstX() - 100 > currentParent.getLayer()) {
                        currentDummyEdge.addFirst();
                    }
                }
                if (currentChild == null) {
                    while (currentDummyEdge.getLastX() <= drawNodes.getLast().getLayer()) {
                        currentDummyEdge.addLast();
                    }
                } else {
                    while (currentDummyEdge.getLastX() + 100 < currentChild.getLayer()) {
                        currentDummyEdge.addLast();
                    }
                }
            }
        }
    }

    /**
     * Computes the y coordinates for dummy nodes in dummyedges.
     */
    private void dummyEdgeSpacing() {
        DummyEdge otherEdge;
        int currentLayer;
        for (int i = 0; i < dummyEdges.size(); i++) {
            for (int j = 0; j < dummyEdges.get(i).getLength(); j++) {
                currentLayer = dummyEdges.get(i).getX(j);
                for (int k = i - 1; k >= 0; k--) {
                    otherEdge = dummyEdges.get(k);
                    if (otherEdge.traversesLayer(currentLayer)) {
                        dummyEdges.get(i).setYOfLayer(currentLayer, otherEdge.getYOfLayer(currentLayer) + 50);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Computes the Y coordinate of the drawNodes
     * by looping over all nodes and adding to their Y coordinate
     * when the X coordinate is the same.
     */
    private void verticalSpacing() {
        for (int i = 0; i < drawNodes.size(); i++) {
            if (i > 0 && drawNodes.get(i - 1).getLayer() == drawNodes.get(i).getLayer()) {
                drawNodes.get(i).setY(drawNodes.get(i - 1).getY() + 50);
            } else {
                DummyEdge current;
                for (int j = dummyEdges.size() - 1; j >= 0; j--) {
                    current = dummyEdges.get(j);
                    if (current.traversesLayer(drawNodes.get(i).getLayer())) {
                        drawNodes.get(i).setY(current.getYOfLayer(drawNodes.get(i).getLayer()) + 50);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Determine y-coordinates of new leaf and root nodes.
     * @param newNodes new leaf and root draw nodes.
     * @param newDummyEdges new leaf and root dummy edges.
     */
    private void verticalSpacingNew(LinkedList<DrawNode> newNodes, LinkedList<DummyEdge> newDummyEdges) {

    }

    /**
     * Saves the root and leave nodes to specific lists.
     */
    private void retrieveEdgeNodes() {
        rootNodes = new LinkedList<>();
        leafNodes = new LinkedList<>();
        double startX = drawNodes.getLast().getX();
        double endX = drawNodes.getFirst().getX();

        retrieveDrawNodes(startX, endX);
        retrieveDummies(startX, endX);
    }

    /**
     * Adds the DrawNodes that are roots or leaves.
     * @param startX the leftmost layer.
     * @param endX the rightmost layer.
     */
    private void retrieveDrawNodes(double startX, double endX) {
        Iterator<DrawNode> it = drawNodes.iterator();

        DrawNode temp;
        while (it.hasNext()) {
            temp = it.next();
            if (temp.getLayer() <= endX && temp.getLayer() >= endX) {
                leafNodes.add(temp.getIndex());
            } else {
                break;
            }
        }

        Iterator<DrawNode> rit = drawNodes.descendingIterator();

        while (rit.hasNext()) {
            temp = rit.next();
            if (temp.getLayer() <= startX && temp.getLayer() >= startX) {
                rootNodes.add(temp.getIndex());
            } else {
                break;
            }
        }
    }

    /**
     * Adds the DummyNodes that are roots or leaves.
     * @param startX the leftmost layer.
     * @param endX the rightmost layer.
     */
    private void retrieveDummies(double startX, double endX) {

    }

    /**
     * Get the LinkedList of DrawNodes that need to be drawn.
     * @return LinkedList of DrawNodes.
     */
    public LinkedList<DrawNode> getDrawNodes() {
        return drawNodes;
    }

    /**
     * Returns the DrawNode that represents the given node.
     * @param index the index of the node we want the representing DrawNode for.
     * @return a DrawNode.
     */
    public DrawNode getDrawNode(int index) {
        for (DrawNode dNode : drawNodes) {
            if (dNode.getIndex() == index) {
                return dNode;
            }
        }
        return null;
    }

    /**
     * Gets the LinkedList of DummyEdges.
     * @return The LinkedList of DummyEdges.
     */
    public LinkedList<DummyEdge> getDummyEdges() {
        return dummyEdges;
    }

    /**
     * Getter for a Dummy Edge.
     * @param parent id of the parent Node.
     * @param child id of the child Node
     * @return the Dummy Edge, null if it isn't found.
     */
    @Nullable
    private DummyEdge getDummyEdge(int parent, int child) {
        for (DummyEdge e : dummyEdges) {
            if (e.getParent() == parent && e.getChild() == child) {
                return e;
            }
        }
        return null;
    }

     /** Get the root nodes of the current SubGraph.
     * @return LinkedList containing the AbsIds of the Root Nodes of the current SubGraph.
     */
    protected LinkedList<Integer> getRootNodes() {
        return rootNodes;
    }

    /**
     * Get the leaf nodes of the current SubGraph.
     * @return LinkedList containing the AbsIds of the Lead Nodes of the current SubGraph.
     */
    protected LinkedList<Integer> getLeafNodes() {
        return leafNodes;
    }
}

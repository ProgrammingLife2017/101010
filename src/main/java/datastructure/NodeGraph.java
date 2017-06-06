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
     * Reverse topologically sort the list of DrawNodes in this NodeGraph.
     */
    private void topologicalSort() {
        drawNodes = topologicalSort(drawNodes);
    }

    /**
     * Reverse topologically sort a list of DrawNodes.
     * @param dNodes the unsorted LinkedList of DrawNodes.
     * @return The sorted list of DrawNodes.
     */
    private LinkedList<DrawNode> topologicalSort(LinkedList<DrawNode> dNodes) {
        LinkedList<DrawNode> sorted = new LinkedList<>();
        while (!dNodes.isEmpty()) {
            topologicalSortUtil(dNodes.getFirst(), sorted, dNodes);
        }
        return sorted;
    }

    /**
     * Recursive part of topological sort.
     * @param current the current node.
     * @param sorted the list which holds all sorted nodes.
     * @param dNodes the list which holds the remaining unsorted nodes.
     */
    private void topologicalSortUtil(DrawNode current, LinkedList<DrawNode> sorted, LinkedList<DrawNode> dNodes) {
        if (!sorted.contains(current)) {
            for (int i : getNode(current.getIndex()).getOutgoingEdges()) {
                for (DrawNode temp : dNodes) {
                    if (temp.getIndex() == i) {
                        topologicalSortUtil(temp, sorted, dNodes);
                        break;
                    }
                }
            }
            sorted.addLast(current);
            drawNodes.remove(current);
        }
    }

    /**
     * Assigns a layer to all DrawNodes in this NodeGraph.
     */
    private void assignLayers() {
        assignLayers(drawNodes, 1200);
    }

    /**
     * Assigns a layer to all drawNodes in the sub graph.
     * @param dNodes The list of DrawNodes in the sub graph.
     * @param maxLayer The X coordinate of the rightmost layer.
     */
    private void assignLayers(LinkedList<DrawNode> dNodes, int maxLayer) {
        Iterator<DrawNode> it = dNodes.iterator();
        DrawNode current;

        while (it.hasNext()) {
            current = it.next();

             int size = dNodes.size();
             for (int i : getNode(current.getIndex()).getOutgoingEdges()) {
                 for (int j = 0; j < size; j++) {
                     DrawNode temp = dNodes.get(j);
                     if (temp.getIndex() == i && temp.getLayer() < maxLayer) {
                         maxLayer = temp.getLayer();
                     }
                 }
             }

            current.setLayer(maxLayer - 100);
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
            for (int i : currentNode.getOutgoingEdges()) {
                otherDrawNode = getDrawNode(i);
                if (otherDrawNode == null || (otherDrawNode != null && Math.abs(currentDrawNode.getLayer() - otherDrawNode.getLayer()) > 100)) {
                    edge = new DummyEdge(currentDrawNode.getIndex(), i);
                    if (!dummyEdges.contains(edge)) {
                        edge.addLast(currentDrawNode.getLayer() + 100, 0);
                        dummyEdges.add(edge);
                    }
                }
            }
            for (int i : currentNode.getIncomingEdges()) {
                otherDrawNode = getDrawNode(i);
                if (otherDrawNode == null || (otherDrawNode != null && Math.abs(currentDrawNode.getLayer() - otherDrawNode.getLayer()) > 100)) {
                    edge = new DummyEdge(i, currentDrawNode.getIndex());
                    if (!dummyEdges.contains(edge)) {
                        edge.addLast(currentDrawNode.getLayer() - 100, 0);
                        dummyEdges.add(edge);
                    }
                }
            }
        }
    }

    /**
     * Fills Dummy Edges with Dummy Nodes to fit the size of the sub graph.
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
     * Computes the y coordinates for the dummy nodes
     * in the list of DummyEdges in this NodeGraph.
     */
    private void dummyEdgeSpacing() {
        dummyEdgeSpacing(dummyEdges);
    }

    /**
     * Computes the y coordinates for dummy nodes in a list of DummyEdges.
     * @param dEdges The LinkedList of DummyEdges.
     */
    private void dummyEdgeSpacing(LinkedList<DummyEdge> dEdges) {
        DummyEdge otherEdge;
        int currentLayer;
        for (int i = 0; i < dEdges.size(); i++) {
            for (int j = 0; j < dEdges.get(i).getLength(); j++) {
                currentLayer = dEdges.get(i).getX(j);
                for (int k = i - 1; k >= 0; k--) {
                    otherEdge = dEdges.get(k);
                    if (otherEdge.traversesLayer(currentLayer)) {
                        dEdges.get(i).setYOfLayer(currentLayer, otherEdge.getYOfLayer(currentLayer) + 50);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Computes the Y coordinate of the drawNodes in this NodeGraph
     * by looping over all nodes and adding to their Y coordinate
     * when the X coordinate is the same.
     */
    private void verticalSpacing() {
        verticalSpacing(drawNodes, dummyEdges);
    }

    /**
     * Computes the Y coordinate of the drawNodes
     * by looping over all nodes and adding to their Y coordinate
     * when the X coordinate is the same.
     * @param dNodes The LinkedList of DrawNodes.
     * @param dEdges The LinkedList of DummyEdges.
     */
    private void verticalSpacing(LinkedList<DrawNode> dNodes, LinkedList<DummyEdge> dEdges) {
        for (int i = 0; i < dNodes.size(); i++) {
            if (i > 0 && dNodes.get(i - 1).getLayer() == dNodes.get(i).getLayer()) {
                dNodes.get(i).setY(dNodes.get(i - 1).getY() + 50);
            } else {
                DummyEdge current;
                for (int j = dEdges.size() - 1; j >= 0; j--) {
                    current = dEdges.get(j);
                    if (current.traversesLayer(dNodes.get(i).getLayer())) {
                        dNodes.get(i).setY(current.getYOfLayer(dNodes.get(i).getLayer()) + 50);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Saves the root and leave nodes to specific lists.
     */
    private void retrieveEdgeNodes() {
        rootNodes = new LinkedList<>();
        leafNodes = new LinkedList<>();
        int startX = drawNodes.getLast().getLayer();
        int endX = drawNodes.getFirst().getLayer();

        retrieveDrawNodes(startX, endX);
        retrieveDummies(startX, endX);
    }

    /**
     * Adds the DrawNodes that are roots or leaves.
     * @param startX the leftmost layer.
     * @param endX the rightmost layer.
     */
    private void retrieveDrawNodes(int startX, int endX) {
        Iterator<DrawNode> it = drawNodes.iterator();

        DrawNode temp;
        while (it.hasNext()) {
            temp = it.next();
            if (temp.getLayer() == endX) {
                leafNodes.add(temp.getIndex());
            } else {
                break;
            }
        }

        Iterator<DrawNode> rit = drawNodes.descendingIterator();

        while (rit.hasNext()) {
            temp = rit.next();
            if (temp.getLayer() == startX) {
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
    private void retrieveDummies(int startX, int endX) {
        for (DummyEdge edge : dummyEdges) {
            if (edge.getFirstX() == startX) {
                rootNodes.addLast(-edge.getParent());
            }
            if (edge.getLastX() == endX) {
                leafNodes.add(-edge.getChild());
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

    /**
     * Returns the DrawNode that represents the given node,
     * from the list of DrawNodes in this NodeGraph.
     * @param index The index of the node we want the representing DrawNode for.
     * @return a DrawNode.
     */
    @Nullable
    public DrawNode getDrawNode(int index) {
        return getDrawNode(drawNodes, index);
    }

    /**
     * Returns the DrawNode that represents the given node.
     * @param dNodes The list of DrawNodes to search in.
     * @param index The index of the node we want the representing DrawNode for.
     * @return a DrawNode.
     */
    @Nullable
    public DrawNode getDrawNode(LinkedList<DrawNode> dNodes, int index) {
        for (DrawNode dNode : dNodes) {
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

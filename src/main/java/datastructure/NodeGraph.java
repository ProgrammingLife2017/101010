package datastructure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.Queue;
import java.util.Iterator;
import java.util.ListIterator;
import javafx.scene.paint.Color;
import javafx.util.Pair;

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
    private LinkedList<DummyNode> dummyNodes;

    /**
     * LinkedList of the nodes that are in the first layer.
     */
    private LinkedList<Pair<Integer, Boolean>> rootNodes;

    /**
     * LinkedList of the nodes that are in the last layer.
     */
    private LinkedList<Pair<Integer, Boolean>> leafNodes;

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
        dummyNodes = new LinkedList<>();
    }

    /**
     * Constructor for NodeGraph.
     * @param nodes The list of nodes for the graph.
     * @param segments The database containing the segments of the nodes.
     * @param drawNodes The LinkedList of nodes to be drawn.
     * @param dummyNodes The LinkedList of DummyNodes.
     */
    public NodeGraph(final ArrayList<Node> nodes, final SegmentDB segments, final LinkedList<DrawNode> drawNodes, final LinkedList<DummyNode> dummyNodes) {
        this.nodes = nodes;
        this.segments = segments;
        this.drawNodes = drawNodes;
        this.dummyNodes = dummyNodes;
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
        dummyNodes = new LinkedList<>();
        TreeSet<Integer> visited = new TreeSet<>();
        Queue<Integer> q = new LinkedList<>();
        int r = Math.min(radius, nodes.size());
        visited.add(center);
        q.offer(center);
        int current;
        DrawNode drawNode;

        while (drawNodes.size() < r) {
            current = q.poll();
            addEdges(current, q, visited);
            drawNode = new DrawNode(current);
            drawNode.setWidth(nodes.get(current).getLength());
            drawNode.setFill(Color.CRIMSON);
            drawNode.setHeight(10);
            drawNodes.addLast(drawNode);
        }
        topoSort();
        assignLayers();
        computeDummyNodes();
        verticalSpacing();
        retrieveEdgeNodes();
        for (DummyNode d : dummyNodes) {
            System.out.println(d.getId() + ", " + d.getFrom() + ", " + d.getTo() + ", " + d.getX() + ", " + d.getY());
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
        double layer = 1200;
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
     * Adds dummy nodes to make the graph more readable.
     */
    private void computeDummyNodes() {
        ListIterator<DrawNode> it = drawNodes.listIterator();
        Queue<DummyNode> dummyNodeQueue = new LinkedList<>();
        DrawNode current;
        DrawNode cDrawNode;
        DummyNode cDummyNode;
        double currentLayer = 1200;
        int[] edges;

        while (it.hasNext()) {
            current = it.next();

            if (current.getX() != currentLayer) {
                currentLayer -= 100;
                while (!dummyNodeQueue.isEmpty()) {
                    cDummyNode = dummyNodeQueue.poll();
                    cDrawNode = getDrawNode(cDummyNode.getFrom());
                    Queue<DummyNode> temp = new LinkedList<>();
                    if (cDrawNode != null && Math.abs(cDummyNode.getX() - cDrawNode.getX()) > 100) {
                        temp.offer(new DummyNode(cDummyNode.getId() - 1, cDummyNode.getFrom(), cDummyNode.getTo(), cDummyNode.getX() - 100, 50));
                    } else if (cDrawNode == null && drawNodes.peekLast().getX() < cDummyNode.getX()) {
                        temp.offer(new DummyNode(cDummyNode.getId() - 1, cDummyNode.getFrom(), cDummyNode.getTo(), cDummyNode.getX() - 100, 50));
                    }
                    dummyNodes.addLast(cDummyNode);
                    dummyNodeQueue = temp;
                }
            }

            edges = nodes.get(current.getIndex()).getIncomingEdges();

            for (int i = 0; i < edges.length; i++) {
                cDrawNode = getDrawNode(edges[i]);
                if (cDrawNode != null && Math.abs(current.getX() - cDrawNode.getX()) > 100) {
                    dummyNodeQueue.add(new DummyNode(-1, cDrawNode.getIndex(), current.getIndex(), (int) currentLayer - 100, 50));
                } else if (cDrawNode == null && drawNodes.peekLast().getX() < currentLayer) {
                    dummyNodeQueue.add(new DummyNode(-1, edges[i], current.getIndex(), (int) currentLayer - 100, 50));
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
        int maxY;
        for (int i = 0; i < dummyNodes.size(); i++) {
            maxY = 0;
            for (int j = i - 1; j >= 0; j--) {
                if (dummyNodes.get(i).getX() == dummyNodes.get(j).getX() && maxY < dummyNodes.get(j).getY()) {
                   maxY = dummyNodes.get(j).getY();
                }
            }
            dummyNodes.get(i).setY(maxY + 50);
        }

        for (int i = 0; i < drawNodes.size(); i++) {
            if (i > 0 && drawNodes.get(i - 1).getX() == drawNodes.get(i).getX()) {
                drawNodes.get(i).setY(drawNodes.get(i - 1).getY() + 50);
            } else {
                maxY = 0;
                for (DummyNode dN : dummyNodes) {
                    if (dN.getX() == (int) drawNodes.get(i).getX() && dN.getY() > maxY) {
                        maxY = dN.getY();
                    }
                }
                drawNodes.get(i).setY(maxY + 50);
            }
        }
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
            if (temp.getX() == endX) {
                leafNodes.add(new Pair<>(temp.getIndex(), false));
            } else {
                break;
            }
        }

        Iterator<DrawNode> rit = drawNodes.descendingIterator();

        while (rit.hasNext()) {
            temp = rit.next();
            if (temp.getX() == startX) {
                rootNodes.add(new Pair<>(temp.getIndex(), false));
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
        Iterator<DummyNode> it = dummyNodes.iterator();

        DummyNode temp;
        while (it.hasNext()) {
            temp = it.next();
            if (temp.getX() == endX) {
                leafNodes.add(new Pair<>(temp.getId(), true));
            } else {
                break;
            }
        }

        Iterator<DummyNode> rit = dummyNodes.descendingIterator();

        while (rit.hasNext()) {
            temp = rit.next();
            if (temp.getX() == startX) {
                rootNodes.add(new Pair<>(temp.getId(), true));
            } else {
                break;
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
     * Gets the LinkedList of DummyNodes.
     * @return The LinkedList of DummyNodes.
     */
    public LinkedList<DummyNode> getDummyNodes() {
        return dummyNodes;
    }
}

package datastructure;

import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
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
    private LinkedList<DummyNode> dummyNodes;

    /**
     * LinkedList of the nodes that are in the first layer.
     */
    private LinkedList<Double> rootNodes;

    /**
     * LinkedList of the nodes that are in the last layer.
     */
    private LinkedList<Double> leafNodes;

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
    private void topoSort() {
        LinkedList<DrawNode> sorted = new LinkedList<>();
        while (!drawNodes.isEmpty()) {
            topoSortUtil(drawNodes.getFirst(), sorted);
        }
        drawNodes = sorted;
    }

    /**
     * Sort the nodes to be drawn.
     * @param dNodes the nodes to be drawn.
     * @return the updated list of nodes.
     */
    private LinkedList<DrawNode> newTopoSort(LinkedList<DrawNode> dNodes) {
        LinkedList<DrawNode> sorted = new LinkedList<>();
        while (!dNodes.isEmpty()) {
            newTopoSortUtil(dNodes.getFirst(), sorted, dNodes);
        }
        dNodes = sorted;
        return dNodes;
    }

    /**
     * Recursive method for topological sorting
     * @param current current node.
     * @param sorted current list of sorted nodes.
     * @param dNodes current list of nodes to be sorted.
     */
    private void newTopoSortUtil(DrawNode current, LinkedList<DrawNode> sorted, LinkedList<DrawNode> dNodes) {
        if (!sorted.contains(current)) {
            for (int i : nodes.get(current.getIndex()).getOutgoingEdges()) {
                for (DrawNode temp : dNodes) {
                    if (temp.getIndex() == i) {
                        newTopoSortUtil(temp, sorted, dNodes);
                        break;
                    }
                }
            }
            sorted.addLast(current);
            dNodes.remove(current);
        }
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

             int size = drawNodes.size();
             for (int i : nodes.get(current.getIndex()).getOutgoingEdges()) {
                 for (int j = 0; j < size; j++) {
                     DrawNode temp = drawNodes.get(j);
                     int ind2 = temp.getIndex();
                     if (temp.getIndex() == i && temp.getX() < layer) {
                         layer = temp.getX();
                     }
                 }
             }

            current.setX(layer - 100);
        }
    }

    /**
     * Assign layers to the new leaf nodes.
     * @param newNodes the new leaf nodes.
     */
    private void assignLayersLeaf(LinkedList<DrawNode> newNodes) {
        ListIterator<DrawNode> it = newNodes.listIterator();
        double layer = drawNodes.getFirst().getX();
        DrawNode current;

        while (it.hasNext()) {
            current = it.next();

            for (int i : nodes.get(current.getIndex()).getOutgoingEdges()) {
                for (DrawNode temp : newNodes) {
                    if (temp.getIndex() == i && temp.getX() > layer) {
                        layer = temp.getX();
                    }
                }
            }
            current.setX(layer + 100);
        }
    }

    /**
     * Assigns layers to the new root nodes.
     * @param newNodes the new root nodes.
     */
    private void assignLayersRoot(LinkedList<DrawNode> newNodes) {
        ListIterator<DrawNode> it = newNodes.listIterator(newNodes.size());
        double layer = drawNodes.getLast().getX();
        DrawNode current;

        while (it.hasPrevious()) {
            current = it.previous();

            for (int i : nodes.get(current.getIndex()).getOutgoingEdges()) {
                for (DrawNode temp : newNodes) {
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

            for (int i : edges) {
                cDrawNode = getDrawNode(i);
                if (cDrawNode != null && Math.abs(current.getX() - cDrawNode.getX()) > 100) {
                    dummyNodeQueue.add(new DummyNode(-1, cDrawNode.getIndex(), current.getIndex(), (int) currentLayer - 100, 50));
                } else if (cDrawNode == null && drawNodes.peekLast().getX() < currentLayer) {
                    dummyNodeQueue.add(new DummyNode(-1, i, current.getIndex(), (int) currentLayer - 100, 50));
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
                    if (dN.getX() == (int) drawNodes.get(i).getX()) {
                        maxY = dN.getY();
                    }
                }
                drawNodes.get(i).setY(maxY + 50);
            }
        }
    }

    /**
     * Determine y-coordinates of new leaf and root nodes.
     * @param newNodes new leaf and root draw nodes.
     * @param newDummyNodes new leaf and root dummy nodes.
     */
    private void verticalSpacingNew(LinkedList<DrawNode> newNodes, LinkedList<DummyNode> newDummyNodes) {
        int maxY;
        for (int i = 0; i < newDummyNodes.size(); i++) {
            maxY = 0;
            for (int j = i - 1; j >= 0; j--) {
                if (newDummyNodes.get(i).getX() == newDummyNodes.get(j).getX() && maxY < newDummyNodes.get(j).getY()) {
                    maxY = newDummyNodes.get(j).getY();
                }
            }
            newDummyNodes.get(i).setY(maxY + 50);
        }

        for (int i = 0; i < newNodes.size(); i++) {
            if (i > 0 && newNodes.get(i - 1).getX() == newNodes.get(i).getX()) {
                newNodes.get(i).setY(newNodes.get(i - 1).getY() + 50);
            } else {
                maxY = 0;
                for (DummyNode dN : newDummyNodes) {
                    if (dN.getX() == (int) newNodes.get(i).getX() && dN.getY() > maxY) {
                        maxY = dN.getY();
                    }
                }
                newNodes.get(i).setY(maxY + 50);
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
                leafNodes.add((double) temp.getIndex());
            } else {
                break;
            }
        }

        Iterator<DrawNode> rit = drawNodes.descendingIterator();

        while (rit.hasNext()) {
            temp = rit.next();
            if (temp.getX() == startX) {
                rootNodes.add((double) temp.getIndex());
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
                leafNodes.add(temp.getAbsId());
            } else {
                break;
            }
        }

        Iterator<DummyNode> rit = dummyNodes.descendingIterator();

        while (rit.hasNext()) {
            temp = rit.next();
            if (temp.getX() == startX) {
                rootNodes.add(temp.getAbsId());
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

    /**
     * Determines nodes to add before the root.
     * @return the new root nodes.
     */
    public Pair<LinkedList<DrawNode>, LinkedList<DummyNode>> addAtRoot() {
        ArrayList<Integer> visited = new ArrayList<>();
        LinkedList<DrawNode> newNodes = new LinkedList<DrawNode>();
        for (Double id : rootNodes) {
            if (id >= 0) {
                for (int m : NodeGraph.getCurrentInstance().getNodes().get(id.intValue()).getIncomingEdges()) {
                    if (!visited.contains(m)) {
                        visited.add(m);
                    }
                }
            } else {
                DummyNode dummy = NodeGraph.getCurrentInstance().getDummyNode(id);
                if (!visited.contains(dummy.getFrom())) {
                    visited.add(dummy.getFrom());
                }
            }
        }
        for (int i = 0; i < visited.size(); i++) {
            newNodes.addLast(new DrawNode(visited.get(i)));
        }
        newNodes = newTopoSort(newNodes);
        assignLayersRoot(newNodes);
        LinkedList<DrawNode> newDrawNodes = new LinkedList<>();
        LinkedList<DummyNode> newDummyNodes = new LinkedList<>();
        double maxX = -Double.MAX_VALUE;
        for (int i = 0; i < newNodes.size(); i++) {
            double currentX = newNodes.get(i).getX();
            if (currentX > maxX) {
                maxX = currentX;
            }
        }
        for (int i = 0; i < newNodes.size(); i++) {
            if (Math.abs(newNodes.get(i).getX() - maxX) < 0.1) {
                nodes.get(newNodes.get(i).getIndex()).computeLength();
                newNodes.get(i).setWidth(nodes.get(newNodes.get(i).getIndex()).getLength());
                newNodes.get(i).setHeight(10);
                newNodes.get(i).setFill(Color.CRIMSON);
                newDrawNodes.add(newNodes.get(i));
            } else {
                Node dummyIn = nodes.get(newNodes.get(i).getIndex());
                int dummyOut = -1;
                for (int j = 0; j < dummyIn.getOutgoingEdges().length; j++) {
                    for (Double id : rootNodes) {
                        if (id.intValue() == dummyIn.getOutgoingEdges()[j]) {
                            dummyOut = j;
                            break;
                        }
                    }
                }
                newDummyNodes.add(new DummyNode(-1, newNodes.get(i).getIndex(), dummyOut, (int) maxX, 0));
            }
        }
        verticalSpacingNew(newDrawNodes, newDummyNodes);
        for (int i = 0; i < newDrawNodes.size(); i++) {
            drawNodes.addLast(newDrawNodes.get(i));
        }
        dummyNodes.addAll(newDummyNodes);
        rootNodes.clear();
        leafNodes.clear();
        retrieveDrawNodes(drawNodes.getLast().getX(), drawNodes.getFirst().getX());
        return new Pair(newDrawNodes, newDummyNodes);
    }

    /**
     * Determines the new nodes to be drawn after the current leaf nodes.
     * @return the new leaf nodes.
     */
    public Pair<LinkedList<DrawNode>, LinkedList<DummyNode>> addAtLeaf() {
        ArrayList<Integer> visited = new ArrayList<>();
        LinkedList<DrawNode> newNodes = new LinkedList<DrawNode>();
        for (Double id : leafNodes) {
            if (id >= 0) {
                for (int m : NodeGraph.getCurrentInstance().getNodes().get(id.intValue()).getOutgoingEdges()) {
                    if (!visited.contains(m)) {
                        visited.add(m);
                    }
                }
            } else {
                DummyNode dummy = NodeGraph.getCurrentInstance().getDummyNode(id);
                if (!visited.contains(dummy.getTo())) {
                    visited.add(dummy.getTo());
                }
            }
        }
        for (int i = 0; i < visited.size(); i++) {
            newNodes.addLast(new DrawNode(visited.get(i)));
        }
        newNodes = newTopoSort(newNodes);
        assignLayersLeaf(newNodes);
        LinkedList<DrawNode> newDrawNodes = new LinkedList<>();
        LinkedList<DummyNode> newDummyNodes = new LinkedList<>();
        double minX = Double.MAX_VALUE;
        for (int i = 0; i < newNodes.size(); i++) {
            if (newNodes.get(i).getX() < minX) {
                minX = newNodes.get(i).getX();
            }
        }
        for (int i = 0; i < newNodes.size(); i++) {
            if (newNodes.get(i).getX() == minX) {
                nodes.get(newNodes.get(i).getIndex()).computeLength();
                newNodes.get(i).setWidth(nodes.get(newNodes.get(i).getIndex()).getLength());
                newNodes.get(i).setHeight(10);
                newNodes.get(i).setFill(Color.CRIMSON);
                newDrawNodes.add(newNodes.get(i));
            } else {
                Node dummyOut = nodes.get(newNodes.get(i).getIndex());
                int dummyIn = -1;
                for (int j = 0; j < dummyOut.getIncomingEdges().length; j++) {
                    for (Double id : leafNodes) {
                        if (id.intValue() == dummyOut.getIncomingEdges()[j]) {
                            dummyIn = j;
                            break;
                        }
                    }
                }
                newDummyNodes.add(new DummyNode(-1, dummyIn, newNodes.get(i).getIndex(), (int) minX, 0));
            }
        }
        verticalSpacingNew(newDrawNodes, newDummyNodes);
        for (int i = 0; i < newDrawNodes.size(); i++) {
            drawNodes.addFirst(newDrawNodes.get(i));
        }
        dummyNodes.addAll(newDummyNodes);
        return new Pair(newDrawNodes, newDummyNodes);
    }

    /**
     * Determines what nodes have to be deleted at root node.
     * @return the xCoordinate of nodes that will get deleted.
     */
    public double removeAtRoot() {
        ArrayList<Integer> visited = new ArrayList<Integer>();
        for (Double id : rootNodes) {
            if (id >= 0) {
                for (int m : NodeGraph.getCurrentInstance().getNodes().get(id.intValue()).getOutgoingEdges()) {
                    if (!visited.contains(m)) {
                        visited.add(m);
                    }
                }
            } else {
                DummyNode dummy = NodeGraph.getCurrentInstance().getDummyNode(id);
                if (!visited.contains(dummy.getTo())) {
                    visited.add(dummy.getTo());
                }
            }
        }
        double minX = Double.MAX_VALUE;
        for (Integer i : visited) {
            for (int j = 0; j < drawNodes.size(); j++) {
                if (drawNodes.get(j).getIndex() == i && drawNodes.get(j).getX() < minX) {
                    minX = drawNodes.get(j).getX();
                    break;
                }
            }
        }
        ArrayList<DrawNode> removeNodes = new ArrayList<DrawNode>();
        while (drawNodes.getLast().getX() < minX) {
            removeNodes.add(drawNodes.removeLast());
        }
        leafNodes.clear();
        rootNodes.clear();
        retrieveDrawNodes(drawNodes.getLast().getX(), drawNodes.getFirst().getX());
        return minX;
    }

    /**
     * Determines what nodes have to be deleted at the leaf nodes.
     * @return the x-coordinate of the leaf nodes to be deleted.
     */
    public double removeAtLeaf() {
        ArrayList<Integer> visited = new ArrayList<Integer>();;
        for (Double id : leafNodes) {
            if (id >= 0) {
                for (int m : NodeGraph.getCurrentInstance().getNodes().get(id.intValue()).getIncomingEdges()) {
                    if (!visited.contains(m)) {
                        visited.add(m);
                    }
                }
            } else {
                DummyNode dummy = NodeGraph.getCurrentInstance().getDummyNode(id);
                if (!visited.contains(dummy.getFrom())) {
                    visited.add(dummy.getFrom());
                    break;
                }
            }
        }
        double maxX = -Double.MAX_VALUE;
        for (Integer i : visited) {
            for (int j = 0; j < drawNodes.size(); j++) {
                if (drawNodes.get(j).getIndex() == i && drawNodes.get(j).getX() > maxX) {
                    maxX = drawNodes.get(j).getX();
                }
            }
        }
        ArrayList<DrawNode> removeNodes = new ArrayList<DrawNode>();
        while (drawNodes.getFirst().getX() > maxX) {
            removeNodes.add(drawNodes.remove());
        }
        return drawNodes.getFirst().getX();
    }
    /**
     * Getter for a Dummy Node.
     * @param id id of the Dummy Node.
     * @return the Dummy Node.
     */
    private DummyNode getDummyNode(double id) {
        for (int i = 0; i < dummyNodes.size(); i++) {
            if (dummyNodes.get(i).getAbsId() == id) {
                return dummyNodes.get(i);
            }
        }
        return null;
    }

     /** Get the root nodes of the current SubGraph.
     * @return LinkedList containing the AbsIds of the Root Nodes of the current SubGraph.
     */
    protected LinkedList<Double> getRootNodes() {
        return rootNodes;
    }

    /**
     * Get the leaf nodes of the current SubGraph.
     * @return LinkedList containing the AbsIds of the Lead Nodes of the current SubGraph.
     */
    protected LinkedList<Double> getLeafNodes() {
        return leafNodes;
    }
}

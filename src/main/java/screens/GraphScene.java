package screens;

import datastructure.DrawNode;
import datastructure.DummyEdge;
import datastructure.NodeGraph;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import parsing.Parser;

import java.util.LinkedList;

/**
 * Implementation of the window that handles graph visualization.
 */
 public final class GraphScene extends Pane {

    /**
     * State for handling center queries.
     */
    private final INodeHandler center;

    /**
     * State for handling information display queries.
     */
    private final INodeHandler info;

    /**
     * The current state for node events.
     */
    private INodeHandler state;

    /**
     * The factory the GraphScene uses to create JavaFX elements.
     */
    private FXElementsFactory fxElementsFactory;

    /**
     * Event handler for when a node or edge is clicked.
     */
     private EventHandler<MouseEvent> click = event -> {

        if (event.getSource() instanceof DrawNode) {
            DrawNode rect = (DrawNode) (event.getSource());
            state.handle(rect);
        } else if (event.getSource() instanceof Line) {
            Line l = (Line) (event.getSource());
            String edgeNodes = l.getId();
            Window.getInfoScreen().getTextArea().appendText("Edge from node " + edgeNodes.substring(0, edgeNodes.indexOf("-")) + " to " + edgeNodes.substring(edgeNodes.indexOf("-") + 1, edgeNodes.length()) + "\n");
        }
     };

    /**
     * GraphScene pane constructor.
     * @param fact the Factory used to create JavaFX elements.
     */
     /*package*/ GraphScene(FXElementsFactory fact) {
         center = new NodeCenter(this);
         info = new NodeInfo();
         state = info;
         this.fxElementsFactory = fact;
     }

    /**
     * Draws graph on the screen.
     * @param id Id of the node/segment.
     * @param radius Radius.
     * @return The thread drawing is running in.
     */
    public Thread drawGraph(final int id, final int radius) {
        this.getChildren().clear();

        Thread thread = new Thread() {
            public void run() {
                try {
                    Parser.getThread().join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                drawGraphUtil(id, radius);
            }
        };
        thread.start();
        return thread;
    }

    /**
     * The recursive method used to draw all nodes within the radius from the center node.
     * @param center The node to take as center.
     * @param radius The maximum depth we want to go.
     */
    private void drawGraphUtil(int center, int radius) {
        NodeGraph nodeGraph = NodeGraph.getCurrentInstance();
        nodeGraph.generateDrawNodes(center, radius);
        LinkedList<DrawNode> drawNodes = nodeGraph.getDrawNodes();

        for (DrawNode dNode : drawNodes) {
            dNode.setX(dNode.getX() - dNode.getWidth() / 2);
            dNode.setOnMousePressed(click);
            Platform.runLater(() -> this.getChildren().add(dNode));
            DrawNode nOut;
            for (int i : nodeGraph.getNode(dNode.getIndex()).getOutgoingEdges()) {
                nOut = nodeGraph.getDrawNode(i);
                if (nOut != null && nOut.getLayer() - dNode.getLayer() <= 100) {
                    drawLine(dNode, nOut, 2);
                }
            }
        }

        drawDummies();
    }

    /**
     * Draws the DummyEdges from the nodegraph.
     */
    private void drawDummies() {
        NodeGraph nodeGraph = NodeGraph.getCurrentInstance();
        LinkedList<DummyEdge> dummyEdges = nodeGraph.getDummyEdges();
        DrawNode parent;
        DrawNode child;
        String id;

        for (DummyEdge e : dummyEdges) {
            parent = nodeGraph.getDrawNode(e.getParent());
            child = nodeGraph.getDrawNode(e.getChild());
            id = e.getParent() + "-" + e.getChild();
            if (parent != null) {
                drawLine(parent, e, 2);
            } else {
                drawLine(id, 2, e.getFirstX() - 100,
                        e.getFirstY() + 5, e.getFirstX(),
                        e.getFirstY() + 5);
            }
            for (int i = 1; i < e.getLength(); i++) {
                drawLine(id, 2, e.getX(i - 1),
                        e.getY(i - 1) + 5, e.getX(i),
                        e.getY(i) + 5);
            }
            if (child != null) {
                drawLine(e, child, 2);
            } else {
                drawLine(id, 2, e.getLastX(),
                        e.getLastY() + 5, e.getLastX() + 100,
                        e.getLastY() + 5);
            }
        }
    }

    /**
     * Draws new root nodes.
     * @param newNodes the new root nodes.
     * @param newDummies the dummy nodes needed to draw the nodes.
     */
    private void drawUpdateRoot(LinkedList<DrawNode> newNodes, LinkedList<DummyEdge> newDummies) {

    }

    /**
     * Draws new leaf nodes.
     * @param newNodes the new leaf nodes.
     * @param newDummies the dummy nodes needed to draw the nodes.
     */
    private void drawUpdateLeaf(LinkedList<DrawNode> newNodes, LinkedList<DummyEdge> newDummies) {

    }

    /**
     * Zooms out on the scene.
     * @param transX x-coordinate of cursor
     * @param transY y-coordinate of cursor
     */
    public void zoomOut(double transX, double transY) {

    }

    /**
     * Zooms in on the scene.
     * @param transX x-coordinate of cursor
     * @param transY y-coordinate of cursor
     */
    public void zoomIn(double transX, double transY) {

    }

    /**
     * Deletes root nodes to remove from the scene.
     * @param minX x-coordinate of nodes to remove.
     */
    private void removeNodesRoot(double minX) {

    }

    /**
     * Deletes leaf nodes to remove from the scene.
     * @param maxX x-coordinate of nodes to remove.
     */
    private void removeNodesLeaf(double maxX) {

    }

    /**
     * Draws a DrawNode.
     * @param node The DrawNode.
     */
    private void drawNode(DrawNode node) {
        node.setWidth(NodeGraph.getCurrentInstance().getNode(node.getIndex()).getLength());
        node.setX(node.getLayer() - node.getWidth() / 2);
        node.setHeight(10);
        node.setFill(Color.CRIMSON);
        node.setOnMousePressed(click);
        this.getChildren().add(node);
    }

    /**
     * Draws a line from the parent node to the child node.
     * @param parent The parent node.
     * @param child The child node.
     * @param width The width of the line.
     */
    private void drawLine(DrawNode parent, DrawNode child, double width) {
        drawLine(parent.getIndex() + "-" + child.getIndex(), width,
                parent.getBoundsInLocal().getMaxX(),
                parent.getBoundsInLocal().getMinY() + 5,
                child.getBoundsInLocal().getMinX(),
                child.getBoundsInLocal().getMinY() + 5);
    }

    /**
     * Draws the first line from a parent node to a child node through a dummy edge.
     * @param parent The parent node.
     * @param edge The dummy edge.
     * @param width The width of the line.
     */
    private void drawLine(DrawNode parent, DummyEdge edge, double width) {
        drawLine(parent.getIndex() + "-" + edge.getChild(), width,
                parent.getBoundsInLocal().getMaxX(),
                parent.getBoundsInLocal().getMinY() + 5,
                edge.getFirstX(),
                edge.getFirstY() + 5
                );
    }

    /**
     * Draws the last line to a child node from a parent node through a dummy edge.
     * @param edge The dummy edge.
     * @param child The child node.
     * @param width The width of the line.
     */
    private void drawLine(DummyEdge edge, DrawNode child, double width) {
        drawLine(edge.getParent() + "-" + child.getIndex(), width,
                edge.getLastX(), edge.getLastY() + 5,
                child.getBoundsInLocal().getMinX(),
                child.getBoundsInLocal().getMinY() + 5
                );
    }

    /**
     * Draws a line in the graphscene.
     * @param id The id of the line.
     * @param width The width of the line.
     * @param startX The starting X coordinate of the line.
     * @param startY The starting Y coordinate of the line.
     * @param endX The ending X coordinate of the line.
     * @param endY The ending Y coordinate of the line.
     */
    private void drawLine(String id, double width, double startX, double startY, double endX, double endY) {
        Line l = new Line();
        l.setId(id);
        l.setStrokeWidth(width);
        l.setStartX(startX);
        l.setStartY(startY);
        l.setEndX(endX);
        l.setEndY(endY);
        l.setOnMousePressed(click);
        Platform.runLater(() -> this.getChildren().add(l));
    }

    /**
     * Switches event handler to center queries.
     */
    public void switchToCenter() {
        state = center;
    }

    /**
     * Switches event handler to graph information handling.
     */
    public void switchToInfo() {
        state = info;
    }

    /**
     * Getter for the current state.
     * @return the current state.
     */
    public INodeHandler getState() {
        return this.state;
    }

    /**
     * Getter for the center-state.
     * @return the center state.
     */
    public INodeHandler getCenter() {
        return this.center;
    }

    /**
     * Getter for the info-state.
     * @return the info state.
     */
    public INodeHandler getInfo() {
        return this.info;
    }

}

package screens;

import datastructure.DrawNode;
import datastructure.DummyNode;
import datastructure.NodeGraph;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Set;
import java.util.TreeSet;

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
            /**
             * DrawNode object that is linked to the Node object.
             */
            DrawNode rect = (DrawNode) (event.getSource());
            state.handle(rect);
        } else if (event.getSource() instanceof Line) {
            /**
             * Line object.
             */
            Line l = (Line) (event.getSource());
            /**
             * Id of line object.
             */
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
     */
    public void drawGraph(final int id, final int radius) {
        if (radius < 5 || radius > 500) {
            Stage newStage = this.fxElementsFactory.createStage();
            Group group = this.fxElementsFactory.createGroup();
            Label label = this.fxElementsFactory.createLabel("Radius is out of bounds");
            group.getChildren().add(label);
            Scene scene = this.fxElementsFactory.createScene(group, 150, 100);
            this.fxElementsFactory.setScene(newStage, scene);
            this.fxElementsFactory.show(newStage);
            return;
        }
        this.getChildren().clear();
        drawGraphUtil(id, radius);
    }

    /**
     * The recursive method used to draw all nodes within the radius from the center node.
     * @param center The node to take as center.
     * @param radius The maximum depth we want to go.
     */
    private void drawGraphUtil(int center, int radius) {
        NodeGraph.getCurrentInstance().generateDrawNodes(center, radius);
        for (DrawNode dNode : NodeGraph.getCurrentInstance().getDrawNodes()) {
            dNode.setHeight(10);
            dNode.setFill(Color.CRIMSON);
            dNode.setOnMousePressed(click);
            dNode.setX(dNode.getX() - dNode.getWidth() / 2);
            dNode.setY(dNode.getY());
            this.getChildren().add(dNode);
            DrawNode nOut;
            for (int i : NodeGraph.getCurrentInstance().getNodes().get(dNode.getIndex()).getOutgoingEdges()) {
                nOut = NodeGraph.getCurrentInstance().getDrawNode(i);
                if (nOut != null && nOut.getBoundsInLocal().getMinX() - dNode.getBoundsInLocal().getMaxX() <= 100) {
                    drawLine(dNode.getIndex() + "-" + i, 2, dNode.getBoundsInLocal().getMaxX(), dNode.getBoundsInLocal().getMinY() + 5, nOut.getBoundsInLocal().getMinX(), nOut.getBoundsInLocal().getMinY() + 5);
                }
            }
        }

        DummyNode current;
        DummyNode current2;
        DrawNode dN;
        Set<DummyNode> visited = new TreeSet<>();
        for (int i = NodeGraph.getCurrentInstance().getDummyNodes().size() - 1; i >= 0; i--) {
            current = NodeGraph.getCurrentInstance().getDummyNodes().get(i);
            if (!visited.contains(current)) {
                visited.add(current);
                if (!visited.contains(current.prevInEdge())) {
                    dN = NodeGraph.getCurrentInstance().getDrawNode(current.getFrom());
                    if (dN != null) {
                        drawLine(current.getFrom() + "-" + current.getTo(), 2, dN.getBoundsInLocal().getMaxX(), dN.getBoundsInLocal().getMinY() + 5, current.getX(), current.getY() + 5);
                    }
                }
                for (int j = NodeGraph.getCurrentInstance().getDummyNodes().size() - 1; j >= 0; j--) {
                    current2 = NodeGraph.getCurrentInstance().getDummyNodes().get(j);
                    if (current.nextInEdge(current2)) {
                        drawLine(current.getFrom() + "-" + current.getTo(), 2, current.getX(), current.getY() + 5, current2.getX(), current2.getY() + 5);
                    }
                }
                if (current.getId() == -1) {
                    dN = NodeGraph.getCurrentInstance().getDrawNode(current.getTo());
                    if (dN != null) {
                        drawLine(current.getFrom() + "-" + current.getTo(), 2, current.getX(), current.getY() + 5, dN.getBoundsInLocal().getMinX(), dN.getBoundsInLocal().getMinY() + 5);
                    }
                }
            }
        }
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
        this.getChildren().add(l);
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

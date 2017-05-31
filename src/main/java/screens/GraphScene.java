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
                    Line l = new Line();
                    l.setId(dNode.getIndex() + "-" + i);
                    l.setStrokeWidth(2);
                    l.setStartX(dNode.getBoundsInLocal().getMaxX());
                    l.setStartY(dNode.getBoundsInLocal().getMinY() + 5);
                    l.setEndX(nOut.getBoundsInLocal().getMinX());
                    l.setEndY(nOut.getBoundsInLocal().getMinY() + 5);
                    l.setOnMousePressed(click);
                    this.getChildren().add(l);
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
                        Line l1 = new Line();
                        l1.setId(current.getFrom() + "-" + current.getTo());
                        l1.setStrokeWidth(5);
                        l1.setStartX(dN.getBoundsInLocal().getMaxX());
                        l1.setStartY(dN.getBoundsInLocal().getMinY() + 5);
                        l1.setEndX(current.getX());
                        l1.setEndY(current.getY() + 5);
                        l1.setOnMousePressed(click);
                        this.getChildren().add(l1);
                    }
                }
                for (int j = NodeGraph.getCurrentInstance().getDummyNodes().size() - 1; j >= 0; j--) {
                    current2 = NodeGraph.getCurrentInstance().getDummyNodes().get(j);
                    if (current.nextInEdge(current2)) {
                        Line l2 = new Line();
                        l2.setId(current.getFrom() + "-" + current.getTo());
                        l2.setStrokeWidth(5);
                        l2.setStartX(current.getX());
                        l2.setStartY(current.getY() + 5);
                        l2.setEndX(current2.getX());
                        l2.setEndY(current2.getY() + 5);
                        l2.setOnMousePressed(click);
                        this.getChildren().add(l2);
                    }
                }
                if (current.getId() == -1) {
                    dN = NodeGraph.getCurrentInstance().getDrawNode(current.getTo());
                    if (dN != null) {
                        Line l3 = new Line();
                        l3.setId(current.getFrom() + "-" + current.getTo());
                        l3.setStrokeWidth(5);
                        l3.setStartX(current.getX());
                        l3.setStartY(current.getY() + 5);
                        l3.setEndX(dN.getBoundsInLocal().getMinX());
                        l3.setEndY(dN.getBoundsInLocal().getMinY() + 5);
                        l3.setOnMousePressed(click);
                        this.getChildren().add(l3);
                    }
                }
            }
        }
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

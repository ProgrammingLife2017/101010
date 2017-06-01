package screens;

import datastructure.DrawNode;
import datastructure.Node;
import datastructure.NodeGraph;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

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
    private FXElementsFactory factory;

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
         this.factory = fact;
     }

    /**
     * Draws graph on the screen.
     * @param id Id of the node/segment.
     * @param radius Radius.
     */
    public void drawGraph(final int id, final int radius) {
        if (radius < 5 || radius > 10000) {
            Stage newStage = this.factory.createStage();
            Group group = this.factory.createGroup();
            Label label = this.factory.createLabel("Radius is out of bounds");
            group.getChildren().add(label);
            Scene scene = this.factory.createScene(group, 150, 100);
            this.factory.setScene(newStage, scene);
            this.factory.show(newStage);
        } else {
            NavigationInfo.getInstance().setCurrentRadius(radius);
            NavigationInfo.getInstance().setCurrentCenterNode(id);
            this.getChildren().clear();
            drawGraphUtil(NodeGraph.getCurrentInstance().getNode(id), radius);
        }
    }

    /**
     * The recursive method used to draw all nodes within the radius from the center node.
     * @param center The node to take as center.
     * @param radius The maximum depth we want to go.
     */
    private void drawGraphUtil(Node center, int radius) {
        double x = center.getX();
        for (int i = 0; i < NodeGraph.getCurrentInstance().getSize(); i++) {
            Node current = NodeGraph.getCurrentInstance().getNode(i);
            if (current.getX() - 543 > NodeGraph.getCurrentInstance().getMaxX()) {
                NodeGraph.getCurrentInstance().setMaxX(current.getX() - 543);
            }
            if (current.getX() >= x - 40 * radius && current.getX() <= x + 40 * radius) {
                DrawNode newRect = new DrawNode(i);
                newRect.setId(Integer.toString(i));
                newRect.setOnMousePressed(click);
                newRect.setX(current.getX() - 40);
                newRect.setY(current.getY());
                newRect.setWidth(20);
                newRect.setHeight(10);
                this.getChildren().add(newRect);
                for (Integer j: current.getOutgoingEdges()) {
                    Node out = NodeGraph.getCurrentInstance().getNode(j);
                    Line l = new Line();
                    l.setId(i + "-" + j);
                    l.setStrokeWidth(2);
                    l.setStartX(newRect.getBoundsInLocal().getMaxX() + newRect.getTranslateX());
                    l.setStartY(newRect.getBoundsInLocal().getMinY() + 5);
                    l.setEndX(out.getX() - 40);
                    l.setEndY(out.getY() + 5);
                    l.setOnMousePressed(click);
                    this.getChildren().add(l);
                }
            }
        }
        Window.updateIndicator(center);
    }

    /**
     * Updates the visualized graph when zooming out.
     */
    public void zoomOut() {
        NodeGraph.getCurrentInstance().addAtRoot();
        NodeGraph.getCurrentInstance().addAtLeaf();
    }
    /**
     * Updates the visualized graph when zooming in.
     */
    public void zoomIn() {
        NodeGraph.getCurrentInstance().removeAtRoot();
        NodeGraph.getCurrentInstance().removeAtLeaf();
    }

    /*
    addAtRoot() {
    for (Node n : rootnodes)
        for (int m : n.getIncomingEdges)
            if(!list.contains(m))
                list.add(NodeGraph.getNode(m));
    list = assignRootLayer(list);
    list = verticalSpacingRoot(list);
    for (Node n : list)
        drawnNodes.append(n);
        GraphScene.draw(n);
    }

    removeAtRoot() {
    for (Node n : rootnodes)
        for (int m: n.getOutgoingEdges)
            if(!list.contains(m))
                list.add(NodeGraph.getNode(m));
        GraphScene.getChildren().remove(GraphScene.lookup(Integer.toString(m)));
     while(rootnodes.contains(drawnNodes.getFirst())
        drawnNodes.removeFirst();
     rootnodes = list;
    }
     */

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

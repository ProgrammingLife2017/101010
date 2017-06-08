package screens.nodehandlers;

import datastructure.DrawNode;
import javafx.scene.shape.Line;
import screens.scenes.Controller;
import screens.scenes.GraphScene;

/**
 * Implementation of the state that handles click events regarding the center query of selected node.
 */
public class NodeCenter implements INodeHandler {

    /**
     * Screen that displays the graph.
     */
    private GraphScene graphScene;

    /**
     * Constructor.
     * @param sc Scene for displaying graphs.
     */
    public NodeCenter(GraphScene sc) {
        this.graphScene = sc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleNode(DrawNode node) {
        graphScene.drawGraph(node.getIndex(), Controller.getRadius());
    }

    @Override
    public void handleLine(Line line) {

    }

}

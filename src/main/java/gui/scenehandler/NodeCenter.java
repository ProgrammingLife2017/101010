package gui.scenehandler;

import datastructure.DrawNode;
import gui.interaction.Controller;
import gui.GraphScene;
import javafx.scene.shape.Line;

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
        handleNodeWithRadius(node.getIndex(), Controller.getRadius());
    }

    private void handleNodeWithRadius(int center, int radius) {
        graphScene.drawGraph(center, radius);
    }

    @Override
    public void handleLine(Line line) {

    }

}

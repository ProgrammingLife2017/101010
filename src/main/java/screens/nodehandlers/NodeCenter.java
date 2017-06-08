package screens.nodehandlers;

import datastructure.DrawNode;
import javafx.scene.shape.Line;
import services.ServiceLocator;

/**
 * Implementation of the state that handles click events regarding the center query of selected node.
 */
public class NodeCenter implements INodeHandler {

    /**
     * Screen that displays the graph.
     */
    private ServiceLocator serviceLocator;

    /**
     * Constructor.
     * @param sL ServiceLocator for locating services registered in that object.
     */
    public NodeCenter(ServiceLocator sL) {
        this.serviceLocator = sL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleNode(DrawNode node) {
        serviceLocator.getGraphScene().drawGraph(node.getIndex(), serviceLocator.getController().getRadius());
        serviceLocator.getGraphScene().switchToInfo();
    }

    @Override
    public void handleLine(Line line) {

    }

}

package screens.nodehandlers;

import datastructure.DrawNode;
import javafx.scene.shape.Line;
import services.ServiceLocator;

/**
 * Implementation of the state that handles click events regarding the information of selected node.
 */
public class NodeInfo implements INodeHandler {
    /**
     * Contains references to other services.
     */
    private ServiceLocator serviceLocator;

    /**
     * NodeInfo state handler constructor.
     * @param sL ServiceLocator that contains references to other objects.
     */
    public NodeInfo(ServiceLocator sL) {
        serviceLocator = sL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleNode(DrawNode node) {
        serviceLocator.getInfoScreen().displayNodeInfo(node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleLine(Line line) {
        serviceLocator.getInfoScreen().displayLineInfo(line);
    }
}

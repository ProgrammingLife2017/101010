package gui.scenehandler;

import datastructure.DrawNode;
import gui.Window;
import javafx.scene.shape.Line;

/**
 * Implementation of the state that handles click events regarding the information of selected node.
 */
public class NodeInfo implements INodeHandler {

    /**
     * NodeInfo state handler constructor.
     */
    public NodeInfo() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleNode(DrawNode node) {
        Window.getInfoScreen().displayNodeInfo(node);
    }

    @Override
    public void handleLine(Line line) {
        Window.getInfoScreen().displayLineInfo(line);
    }
}

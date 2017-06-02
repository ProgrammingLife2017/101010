package screens.scenehandler;

import datastructure.DrawNode;
import datastructure.NodeGraph;
import screens.Window;
import screens.scenehandler.INodeHandler;

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
    public void handle(DrawNode node) {
        Window.getInfoScreen().getTextArea().appendText(NodeGraph.getCurrentInstance().getSegment(node.getIndex()) + "\n");
    }
}

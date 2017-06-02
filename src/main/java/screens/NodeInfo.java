package screens;

import datastructure.DrawNode;
import datastructure.NodeGraph;

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

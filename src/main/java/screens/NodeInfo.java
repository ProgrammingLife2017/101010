package screens;

import datastructure.DrawNode;
import datastructure.NodeGraph;

/**
 * Implementation of the state that handles click events regarding the information of selected node.
 */
public class NodeInfo implements INodeHandler {

    private InfoScreen infoScreen;
    /**
     * NodeInfo state handler constructor.
     */
    public NodeInfo(InfoScreen iScreen) {
        this.infoScreen = iScreen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(DrawNode node) {
        this.infoScreen.getTextArea().appendText(NodeGraph.getCurrentInstance().getSegment(node.getIndex()) + "\n");
    }
}

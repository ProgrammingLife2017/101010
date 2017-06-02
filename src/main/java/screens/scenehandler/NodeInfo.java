package screens.scenehandler;

import datastructure.DrawNode;
import datastructure.NodeGraph;
import screens.InfoScreen;

/**
 * Implementation of the state that handles click events regarding the information of selected node.
 */
public class NodeInfo implements INodeHandler {
    /**
     * Window to print content.
     */
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

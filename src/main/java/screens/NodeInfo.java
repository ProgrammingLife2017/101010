package screens;

import datastructure.DrawNode;
import datastructure.NodeGraph;

/**
 * Implementation of the state that handles click events regarding the information of selected node.
 */
public class NodeInfo implements INodeHandler {
    /**
     * Screen used for displaying graphs.
     */
    private GraphScene scene;

    /**
     * NodeInfo state handler constructor.
     * @param sc Scene where the graph is drawn.
     */
    public NodeInfo(GraphScene sc) {
        this.scene = sc;
    }

    @Override
    public void handle(DrawNode node) {
        Window.getInfoScreen().getTextArea().appendText(NodeGraph.getCurrentInstance().getSegment(node.getIndex()) + "\n");
    }
}

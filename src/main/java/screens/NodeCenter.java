package screens;

import datastructure.Node;

/**
 * Implementation of the state that handles click events regarding the radius of selected node.
 */
public class NodeCenter implements INodeHandler {
    private GraphScene scene;


    public NodeCenter(GraphScene sc) {
        this.scene = sc;
    }

    public void handle(Node node) {
        Window.getInfoScreen().getTextArea().appendText("hello world" + "\n");
        scene.switchToInfo();
    }
}

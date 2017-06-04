package gui.scenehandler;

import datastructure.DrawNode;
import javafx.scene.shape.Line;

/**
 * Interface for state classes that handle node events.
 */
public interface INodeHandler {

    /**
     * Executes operations when a node is selected.
     * @param node Node to work with.
     */
    void handleNode(DrawNode node);

    void handleLine(Line line);
}

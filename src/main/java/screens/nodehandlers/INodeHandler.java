package screens.nodehandlers;

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

    /**
     * Executes operations when a line is selected.
     * @param line Line object to work with.
     */
    void handleLine(Line line);

}
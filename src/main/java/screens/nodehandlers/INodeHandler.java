package screens.nodehandlers;

import datastructure.DrawNode;

/**
 * Interface for state classes that handle node events.
 */
public interface INodeHandler {

    /**
     * Executes operations when a node is selected.
     * @param node Node to work with.
     */
    void handle(DrawNode node);
}

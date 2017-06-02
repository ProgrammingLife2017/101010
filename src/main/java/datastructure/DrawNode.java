package datastructure;

import javafx.scene.shape.Rectangle;

/**
 * Created by 101010.
 */
public class DrawNode extends Rectangle {

    /**
     * The id of the node corresponding to the node in the GFA file.
     */
    private int id;

    /**
     * Constructor for the draw node.
     * @param index the id of the node.
     */
    public DrawNode(int index) {
        this.id = index;
    }

    /**
     * Getter for the id of the draw node.
     * @return the id of the draw node.
     */
    public int getIndex() {
        return this.id;
    }

    /**
     * Checks if given object is the same as current DrawNode.
     * @param other The objectto be checked.
     * @return true iff object is the same.
     */
    public boolean equals(Object other) {
         if (other instanceof DrawNode) {
             DrawNode that = (DrawNode) other;
             return this.id == that.id;
         }
         return false;
    }

    /**
     * Givens the hashcode of the DrawNode
     * @return the hashcode
     */
    public int hashCode() {
        return id;
    }

}

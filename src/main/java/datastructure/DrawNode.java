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
     * The layer this DrawNode is in.
     */
    private int layer;

    /**
     * Constructor for the draw node.
     * @param index the id of the node.
     */
    public DrawNode(int index) {
        this.id = index;
        this.layer = 0;
    }

    /**
     * Constructor for a DrawNode in a specific layer.
     * @param index The id of the node.
     * @param layer The layer the node is in.
     */
    public DrawNode(int index, int layer) {
       this.id = index;
       this.layer = layer;
       this.setX(layer);
    }

    /**
     * Getter for the id of the draw node.
     * @return the id of the draw node.
     */
    public int getIndex() {
        return this.id;
    }

    /**
     * Retrieves the layer of the DrawNode.
     * @return The layer of the DrawNode.
     */
    public int getLayer() {
        return this.layer;
    }

    /**
     * Sets the layer of the DrawNode.
     * @param newLayer The new layer of the DrawNode.
     */
    public void setLayer(int newLayer) {
        this.setX(Math.abs(newLayer - this.getBoundsInLocal().getMinX() - layer));
        this.layer = newLayer;
    }

    /**
     * Increments the layer of the DrawNode, effectively moving it to the right.
     * @param offset The amount the layer is incremented by.
     */
    public void moveRight(int offset) {
        setLayer(layer + offset);
    }

    /**
     * Increments the layer of the DrawNode using the default offset.
     */
    public void moveRight() {
        moveRight(100);
    }

    /**
     * Decrements the layer of the DrawNode, effectively moving it to the left.
     * @param offset The amount the layer is decremented by.
     */
    public void moveLeft(int offset) {
        setLayer(layer - offset);

    }

    /**
     * Decrements the layer of the DrawNode using the default offset.
     */
    public void moveLeft() {
        moveLeft(100);
    }

    /**
     * Checks if given object is the same as current DrawNode.
     * @param other The object to be checked.
     * @return true iff object is the same.
     */
    @Override
    public boolean equals(Object other) {
         if (other instanceof DrawNode) {
             DrawNode that = (DrawNode) other;
             return this.id == that.id;
         }
         return false;
    }

    /**
     * Givens the hashcode of the DrawNode.
     * @return The hashcode of the DrawNode.
     */
    @Override
    public int hashCode() {
        return id;
    }

}

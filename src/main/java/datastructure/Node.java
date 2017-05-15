package datastructure;

import javafx.scene.shape.Rectangle;

/**
 * Created by 101010.
 */
public class Node extends Rectangle{
    /**
     * The id of the node.
     */
    private int id;

    /**
     * The length of the segment corresponding to the node.
     */
    private int length;

    /**
     * Array of ids on the other end of the outgoing edges
     * from this node.
     */
    private int[] to;

    /**
     * Array of ids on the other end of the incoming edges
     * to this node.
     */
    private int[] from;

    /**
     * Empty constructor for Node.
     */
    public Node() {
        this.id = 0;
        this.length = 0;
        this.to = new int[0];
        this.from = new int[0];
    }

    /**
     * Constructor for Node.
     * @param id the id of the node.
     * @param length the length of the node.
     * @param outgoingEdges array of outgoing edge ids.
     * @param incomingEdges array of incoming edge ids.
     */
    public Node(final int id,
                final int length,
                final int[] outgoingEdges,
                final int[] incomingEdges) {
        this.id = id;
        this.length = length;
        this.to = outgoingEdges;
        this.from = incomingEdges;
    }

    /**
     *
     * @return the length of the node.
     */
    public int getLength() {
        return length;
    }

    /**
     *
     * @return the ids of the outgoing edges from this node.
     */
    public int[] getOutgoingEdges() {
        return to;
    }

    /**
     *
     * @return the ids of the incoming edges to this node.
     */
    public int[] getIncomingEdges() {
        return from;
    }

    /**
     *
     * @param other the object to compare to.
     * @return whether this Node is equal to the other object.
     */
    public boolean equals(final Object other) {
        if (other instanceof Node) {
            Node that = (Node) other;
            if (this.to.length == that.to.length
                    && this.from.length == that.from.length
                    && this.id == that.id
                    && this.length == that.length) {
                for (int i = 0; i < this.to.length; i++) {
                    if (this.to[i] != that.to[i]) {
                        return false;
                    }
                }
                for (int j = 0; j < this.from.length; j++) {
                    if (this.from[j] != that.from[j]) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}

package datastructure;

import javafx.scene.shape.Rectangle;

/**
 * Created by 101010.
 */
public class Node extends Rectangle{
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
        this.length = 0;
        this.to = new int[0];
        this.from = new int[0];
    }

    /**
     * Constructor for Node.
     * @param length the length of the node.
     * @param outgoingEdges array of outgoing edge ids.
     * @param incomingEdges array of incoming edge ids.
     */
    public Node(final int length,
                final int[] outgoingEdges,
                final int[] incomingEdges) {
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
     * Adds a node id to the list of incoming edges.
     *
     * @param incomingId
     *        The id of the incoming edge's node.
     */
    public void addIncomingEdge(final int incomingId) {
        int[] temp = new int[from.length + 1];

        for (int i = 0; i < from.length; i++) {
            temp[i] = from[i];
        }

        temp[from.length] = incomingId;

        from = temp;
    }

    /**
     * Adds a node if to the list of outgoing edges.
     *
     * @param outgoingId
     *        The id of the outgoing edge's node.
     */
    public void addOutgoingEdge(final int outgoingId) {
        int[] temp = new int[to.length + 1];

        for (int i = 0; i < to.length; i++) {
            temp[i] = to[i];
        }

        temp[to.length] = outgoingId;

        to = temp;
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

    /**
     * Sets the incoming edges.
     * @param incoming The incoming edges.
     */
    public void setIncomingEdges(int[] incoming) {
        from = incoming;
    }
}

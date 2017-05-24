package datastructure;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by 101010.
 */
public class Node implements Serializable {
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
     * Number of incoming edges of a node.
     */
    private int inDegree;

    /**
     * Child number of the node.
     */
    private int child;

    /**
     * X-coordinate assigned to the node.
     */
    private int x;

    /**
     * Y-coordinate assigned to the node.
     */
    private int y;

    /**
     * Empty constructor for Node.
     */
    public Node() {
        this.length = 0;
        this.to = new int[0];
        this.from = new int[0];
        this.child = 0;
        this.x = 0;
        this.y = 0;
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
        this.child = 0;
        this.x = 0;
        this.y = 0;
    }

    /**
     * Getter for the x-coordinate of the node.
     * @return the x-coordinate of a node.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Getter for the y-coordinate of the node.
     * @return the y-coordinate of a node.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Setter for the x-coordinate of a node.
     * @param newX the new x-coordinate.
     */
    public void setX(int newX) {
        this.x = newX;
    }

    /**
     * Setter for the y-coordinate of a node.
     * @param newY the new y-coordinate.
     */
    public void setY(int newY) {
        this.y = newY;
    }

    /**
     * Getter for the child number of a node.
     * @return the child number of the node.
     */
    public int getChild() {
        return this.child;
    }

    /**
     * Setter for the child of the node.
     * @param newChild the new child number of the node.
     */
    public void setChild(int newChild) {
        this.child = newChild;
    }

    /**
     * Getter for the in-degree of a node.
     * @return the in-degree of a node.
     */
    public int getInDegree() {
        return this.inDegree;
    }

    /**
     * Setter for the in-degree of a node.
     * @param newIn the new in-degree of a node.
     */
    public void setInDegree(int newIn) {
        this.inDegree = newIn;
    }

    /**
     * Getter for the length of the sequence of a node.
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
     * Compares this Node to an object.
     * Returns true if that object is a Node equal to this Node.
     * Returns false otherwise.
     * @param other the object to compare to.
     * @return whether this Node is equal to the other object.
     */
    @Override
    public boolean equals(final Object other) {
        if (other instanceof Node) {
            Node that = (Node) other;
            if (this.to.equals(that.to)
                    && this.from.equals(that.from)
                    && this.length == that.length) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates hashCode of this Node object.
     * @return The hashCode of this Node object.
     */
    @Override
    public int hashCode() {
        int result = length;
        result = 31 * result + Arrays.hashCode(to);
        result = 31 * result + Arrays.hashCode(from);
        return result;
    }

    /**
     * Sets the incoming edges.
     * @param incoming The incoming edges.
     */
    public void setIncomingEdges(int[] incoming) {
        from = incoming;
    }
}

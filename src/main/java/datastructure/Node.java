package datastructure;

/**
 * Created by 101010.
 */
public class Node {
    /**
     *  The id of the Node.
     */
    private int id;

    /**
     *  The length of the segment of the node.
     */
    private int length;

    /**
     * Basic constructor for a Node.
     * @param id The id of the Node.
     * @param length The length of the segment of the node.
     */
    public Node(int id, int length) {
        this.id = id;
        this.length = length;
    }

    /**
     * Retrieves the id of the node.
     * @return The id of the node.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the length of the Node.
     * @param newLength The new length of the node.
     */
    public void setLength(int newLength) {
        this.length = newLength;
    }

    /**
     * Retrieves the length of the segment of the node.
     * @return The length of the segment of the node.
     */
    public int getLength() {
        return length;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Node) {
            Node that = (Node) other;
            return this.id == that.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }
}

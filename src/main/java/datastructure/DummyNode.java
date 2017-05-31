package datastructure;

/**
 * Created by 101010.
 */
public class DummyNode {
    /**
     * The id of this DummyNode.
     */
    private int id;

    /**
     * Id of the incoming edge's origin node.
     */
    private int from;

    /**
     * Id of the outgoing edge's destination node.
     */
    private int to;

    /**
     * X coordinate of this DummyNode.
     */
    private int x;

    /**
     * Y coordinate of this DummyNode.
     */
    private int y;

    /**
     * Constructor for a DummyNode object.
     * @param id The id of the DummyNode.
     * @param from The id of the incoming edge's origin.
     * @param to The id of the outgoing edge's destination.
     */
    public DummyNode(int id, int from, int to, int x, int y) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the id of this DummyNode.
     * @return The id of this DummyNode.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the id of the incoming edge's origin node.
     * @return The id of the incoming edge's origin node.
     */
    public int getFrom() {
        return from;
    }

    /**
     * Sets the id of the incoming edge's origin node.
     * @param newFrom New if of the incoming edge's origin node.
     */
    public void setFrom(int newFrom) {
        this.from = newFrom;
    }

    /**
     * Gets the id of the outgoing edge's destination node.
     * @return The id of the outgoing edge's destination node.
     */
    public int getTo() {
        return to;
    }

    /**
     * Sets the id of the outgoing edge's destination node.
     * @param newTo New id of the outgoing edge's destination node.
     */
    public void setTo(int newTo) {
        this.to = newTo;
    }

    /**
     * Gets the X coordinate of this DummyNode.
     * @return The X coordinate of this DummyNode.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the X coordinate of this DummyNode.
     * @param newX The new X coordinate.
     */
    public void setX(int newX) {
        this.x = newX;
    }

    /**
     * Gets the Y coordinate of this DummyNode.
     * @return The Y coordinate of this DummyNode.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the Y coordinate of this DummyNode.
     * @param newY The new Y coordinate.
     */
    public void setY(int newY) {
        this.y = newY;
    }

    /**
     *
     * @param other
     * @return
     */
    public boolean nextInEdge(DummyNode other) {
        return this.from == other.from && this.to == other.to && this.id + 1 == other.id;
    }

    /**
     *
     * @return
     */
    public DummyNode prevInEdge() {
        return new DummyNode(this.id - 1, this.from, this.to, this.x - 100, this.y);
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + from;
        result = 31 * result + to;
        return result;
    }

    /**
     * Equals method.
     * @param other Object to compare to
     * @return Whether the object is equal to this DummyNode.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof DummyNode) {
            DummyNode that = (DummyNode) other;
            return this.id == that.id && this.from == that.from && this.to == that.to;
        }

        return false;
    }
}

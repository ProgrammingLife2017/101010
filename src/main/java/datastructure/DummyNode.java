package datastructure;

/**
 * Created by 101010.
 */
public class DummyNode extends DrawNode {
    /**
     * Id of the incoming edge's origin node.
     */
    private int from;

    /**
     * Id of the outgoing edge's destination node.
     */
    private int to;

    /**
     * Constructor for a DummyNode object.
     * @param id The id of the DummyNode.
     * @param from The id of the incoming edge's origin.
     * @param to The id of the outgoing edge's destination.
     */
    public DummyNode(int id, int from, int to) {
        super(id);
        this.from = from;
        this.to = to;
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
}

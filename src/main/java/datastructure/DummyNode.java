package datastructure;

/**
 * Created by 101010.
 */
public class DummyNode implements Comparable<DummyNode> {
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
        return new DummyNode(this.id-1, this.from, this.to, this.x - 100, this.y);
    }

    /**
     *
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof DummyNode) {
            DummyNode that = (DummyNode) other;
            return this.id == that.id && this.from == that.from && this.to == that.to;
        }

        return false;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(DummyNode o) {
        return Integer.compare(this.id, o.id);
    }
}

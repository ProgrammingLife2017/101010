package Parsing;

/**
 * Created by 101010.
 */
public class Link {
    int first;
    boolean rCfirst;
    int second;
    boolean rCsecond;
    int offset;

    /**
     * Constructs an empty Link.
     */
    public Link() {
        this.first = 0;
        this.rCfirst = false;
        this.second = 0;
        this.rCsecond = false;
        this.offset = 0;
    }

    /**
     * Constructs a Link between two nodes.
     * @param first The index of the first Node.
     * @param rCfirst Whether the first Node should be reversed and complemented.
     * @param second The index of the second Node.
     * @param rCsecond Whether the second Node should be reversed and complemented.
     * @param offset The offset between the two nodes.
     */
    public Link(int first, boolean rCfirst, int second, boolean rCsecond, int offset) {
        this.first = first;
        this.rCfirst = rCfirst;
        this.second = second;
        this.rCsecond = rCsecond;
        this.offset = offset;
    }

    /**
     * Getter for the first Node.
     * @return The index of the first Node.
     */
    public int getFirst() {
        return first;
    }

    /**
     * Setter for the first Node.
     * @param first The index of the first Node.
     */
    public void setFirst(int first) {
        this.first = first;
    }

    /**
     * Getter for the reversed complement of the first Node.
     * @return The reversed complement of first Node.
     */
    public boolean getrCfirst() {
        return rCfirst;
    }

    /**
     * Setter for the revesed complement of the first Node.
     * @param rCfirst the reversed complement of first Node.
     */
    public void setrCfirst(boolean rCfirst) {
        this.rCfirst = rCfirst;
    }

    /**
     * Getter for the second Node.
     * @return The index of the second Node.
     */
    public int getSecond() {
        return second;
    }

    /**
     * Setter for the second Node.
     * @param second The index of the second Node.
     */
    public void setSecond(int second) {
        this.second = second;
    }

    /**
     * Getter for the reversed complement of the second Node.
     * @return The reversed complement of second Node.
     */
    public boolean getrCsecond() {
        return rCsecond;
    }

    /**
     * Setter for the revesed complement of the second Node.
     * @param rCsecond the reversed complement of second Node.
     */
    public void setrCsecond(boolean rCsecond) {
        this.rCsecond = rCsecond;
    }

    /**
     * Getter for the offset between the nodes.
     * @return The offset between the nodes.
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Setter for the offset between the nodes.
     * @return The offset between the nodes.
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * Checks whether this Link and the given Object are the same Link.
     * @param other The given Object to compare.
     * @return True iff given Object is a Link and as the same attributes.
     */
    public boolean equals(Object other) {
        if (other instanceof Link) {
            Link that = (Link) other;
            if (this.getFirst() == that.getFirst() &&
                    this.getrCfirst() == that.getrCfirst() &&
                    this.getSecond() == that.getSecond() &&
                    this.getrCsecond() == that.getrCsecond() &&
                    this.getOffset() == that.getOffset()) {
                return true;
            }
        }
        return false;
    }
}

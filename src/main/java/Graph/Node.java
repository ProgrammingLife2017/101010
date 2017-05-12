package Graph;

/**
 * Created by 101010.
 */
public class Node {
    String segment;
    int id;

    /**
     * Constructs an empty Node.
     */
    public Node() {
        this.segment = "";
        this.id = 0;
    }

    /**
     * Constructs a Node.
     * @param segment The sequence of bases.
     * @param id The unique id of the Node.
     */
    public Node(String segment, int id) {
        this.segment = segment;
        this.id = id;
    }

    /**
     * Getter for the sequence of bases.
     * @return The sequence of bases.
     */
    public String getSegment() {
        return segment;
    }

    /**
     * Setter for the sequence of bases.
     * @param segment The sequence of bases.
     */
    public void setSegment(String segment) {
        this.segment = segment;
    }

    /**
     * Getter for the id.
     * @return The id.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for the id.
     * @param id The id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Checks whether this Node and the given Object are the same Node.
     * @param other The given Object to compare.
     * @return True iff given Object is a Node and has the same attributes.
     */
    public boolean equals(Object other) {
        if (other instanceof Node) {
            Node that = (Node) other;
            if (this.getSegment().equals(that.getSegment()) && this.getId() == that.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the sequence to its reversed complement.
     */
    public void reverseComplement() {
        char[] chars = this.getSegment().toCharArray();
        int length = chars.length;
        String result = "";
        for (int i = 0; i < length; i++) {
            char c = chars[i];
            switch (c) {
                case 'A':
                    c = 'T';
                    break;
                case 'T':
                    c = 'A';
                    break;
                case 'C':
                    c = 'G';
                    break;
                case 'G':
                    c = 'C';
                    break;
                default:
                    c = c;
                    break;
            }
            result = c + result;
        }
        this.setSegment(result);
    }

    public String toString() {
        return segment;
    }
}

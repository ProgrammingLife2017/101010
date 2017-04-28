package Parsing;

/**
 * Created by Jochem on 26-4-2017.
 */
public class Node {
    String segment;

    public Node(String segment) {
        this.segment = segment;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public boolean equals(Object other) {
        if (other instanceof Node) {
            Node that = (Node) other;
            if (this.getSegment().equals(that.getSegment())) {
                return true;
            }
        }
        return false;
    }
}

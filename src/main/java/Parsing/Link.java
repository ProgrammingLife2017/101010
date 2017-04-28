package Parsing;

/**
 * Created by Jochem on 26-4-2017.
 */
public class Link {
    Node first;
    Node second;
    String offset;

    public Link(Node first, Node second, String offset) {
        this.first = first;
        this.second = second;
        this.offset = offset;
    }

    public Node getFirst() {
        return first;
    }

    public void setFirst(Node first) {
        this.first = first;
    }

    public Node getSecond() {
        return second;
    }

    public void setSecond(Node second) {
        this.second = second;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public boolean equals(Object other) {
        if (other instanceof Link) {
            Link that = (Link) other;
            if (this.getFirst().equals(that.getFirst()) &&
                    this.getSecond().equals(that.getSecond()) &&
                    this.getOffset().equals(that.getOffset())) {
                return true;
            }
        }
        return false;
    }
}

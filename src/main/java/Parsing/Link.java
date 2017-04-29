package Parsing;

/**
 * Created by Jochem on 26-4-2017.
 */
public class Link {
    int first;
    int second;
    int offset;

    public Link(int first, int second, int offset) {
        this.first = first;
        this.second = second;
        this.offset = offset;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean equals(Object other) {
        if (other instanceof Link) {
            Link that = (Link) other;
            if (this.getFirst() == that.getFirst() &&
                    this.getSecond() == that.getSecond() &&
                    this.getOffset() == that.getOffset()) {
                return true;
            }
        }
        return false;
    }
}

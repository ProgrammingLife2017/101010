package Parsing;

/**
 * Created by Jochem on 26-4-2017.
 */
public class Link {
    int first;
    boolean rCfirst;
    int second;
    boolean rCsecond;
    int offset;

    public Link() {
        this.first = 0;
        this.rCfirst = false;
        this.second = 0;
        this.rCsecond = false;
        this.offset = 0;
    }

    public Link(int first, boolean rCfirst, int second, boolean rCsecond, int offset) {
        this.first = first;
        this.rCfirst = rCfirst;
        this.second = second;
        this.rCsecond = rCsecond;
        this.offset = offset;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public boolean isrCfirst() {
        return rCfirst;
    }

    public void setrCfirst(boolean rCfirst) {
        this.rCfirst = rCfirst;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public boolean isrCsecond() {
        return rCsecond;
    }

    public void setrCsecond(boolean rCsecond) {
        this.rCsecond = rCsecond;
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

    public String toString() {
        return Integer.toString(offset);
    }
}

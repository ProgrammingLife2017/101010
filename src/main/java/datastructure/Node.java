package datastructure;

/**
 * Created by 101010.
 */
public class Node {
    /**
     *
     */
    private int id;

    /**
     *
     */
    private int length;

    public void Node(int id, int length) {
        this.id = id;
        this.length = length;
    }

    public void setLength(int newLength) {
        this.length = newLength;
    }

    public int getLength() {
        return length;
    }

    public int getId() {
        return id;
    }
}

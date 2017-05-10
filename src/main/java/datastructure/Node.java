package datastructure;

/**
 * Created by 101010.
 */
public class Node{
    int id;
    String segment;
    int[] to;
    int[] from;

    public Node() {
        this.id = 0;
        this.segment = "";
        this.to = new int[0];
        this.from = new int[0];
    }

    public Node(int id, String segment, int[] outgoingEdges, int[] incomingEdges) {
        this.id = id;
        this.segment = segment;
        this.to = outgoingEdges;
        this.from = incomingEdges;
    }

    public String getSegment() {
        return segment;
    }

    public int getId() {
        return id;
    }

    public int[] getOutgoingEdges() {
        return to;
    }

    public int[] getIncomingEdges() {
        return from;
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof Node) {
            Node that = (Node) other;
            if(this.to.length == that.to.length && this.id == that.id && this.segment.equals(that.segment)) {
                for(int i = 0; i < this.to.length; i++) {
                    if(this.to[i] != that.to[i]) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}

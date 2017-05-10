package Datastructure;

/**
 * Created by 101010.
 */
public class Node{
    int id;
    String segment;
    int[] to;

    public Node() {
        this.id = 0;
        this.segment = "";
        this.to = new int[0];
    }

    public Node(int id, String segment, int numberOfOutgoingEdges) {
        this.id = id;
        this.segment = segment;
        this.to = new int[numberOfOutgoingEdges];
    }

    public String getSegment() {
        return segment;
    }

    public int getId() {
        return id;
    }

    public int[] getDestinationIDs() {
        return to;
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

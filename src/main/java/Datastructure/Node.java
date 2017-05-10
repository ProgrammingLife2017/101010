package Datastructure;

/**
 * Created by 101010 on 5/9/2017.
 */
public class Node{
    String segment;
    int id;
    int coordinate;
    int[] to;

    public Node() {
        this.segment = "";
        this.id = 0;
        this.coordinate = 0;
        this.to = new int[0];
    }

    public Node(String segment, int numberOfOutgoingEdges, int id, int coordinate) {
        this.segment = segment;
        this.id = id;
        this.coordinate = coordinate;
        this.to = new int[numberOfOutgoingEdges];
    }

    public String getSegment() {
        return segment;
    }

    public int getId() {
        return id;
    }

    public int getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(int newCoordinate) {
        this.coordinate = newCoordinate;
    }

    public int[] getDestinationIDs() {
        return to;
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof Node) {
            Node that = (Node) other;
            if(this.to.length == that.to.length && this.id == that.id && this.coordinate == that.coordinate && this.segment.equals(that.segment)) {
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

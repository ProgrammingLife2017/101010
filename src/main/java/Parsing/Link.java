package Parsing;

/**
 * Created by Jochem on 26-4-2017.
 */
public class Link {
    Node first;
    Node second;
    String cigar;

    public Link(Node first, Node second, String cigar) {
        this.first = first;
        this.second = second;
        this.cigar = cigar;
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

    public String getCigar() {
        return cigar;
    }

    public void setCigar(String cigar) {
        this.cigar = cigar;
    }
}

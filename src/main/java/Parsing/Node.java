package Parsing;

/**
 * Created by Jochem on 26-4-2017.
 */
public class Node {
    String segment;
    int id;

    public Node(String segment, int id) {
        this.segment = segment;
        this.id = id;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean equals(Object other) {
        if (other instanceof Node) {
            Node that = (Node) other;
            if (this.getSegment().equals(that.getSegment()) && this.getId() == that.getId()) {
                return true;
            }
        }
        return false;
    }

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
}

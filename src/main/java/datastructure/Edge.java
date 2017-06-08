package datastructure;

/**
 * Created by 101010.
 */
public class Edge {

    /**
     * Id of the node this edge origins from.
     */
    private int parent;

    /**
     * The id of the node this edge goes to.
     */
    private int child;

    /**
     * The weight of the edge.
     */
    private int weight;

    /**
     * Constructor of the Edge.
     * @param parent the parent node id.
     * @param child the child node id
     */
    public Edge(int parent, int child) {
        this.parent = parent;

        this.child = child;
        this.weight = 2;
    }

    /**
     * Getter for the parent.
     * @return the parent node id.
     */
    public int getParent() {
        return parent;
    }

    /**
     * Getter for the child.
     * @return the id of the child node.
     */
    public int getChild() {
        return child;
    }

    /**
     * Getter for the weigh.
     * @return The weight of the edge.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * The setter for the weight.
     * @param weight the weight to be set.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Edge) {
            Edge that = (Edge) other;
            return (this.parent == that.parent && this.child == that.child);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = parent;
        result = 31 * result + child;
        return result;
    }
}

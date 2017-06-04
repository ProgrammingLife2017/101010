package datastructure;

/**
 * Object used to represent all dummy nodes in a single edge.
 */
public class DummyEdge {
    /**
     * The id of the parent node.
     */
    private int parent;

    /**
     * The id of the child node.
     */
    private int child;

    /**
     * Two dimensional array containing the x and y coordinates of each dummy node in this dummy edge.
     */
    private int[][] nodes;

    /**
     * Constructor for DummyEdges.
     * @param parent The parent id.
     * @param child The child id.
     */
    public DummyEdge(int parent, int child) {
        this.parent = parent;
        this.child = child;
        this.nodes = new int[0][2];
    }

    /**
     * Retrieves the id of the parent of this DummyEdge.
     * @return The id of the parent of this DummyEdge.
     */
    public int getParent() {
        return parent;
    }

    /**
     * Retrieves the id of the child of this DummyEdge.
     * @return The id of the child of this DummyEdge.
     */
    public int getChild() {
        return child;
    }

    /**
     * Adds a new Dummy Node at the start of the edge.
     * @param x The x coordinate for the new Dummy Node.
     * @param y The y coordinate for the new Dummy Node.
     */
    public void addFirst(int x, int y) {
        int[][] newNodes = new int[nodes.length + 1][];
        newNodes[0] = new int[] {x, y};
        for (int i = 0; i < nodes.length; i++) {
            newNodes[i + 1] = nodes[i];
        }
        nodes = newNodes;
    }

    /**
     * Adds a new Dummy Node at the start of the edge,
     * where the X coordinate is the layer before
     * the previous first Dummy Node, and
     * the Y coordinate is equal to that of
     * the previous first Dummy Node.
     */
    public void addFirst() {
        try {
            addFirst(getFirstX() - 100, getFirstY());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Can not auto-prepend a Dummy Node to an empty DummyEdge,\n"
                    + "because at least one Dummy Node is required to determine the "
                    + "X and Y coordinates of the new Dummy Node");
        }
    }

    /**
     * Adds a new Dummy Node at the end of the edge.
     * @param x The x coordinate for the new Dummy Node.
     * @param y The y coordinate for the new Dummy Node.
     */
    public void addLast(int x, int y) {
        int[][] newNodes = new int[nodes.length + 1][];
        newNodes[nodes.length] = new int[] {x, y};
        for (int i = 0; i < nodes.length; i++) {
            newNodes[i] = nodes[i];
        }
        nodes = newNodes;
    }

    /**
     * Adds a new Dummy Node at the end of the edge,
     * where the X coordinate is the layer after
     * the previous last Dummy Node, and
     * the Y coordinate is equal to that of
     * the previous last Dummy Node.
     */
    public void addLast() {
        try {
            addLast(getLastX() + 100, getLastY());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Can not auto-append a Dummy Node to an empty DummyEdge,\n"
                    + "because at least one Dummy Node is required to determine the "
                    + "X and Y coordinates of the new Dummy Node");
        }
    }

    /**
     * Get the X coordinate of a Dummy Node.
     * @param index The index of the Dummy Node.
     * @return The X coordinate of the Dummy Node.
     */
    public int getX(int index) {
        return nodes[index][0];
    }

    /**
     * Get the Y coordinate of a Dummy Node.
     * @param index The index of the Dummy Node.
     * @return The Y coordinate of the Dummy Node.
     */
    public int getY(int index) {
        return nodes[index][1];
    }

    /**
     * Sets the X coordinate of a Dummy Node.
     * @param index The index of the Dummy Node.
     * @param newX The new X coordinate of the Dummy Node.
     */
    public void setX(int index, int newX) {
        nodes[index][0] = newX;
    }

    /**
     * Sets the Y coordinate of a Dummy Node.
     * @param index The index of the Dummy Node.
     * @param newY The new Y coordinate of the Dummy Node.
     */
    public void setY(int index, int newY) {
        nodes[index][1] = newY;
    }

    /**
     * Gets the X coordinate of the first Dummy Node.
     * @return The X coordinate of the first Dummy Node.
     */
    public int getFirstX() {
        return nodes[0][0];
    }

    /**
     * Gets the Y coordinate of the first Dummy Node.
     * @return The Y coordinate of the first Dummy Node.
     */
    public int getFirstY() {
        return nodes[0][1];
    }

    /**
     * Gets the X coordinate of the last Dummy Node.
     * @return The X coordinate of the last Dummy Node.
     */
    public int getLastX() {
        return nodes[nodes.length - 1][0];
    }

    /**
     * Gets the Y coordinate of the last Dummy Node.
     * @return The Y coordinate of the last Dummy Node.
     */
    public int getLastY() {
        return nodes[nodes.length - 1][1];
    }

    /**
     * Gets the amount of Dummy Nodes in this DummyEdge.
     * @return The amount of Dummy Nodes in this DummyEdge.
     */
    public int getLength() {
       return nodes.length;
    }

    /**
     * Checks whether this DummyEdge contains any Dummy Nodes.
     * @return True iff this DummyEdge contains at least one Dummy Node.
     */
    public boolean isEmpty() {
        return nodes.length == 0;
    }

    /**
     * Checks whether this DummyEdge traverses through a specific layer.
     * @param x The X coordinate of the layer.
     * @return True iff this DummyEdge traverses the layer with X coordinate x.
     */
    public boolean traversesLayer(int x) {
        return x >= getFirstX() && x <= getLastX();
    }

    /**
     * Returns the index of the node in the layer with coordinate x.
     * @param x The X coordinate of the layer.
     * @return The index of the node in the specified layer,
     * -1 if there is no node in this layer.
     */
    public int indexOfLayer(int x) {
        int res = -1;
        if (!isEmpty() && traversesLayer(x)) {
            res = (x - getFirstX()) / 100;
        }
        return res;
    }

    /**
     * Sets the y coordinate of a Dummy Node.
     * @param x The X coordinate of the layer the Dummy Node is in.
     * @param newY The new Y coordinate of the Dummy Node.
     */
    public void setYOfLayer(int x, int newY) {
        int i = indexOfLayer(x);
        try {
            nodes[i][1] = newY;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Tried to access a non existent Dummy Node in method setYOfLayer\n"
                    + "with args x: " + x + ", newY: " + newY + ", at index: " + i + ".");
            if (i == -1) {
                System.out.println("This index indicates that this DummyEdge does not have a Dummy Node in this layer.");
            }
        }
    }

    /**
     * Retrieves the y coordinate of the Dummy Node in the layer with X coordinate x.
     * @param x The X coordinate of the layer.
     * @return The Y coordinate of the Dummy Node in the layer with X coordinate x,
     * iff such a node exists, returns -1 otherwise.
     */
    public int getYOfLayer(int x) {
        int i = indexOfLayer(x);
        try {
            return nodes[i][1];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Tried to access a non existent Dummy Node in method getYOfLayer\n"
                    + "with args x: " + x + ", at index: " + i + ".");
            if (i == -1) {
                System.out.println("This index indicates that this DummyEdge does not have a Dummy Node in this layer.");
            }
        }
        return -1;
    }

    /**
     * Removes the first Dummy Node of this DummyEdge.
     */
    public void removeFirst() {
        int[][] newNodes = new int[Math.max(nodes.length - 1, 0)][];
        for (int i = 0; i < newNodes.length; i++) {
            newNodes[i] = nodes[i + 1];
        }
        nodes = newNodes;
    }

    /**
     * Removes the last Dummy Node of this DummyEdge.
     */
    public void removeLast() {
       int[][] newNodes = new int[Math.max(nodes.length - 1, 0)][];
       for (int i = 0; i < newNodes.length; i++) {
           newNodes[i] = nodes[i];
       }
       nodes = newNodes;
    }

    @Override
    public boolean equals(Object other) {
       if (other instanceof DummyEdge) {
           DummyEdge that = (DummyEdge) other;
           return this.parent == that.parent && this.child == that.child;
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

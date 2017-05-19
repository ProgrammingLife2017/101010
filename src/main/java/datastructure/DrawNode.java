package datastructure;

import javafx.scene.shape.Rectangle;

/**
 * Created by Martijn on 18-5-2017.
 */
public class DrawNode extends Rectangle {

    /**
     * The id of the node corresponding to the node in the GFA file.
     */
    private int id;

    /**
     * Constructor for the draw node.
     * @param index the id of the node.
     */
    public DrawNode(int index) {
        this.id = index;
    }

    /**
     * Getter for the id of the draw node.
     * @return the id of the draw node.
     */
    public int getIndex() {
        return this.id;
    }


}

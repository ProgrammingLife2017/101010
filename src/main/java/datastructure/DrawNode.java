package datastructure;

import javafx.scene.shape.Rectangle;

/**
 * Created by Martijn on 18-5-2017.
 */
public class DrawNode extends Rectangle {

    private int id;

    public DrawNode(int index) {
        this.id = index;
    }

    public int getIndex() {
        return this.id;
    }


}

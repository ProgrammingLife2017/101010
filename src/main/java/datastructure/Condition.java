package datastructure;


import javafx.scene.paint.Color;

/**
 * Created by 101010.
 */
public abstract class Condition {
    /**
     * The color to be painted when condition holds.
     */
    private Color color;

    /**
     * Default constructor.
     */
    public Condition(){}

    /**
     *Constructor of the condition
     * @param color the color of this condition.
     */
    public Condition(Color color) {
        this.color = color;
    }
    /**
     * Derives if condition holds.
     * @param drawNode the node to derive condition on.
     * @return true iff the condition holds.
     */
    public abstract boolean addColor(DrawNode drawNode);


}

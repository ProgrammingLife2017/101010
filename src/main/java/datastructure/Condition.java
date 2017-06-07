package datastructure;


import javafx.scene.paint.Color;

/**
 * Created by 101010.
 */
public abstract class Condition {
    /**
     * The color to be painted when condition holds.
     */
    private final Color color;

    /**
     * Default constructor.
     */
    public Condition(){
        color = Color.BLACK;
    }

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

    /**
     * Gets the color of the condition.
     * @return the color of the condition.
     */
    public Color getColor() {
        return color;
    }


}

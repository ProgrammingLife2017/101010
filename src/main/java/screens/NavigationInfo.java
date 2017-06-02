package screens;

/**
 * Class that can store information about the current state on the drawn graph used for navigation.
 */
public final class NavigationInfo {

    /**
     * The radius of the currently drawn graph.
     */
    private int currentRadius;

    /**
     * The center node id of the currently drawn graph.
     */
    private int currentCenterNode;

    private double xOffset;

    private double yOffset;

    /**
     * A singleton instance of NavigationInfo.
     */
    private static NavigationInfo instance;

    /**
     * Constructor for the information.
     */
    private NavigationInfo() {
        this.currentCenterNode = 0;
        this.currentRadius = 200;
        this.xOffset = 0;
        this.yOffset = 0;
    }

    /**
     * Getter for the instance of the NavigationInfo.
     * @return the currrent instance of the NavigationInfo.
     */
    public static NavigationInfo getInstance() {
        if (instance == null) {
            instance = new NavigationInfo();
        }
        return instance;
    }

    /**
     * Getter for the current radius.
     * @return the current radius.
     */
    public int getCurrentRadius() {
        return this.currentRadius;
    }

    /**
     * Getter for the current center node id.
     * @return the id of the current center node.
     */
    public int getCurrentCenterNode() {
        return this.currentCenterNode;
    }

    /**
     * Setter for the current radius of the graph.
     * @param radius the new radius of the graph.
     */
    public void setCurrentRadius(int radius) {
        this.currentRadius = radius;
    }

    /**
     * Setter for the center node id of the current graph.
     * @param id the new id of the center node of the graph.
     */
    public void setCurrentCenterNode(int id) {
        this.currentCenterNode = id;
    }
}

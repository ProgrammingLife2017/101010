package screens;

/**
 * Created by Martijn on 26-5-2017.
 */
public class NavigationInfo {

    private int currentRadius;

    private int currentCenterNode;

    private static NavigationInfo instance;

    private NavigationInfo() {
        this.currentCenterNode = 0;
        this.currentRadius = 200;
    }

    public static NavigationInfo getInstance() {
        if (instance == null) {
            instance = new NavigationInfo();
        }
        return instance;
    }

    public int getCurrentRadius() {
        return this.currentRadius;
    }

    public int getCurrentCenterNode() {
        return this.currentCenterNode;
    }

    public void setCurrentRadius(int radius) {
        this.currentRadius = radius;
    }

    public void setCurrentCenterNode(int id) {
        this.currentCenterNode = id;
    }
}

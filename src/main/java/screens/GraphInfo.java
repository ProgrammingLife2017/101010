package screens;

import datastructure.Condition;
import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 * Class that can store information about the current state on the drawn graph used for navigation.
 */
public class GraphInfo {

    /**
     * The radius of the currently drawn graph.
     */
    private int currentRadius;

    /**
     * The center node id of the currently drawn graph.
     */
    private int currentCenterNode;

    /**
     * The number of genomes paths that are specified.
     */
    private int genomeNum;

    /**
     * All specified genome paths per node.
     */
    private int[][] genomes;

    /**
     * A singleton instance of GraphInfo.
     */
    private static GraphInfo instance;

    /**
     * List of colors that can still be assigned to new conditions.
     */
    private ArrayList<Color> colors;

    /**
     * List of all conditions that are currently checked for.
     */
    private ArrayList<Condition> conditions;

    /**
     * List of all genome names specified in the file.
     */
    private String[] genomeNames;

    /**
     * Constructor for the information.
     */
    public GraphInfo() {
        this.currentCenterNode = 0;
        this.currentRadius = 200;
        colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);
        colors.add(Color.CYAN);
        conditions = new ArrayList<>();
    }

    /**
     * Getter for the instance of the GraphInfo.
     * @return the currrent instance of the GraphInfo.
     */
    public static GraphInfo getInstance() {
        if (instance == null) {
            instance = new GraphInfo();
        }
        return instance;
    }

    /**
     * Setter for the instance of the GraphInfo.
     * @param gi the new GraphInfo.
     */
    public static void setInstance(GraphInfo gi) {
        instance = gi;
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

    /**
     * Setter for the number of genome paths specified in the file.
     * @param num the number of genome paths specified in the file.
     */
    public void setGenomesNum(int num) {
        this.genomeNum = num;
    }

    /**
     * Getter for the number of genome paths specified in the file.
     * @return the number of genome paths specified in the file.
     */
    public int getGenomesNum() {
        return this.genomeNum;
    }

    /**
     * Setter for the genome paths of the file.
     * @param newGenomes the genome paths to set.
     */
    public void setGenomes(int[][] newGenomes) {
        this.genomes = newGenomes;
    }

    /**
     * Getter for the genome paths of the file.
     * @return the genome paths of the file per node.
     */
    public int[][] getGenomes() {
        return this.genomes;
    }

    /**
     * Determines the color of the next condition.
     * @return the color the next condition should get.
     */
    public Color determineColor() {
        if (colors.size() != 0) {
            return colors.remove(0);
        }
        return Color.GRAY;
    }

    /**
     * Add a color to the list of colors to assign to conditions.
     * @param color the color that will get added to the list.
     */
    public void addColor(Color color) {
        colors.add(color);
    }

    /**
     * Add a new condition to the list of conditions.
     * @param cond the condition that will get added.
     */
    public void addCondition(Condition cond) {
        this.conditions.add(cond);
    }

    /**
     * Getter for the list of condition that are checked for.
     * @return a list of all current conditionals.
     */
    public ArrayList<Condition> getConditions() {
        return this.conditions;
    }

    /**
     * Setter for the list of all genome names in the current file.
     * @param names all names of the genomes in the file.
     */
    public void setGenomeNames(String[] names) {
        this.genomeNames = names;
    }

    /**
     * Getter for the genome names.
     * @return the genome names.
     */
    public String[] getGenomeNames() {
        return this.genomeNames;
    }
}

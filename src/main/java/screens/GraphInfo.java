package screens;

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
    private double genomeNum;

    /**
     * All specified genome paths per node.
     */
    private int[][] genomes;

    /**
     * A singleton instance of GraphInfo.
     */
    private static GraphInfo instance;

    /**
     * Constructor for the information.
     */
    public GraphInfo() {
        this.currentCenterNode = 0;
        this.currentRadius = 200;
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
    public void setGenomesNum(double num) {
        this.genomeNum = num;
    }

    /**
     * Getter for the number of genome paths specified in the file.
     * @return the number of genome paths specified in the file.
     */
    public double getGenomesNum() {
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
    public int[][] getPaths() {
        return this.genomes;
    }
}

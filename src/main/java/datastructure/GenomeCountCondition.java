package datastructure;

/**
 * Created by 101010.
 */
public class GenomeCountCondition extends Condition{

    /**
     * The decision number of the condition.
     */
    private int decisionNumber;

    /**
     * True iff condition is greater than.
     */
    private boolean greater;

    /**
     * True iff condition in equal.
     */
    private boolean equal;


    /**
     * Constructor of the condition
     * @param decisionNumber the number that decides the condition.
     * @param greater true iff condition is greater than the decisionNumber.
     * @param equal true iff condition is equal to the decisionNumber.
     */
    public GenomeCountCondition(int decisionNumber, boolean greater, boolean equal) {
        this.decisionNumber = decisionNumber;
        this.greater = greater;
        this.equal = equal;
    }

    @Override
    public boolean addColor(DrawNode drawNode) {
        int genomes = drawNode.getGenomes().length;
        if (greater) {
            if (equal) {
                return decisionNumber >= genomes;
            } else {
                return decisionNumber > genomes;
            }
        } else {
            if (equal) {
                return decisionNumber <= genomes;
            } else {
                return decisionNumber < genomes;
            }
        }

    }
}

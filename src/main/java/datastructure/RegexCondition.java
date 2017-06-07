package datastructure;

/**
 * Created by 101010.
 */
public class RegexCondition extends Condition {

    /**
     * the regex of the condition.
     */
    private String regex;

    /**
     * The constructor of the RegexCondition.
     * @param regex the regex of the condition.
     */
    public RegexCondition(String regex) {
        this.regex = regex;
    }

    @Override
    public boolean addColor(DrawNode drawNode) {
        String[] genomes = drawNode.getGenomes();
        for (int i = 0; i < genomes.length; i++) {
            if (genomes[i].contains(regex)) {
                return true;
            }
        }
        return false;
    }
}

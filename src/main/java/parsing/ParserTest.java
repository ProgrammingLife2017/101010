package parsing;

import datastructure.NodeGraph;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by 101010.
 */
public class ParserTest {
    @org.junit.Test
    public void getInstance() {
        Object parser = Parser.getInstance();
        assertTrue(parser instanceof Parser);
    }

    @org.junit.Test
    public void parse() {
        Parser parser = Parser.getInstance();
        NodeGraph data = parser.parse("/Test2.gfa");
    }

}

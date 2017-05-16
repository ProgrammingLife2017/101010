package parsing;

import datastructure.NodeGraph;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by 101010.
 */
class ParserTest {
    @Test
    void getInstance() {
        Object parser = Parser.getInstance();
        assertTrue(parser instanceof Parser);
    }

    @Test
    void parse() {
        Parser parser = Parser.getInstance();
        NodeGraph data = parser.parse("/TB10.gfa");
    }

}

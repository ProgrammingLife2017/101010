package parsing;

import datastructure.Node;
import datastructure.NodeGraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

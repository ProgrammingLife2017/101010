package parsing;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Created by 101010.
 */
class ParserTest {
    /**
     * Check that the parser returns a parser when an instance is requested.
     */
    @Test
    void getInstance() {
        Object parser = Parser.getInstance();
        assertTrue(parser instanceof Parser);
    }


    /**
     * This checks nothing.
     */
//    @Test
//    void parse() {
//        Parser parser = Parser.getInstance();
//        NodeGraph data = parser.parse("/TB10.gfa");
//    }

}

package parsing;

import datastructure.Node;
import datastructure.NodeDB;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

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
        NodeDB data = parser.parse("/test2.gfa");
        assertEquals("G",  data.getSegment(7));
        Node node = data.getNode(5);
        assertEquals(3, node.getIncomingEdges()[0]);
        assertEquals(4, node.getIncomingEdges()[1]);
        assertEquals(6, node.getOutgoingEdges()[0]);
        assertEquals(7, node.getOutgoingEdges()[1]);
    }

}

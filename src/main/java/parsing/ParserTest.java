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
        NodeGraph data = parser.parse("/test2.gfa");
        Node node = data.getNode(3);
        assertEquals(4, node.getOutgoingEdges()[0]);

        Node node2 = data.getNode(7);
        int[] tempArray1 = node2.getIncomingEdges();
        assertEquals(5, tempArray1[0]);
        assertEquals(data.getSegment(7).length(), node2.getLength());
        assertTrue(node2.getLength() != 0);


    }

}

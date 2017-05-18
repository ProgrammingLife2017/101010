package parsing;

import datastructure.Node;
import datastructure.NodeGraph;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        String workingDirectory = System.getProperty("user.dir");

        String absoluteFilePath = workingDirectory + File.separator;
        NodeGraph data = parser.parse(new File(absoluteFilePath + "/test/test2.gfa"));
        Node node = data.getNode(3);
        assertEquals(4, node.getOutgoingEdges()[0]);

        Node node2 = data.getNode(7);
        int[] tempArray1 = node2.getIncomingEdges();
        assertEquals(5, tempArray1[0]);
        assertEquals(data.getSegment(7).length(), node2.getLength());
        assertTrue(node2.getLength() != 0);

    }

}

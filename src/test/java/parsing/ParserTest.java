package parsing;

import datastructure.Node;
import datastructure.NodeGraph;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by 101010.
 */
public class ParserTest {
    @Test
    public void getInstance() {
        Object parser = Parser.getInstance();
        assertTrue(parser instanceof Parser);
    }

    @Test
    public void parse() {
        Parser parser = Parser.getInstance();
        String workingDirectory = System.getProperty("user.dir");

        String absoluteFilePath = workingDirectory + File.separator;
        NodeGraph data = parser.parse(new File(absoluteFilePath + "/src/main/resources/test2.gfa"));
        Node node2 = data.getNode(7);
        assertEquals(data.getSegment(7).length(), node2.getLength());
        assertTrue(node2.getLength() != 0);

    }

}

package parsing;

import datastructure.Node;
import datastructure.NodeGraph;
import java.io.*;
import org.junit.After;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by 101010.
 */
public class ParserTest {

    @After
    public void tearDown() {
        String workingDirectory = System.getProperty("user.dir");
        String absoluteFilePath = workingDirectory + File.separator;
        File cache = new File(absoluteFilePath + "/src/main/resources/test2.txt");
        File segments = new File(absoluteFilePath + "/src/main/resources/test2Segments.txt");
        if (cache.exists()) {
            cache.delete();
        }
        if (segments.exists()) {
            segments.delete();
        }
    }


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


        NodeGraph data2 = parser.parse(new File(absoluteFilePath + "/src/main/resources/test2.gfa"));
        Node node3 = data2.getNode(7);
        assertEquals(data2.getSegment(7).length(), node3.getLength());
        assertTrue(node3.getLength() != 0);
    }

    @Test
    public void createCache() {
        Parser parser = Parser.getInstance();
        String workingDirectory = System.getProperty("user.dir");

        String absoluteFilePath = workingDirectory + File.separator;
        NodeGraph data = parser.parse(new File(absoluteFilePath + "/src/main/resources/test2.gfa"));
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(absoluteFilePath + "/src/main/resources/test2.txt"))));
            assertEquals(data.getSize(), Integer.parseInt(br.readLine()));
            int[] in;
            int[] out;
            for(int i = 0; i < data.getSize(); i++) {
                assertEquals(data.getNode(i).getLength(), Integer.parseInt(br.readLine()));
                Integer.parseInt(br.readLine());
                Integer.parseInt(br.readLine());
                assertEquals(data.getNode(i).getOutgoingEdges().length, Integer.parseInt(br.readLine()));
                out = data.getNode(i).getOutgoingEdges();
                for (int j : out) {
                    assertEquals(j, Integer.parseInt(br.readLine()));
                }
            }
            File cache = new File(absoluteFilePath + "/src/main/resources/test2.txt");
            File segments = new File(absoluteFilePath + "/src/main/resources/test2Segments.txt");
            segments.delete();
            br.close();
            if (cache.exists()) {
                cache.delete();
            }
        } catch(Exception e) {
            fail();
        }
    }

    @Test
    public void parseCache() {
        Parser parser = Parser.getInstance();
        String workingDirectory = System.getProperty("user.dir");
        String absoluteFilePath = workingDirectory + File.separator;
        File file = new File(absoluteFilePath + "/src/test/resources/testCache.txt");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write("4\n" +
                    "5\n" +
                    "0\n" +
                    "0\n" +
                    "3\n" +
                    "1\n" +
                    "2\n" +
                    "3\n" +
                    "6\n" +
                    "0\n" +
                    "0\n" +
                    "1\n" +
                    "3\n" +
                    "7\n" +
                    "0\n" +
                    "0\n" +
                    "0\n" +
                    "8\n" +
                    "0\n" +
                    "0\n" +
                    "0");
            bw.close();
            NodeGraph graph = new NodeGraph();
            Node node1 = new Node();
            Node node2 = new Node();
            Node node3 = new Node();
            Node node4 = new Node();
            node1.setLength(5);
            node2.setLength(6);
            node3.setLength(7);
            node4.setLength(8);
            node1.addOutgoingEdge(1);
            node1.addOutgoingEdge(2);
            node1.addOutgoingEdge(3);
            node2.addOutgoingEdge(3);
            node2.addIncomingEdge(0);
            node3.addIncomingEdge(0);
            node4.addIncomingEdge(0);
            node4.addIncomingEdge(1);
            graph.addNode(0, node1);
            graph.addNode(1, node2);
            graph.addNode(2, node3);
            graph.addNode(3, node4);
            NodeGraph testGraph = new NodeGraph();
            parser.parseCache(testGraph, file);
            file.delete();
            assertEquals(graph.getSize(), testGraph.getSize());
            Node testNode1;
            Node testNode2;
            int[] out1;
            int[] out2;
            for (int i = 0; i < graph.getSize(); i++) {
                testNode1 = graph.getNode(i);
                testNode2 = testGraph.getNode(i);
                out1 = testNode1.getOutgoingEdges();
                out2= testNode2.getOutgoingEdges();
                assertEquals(testNode1.getLength(), testNode2.getLength());
                for (int j = 0; j < out1.length; j++) {
                    assertEquals(out1[j], out2[j]);
                }
            }
        } catch(Exception e) {
            fail();
        }
    }

}

package parsing;

import datastructure.Node;
import datastructure.NodeGraph;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.File;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.FileOutputStream;


/**
 * Created by 101010.
 */
public class Parser {
    /**
     * Initial Parser.
     */
    private static Parser instance = null;

    /**
     * Constructor of the parser.
     */
    protected Parser() { }

    /**
     * Getter for the Singleton parser.
     * @return The singleton parser.
     */
    public static Parser getInstance() {
        if (instance == null) {
            instance = new Parser();
        }
        return instance;
    }

    /**
     * Parses the data of the inputted file.
     * @param filename The name of the file.
     * @return The graph created from the .gfa file.
     */
    public NodeGraph parse(final String filename) {
        NodeGraph graph = new NodeGraph();
        return parse(filename, graph);
    }

    /**
     * Parses a .gfa file to a graph.
     * @param filename The name of the target .gfa file.
     * @param graph The graph the data gets put into.
     * @return The graph created from the .gfa file.
     */
    public NodeGraph parse(final String filename, NodeGraph graph) {

        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(getClass().getResourceAsStream(filename)));
            String line = in.readLine();
            boolean newCache = true;
            line = line.substring(line.indexOf("\t") + 1);
            line = line.replaceAll(":", "");
            String workingDirectory = System.getProperty("user.dir");

            String absoluteFilePath;

            absoluteFilePath = workingDirectory + File.separator + line;
            String cacheName = line;
            File file = new File(absoluteFilePath + ".txt");
            if (file.exists()) {
                newCache = false;
                BufferedReader in2 = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                line = in2.readLine();
                int graphSize = Integer.parseInt(line);
                for(int i = 0; i < graphSize; i++) {
                    line = in2.readLine();
                    int inLength = Integer.parseInt(line);
                    int[] ingoing = new int[inLength];
                    for (int j = 0; j < inLength; j++) {
                        line = in2.readLine();
                        ingoing[j] = Integer.parseInt(line);
                    }
                    line = in2.readLine();
                    int outLength = Integer.parseInt(line);
                    int[] outgoing = new int[outLength];
                    for (int j = 0; j < outLength; j++) {
                        line = in2.readLine();
                        outgoing[j] = Integer.parseInt(line);
                    }
                    Node temp = new Node(0, outgoing, ingoing);
                    graph.addNode(i, temp);
                }
            }

            while (line != null) {
               if (line.startsWith("S")) {
                    int id;
                    String segment;
                    line = line.substring(line.indexOf("\t") + 1);
                    id = Integer.parseInt(line.substring(0, line.indexOf("\t"))) - 1;


                    line = line.substring(line.indexOf("\t") + 1);
                    segment = line.substring(0, line.indexOf("\t"));
                    if (newCache) {
                        graph.addNode(id, new Node(segment.length(), new int[0], new int[0]), segment);
                        line = in.readLine();
                        while (line != null && line.startsWith("L")) {
                            int from;
                            int to;
                            line = line.substring(line.indexOf("\t") + 1);
                            from = Integer.parseInt(line.substring(0, line.indexOf("\t"))) - 1;
                            line = line.substring(line.indexOf("+") + 2);
                            to = Integer.parseInt(line.substring(0, line.indexOf("\t"))) - 1;
                            graph.addEdge(from, to);
                            line = in.readLine();
                        }
                    } else {
                        graph.addSegment(id, segment);
                        graph.getNode(id).setLength(segment.length());
                    }
               } else {
                    line = in.readLine();
                }
            }
            if (newCache) {
                createCache(cacheName, graph);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Wrong file Destination");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error while reading file");
            e.printStackTrace();
        }


        return graph;
    }

    /**
     * Creates cache file.
     * @param filename the name of the file.
     * @param graph the graph to be cached.
     */
    private void createCache(String filename, NodeGraph graph) {
        try{
            String workingDirectory = System.getProperty("user.dir");

            String absoluteFilePath;

            absoluteFilePath = workingDirectory + File.separator + filename;

            File file = new File(absoluteFilePath + ".txt");
            int graphSize = graph.getSize();
            OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(file));
            BufferedWriter writer = new BufferedWriter(ow);
            writer.write("" + graphSize + "");
            writer.newLine();
            int size;
            for (int i = 0; i < graphSize; i++) {
                Node temp = graph.getNode(i);
                int[] tempList = temp.getIncomingEdges();
                size = tempList.length;
                writer.write("" + size);
                writer.newLine();
                for (int j = 0; j < size; j++) {
                    writer.write("" + tempList[j]);
                    writer.newLine();
                }
                tempList = temp.getOutgoingEdges();
                size = tempList.length;
                writer.write("" + size);
                writer.newLine();
                for (int j = 0; j < size; j++) {
                    writer.write("" + tempList[j]);
                    writer.newLine();
                }

            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

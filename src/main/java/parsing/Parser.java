package parsing;

import datastructure.Node;
import datastructure.NodeGraph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

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

            while (line != null) {

                if (line.startsWith("S")) {
                    int id;
                    String segment;
                    line = line.substring(line.indexOf("\t") + 1);
                    id = Integer.parseInt(line.substring(0, line.indexOf("\t"))) - 1;
                    line = line.substring(line.indexOf("\t") + 1);
                    segment = line.substring(0, line.indexOf("\t"));

                    graph.addNode(id, new Node(segment.length(), new int[0], new int[0]), segment);

                    line = in.readLine();
                    segment = null;

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
                    line = in.readLine();
                }
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
}

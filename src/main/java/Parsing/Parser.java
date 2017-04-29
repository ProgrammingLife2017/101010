package Parsing;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.jgrapht.graph.DefaultDirectedGraph;

/**
 * Created by Jochem on 26-4-2017.
 */
public class Parser {
    private static Parser instance = null;

    protected Parser() {}

    public static Parser getInstance() {
        if (instance == null) {
            instance = new Parser();
        }
        return instance;
    }

    public DefaultDirectedGraph<Node, Link> parse(String filename) {
        DefaultDirectedGraph<Node, Link> graph = new DefaultDirectedGraph<Node, Link>(Link.class);
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));
            String line = in.readLine();
            while (line != null) {
                if (line.startsWith("S")) {
                    Node temp = new Node();
                    line = line.substring(2);
                    temp.setId(Integer.parseInt(line.substring(0, line.indexOf("\t"))));
                    line = line.substring(line.indexOf("\t") + 1);
                    if (line.indexOf("\t") != -1) {
                        line = line.substring(0, line.indexOf("\t"));
                    }
                    temp.setSegment(line);
                    graph.addVertex(temp);
                } else if (line.startsWith("L")) {
                    Link temp = new Link();
                    line = line.substring(2);
                    System.out.println(line);
                }
                line = in.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return graph;
    }
}

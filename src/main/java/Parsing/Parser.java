package Parsing;

import Graph.Node;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * Created by 101010.
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

    /**
     * Parses a .gfa file to a graph.
     * @param filename The name of the target .gfa file.
     * @return The graph created from the .gfa file.
     */
    public DefaultDirectedGraph<Node, Link> parse(String filename) {
        DefaultDirectedGraph<Node, Link> graph = new DefaultDirectedGraph<Node, Link>(Link.class);
        TreeMap<Integer, Node> nodes = new TreeMap<Integer, Node>();
        ArrayList<Link> links = new ArrayList<Link>();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));
            String line = in.readLine();

            while (line != null) {
                System.out.println(line);
                if (line.startsWith("S")) {
                    Node temp = new Node();
                    line = line.substring(2);
                    temp.setId(Integer.parseInt(line.substring(0, line.indexOf("\t"))));
                    line = line.substring(line.indexOf("\t") + 1);
                    if (line.indexOf("\t") != -1) {
                        line = line.substring(0, line.indexOf("\t"));
                    }
                    temp.setSegment(line);
                    nodes.put(temp.getId(), temp);
                } else if (line.startsWith("L")) {
                    Link temp = new Link();
                    line = line.substring(2);
                    temp.setFirst(Integer.parseInt(line.substring(0, line.indexOf("\t"))));
                    line = line.substring(line.indexOf("\t") + 1);
                    if (line.substring(1).equals("-")) {
                        temp.setrCfirst(true);
                    }
                    line = line.substring(line.indexOf("\t") + 1);
                    temp.setSecond(Integer.parseInt(line.substring(0, line.indexOf("\t"))));
                    line = line.substring(line.indexOf("\t") + 1);
                    if (line.substring(1).equals("-")) {
                        temp.setrCsecond(true);
                    }
                    line = line.substring(line.indexOf("\t") + 1);
                    temp.setOffset(Integer.parseInt(line.substring(0, line.indexOf("M"))));
                    links.add(temp);
                }
                line = in.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Iterator<Integer> i1 = nodes.keySet().iterator();
        while (i1.hasNext()) {
            graph.addVertex(nodes.get(i1.next()));
        }

        Iterator<Link> i2 = links.iterator();
        Link link = null;
        while (i2.hasNext()) {
            link = i2.next();
            graph.addEdge(nodes.get(link.getFirst()), nodes.get(link.getSecond()), link);
        }

        return graph;
    }
}

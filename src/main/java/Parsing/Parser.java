package parsing;

import datastructure.NodeDB;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
    protected Parser() {}

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
     * Parses a .gfa file to a graph.
     * @param filename The name of the target .gfa file.
     * @return The graph created from the .gfa file.
     */
    public NodeDB parse(final String filename) {
        NodeDB database = new NodeDB();


        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));
            String line = in.readLine();

            while (line != null) {
//                System.out.println(line);

                if (line.startsWith("S")) {
                    int id;
                    String segment;
                    ArrayList<Integer> edgesList = new ArrayList<>();
                    line = line.substring(2);
                    id = Integer.parseInt(line.substring(0, line.indexOf("\t")));
                    line = line.substring(line.indexOf("\t") + 1);
                    if (line.indexOf("\t") != -1) {
                        line = line.substring(0, line.indexOf("\t"));
                    }
                    segment = line;
                    line = in.readLine();
                    while (line.startsWith("L")) {
                        int edgeId;
                        line = line.substring(2);
                        line = line.substring(line.indexOf("\t") + 1);
                        line = line.substring(line.indexOf("\t") + 1);
                       edgeId = Integer.parseInt(line.substring(0, line.indexOf("\t")));
                       edgesList.add(edgeId);
                    }
                    int[] edges = new int[edgesList.size()];
                    for (int i = 0; i < edgesList.size(); i++){
                        edges[i] = edgesList.get(i);
                    }
                    database.addNode(id, segment, edges);
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

        return database;
    }
}

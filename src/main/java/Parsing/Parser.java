package parsing;

import datastructure.Node;
import datastructure.NodeGraph;
import datastructure.SegmentDB;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import java.io.*;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Queue;

/**
 * Created by 101010.
 */
public final class Parser {
    /**
     * Initial Parser.
     */
    private static Parser instance = null;

    /**
     * Constructor of the parser.
     */
    private Parser() { }

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
     * @param file The name of the file.
     * @return The graph created from the .gfa file.
     */
    public NodeGraph parse(File file) {
        NodeGraph graph = new NodeGraph();

        String cacheName = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 4);
        graph.setSegmentDB(new SegmentDB(cacheName + "Segments.txt"));
        File cache = new File(cacheName + ".txt");
        if (cache.exists()) {
            file = null;
//             try {
//                BufferedReader br = new BufferedReader(new FileReader(cache));
//                if (br.readLine() != null) {
//                    br.close();
//                    br = null;
                    System.out.println("not an empty cache");
                    return parseCache(graph, cache);
//                }
//            } catch(IOException e){
//                System.out.println("Error parsing file");
//                e.printStackTrace();
//            }
        }
        return parse(file, graph);
    }

    /**
     * Parses a .gfa file to a graph.
     * @param file The name of the target .gfa file.
     * @param graph The graph the data gets put into.
     * @return The graph created from the .gfa file.
     */
    public NodeGraph parse(final File file, NodeGraph graph) {

        try {
            BufferedReader in = new BufferedReader(
                    new FileReader(file));
            String line = in.readLine();
            line = line.substring(line.indexOf('\t') + 1);
            line = line.replaceAll(":", "");

            String absoluteFilePath = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 4);

            String sDB = absoluteFilePath + "Segments.txt";
            graph.setSegmentDB(new SegmentDB(sDB));
            File segments = new File(sDB);

            segments.createNewFile();

            BufferedWriter out = new BufferedWriter(new FileWriter(segments));

            while (line != null) {
                if (line.startsWith("S")) {
                    int id;
                    String segment;
                    line = line.substring(line.indexOf('\t') + 1);
                    id = Integer.parseInt(line.substring(0, line.indexOf('\t'))) - 1;
                    line = line.substring(line.indexOf('\t') + 1);
                    segment = line.substring(0, line.indexOf('\t'));
                    graph.addNode(id, new Node(segment.length(), new int[0], new int[0]));
                    out.write(segment + "\n");
                    out.flush();
                    line = in.readLine();
                    while (line != null && line.startsWith("L")) {
                        int from;
                        int to;
                        line = line.substring(line.indexOf('\t') + 1);
                        from = Integer.parseInt(line.substring(0, line.indexOf('\t'))) - 1;
                        line = line.substring(line.indexOf('+') + 2);
                        to = Integer.parseInt(line.substring(0, line.indexOf('\t'))) - 1;
                        graph.addEdge(from, to);
                        line = in.readLine();
                    }
                } else {
                    line = in.readLine();
                }
            }
            in.close();
            out.close();
            for (int i = 0; i < graph.getSize(); i++) {
                graph.getNode(i).setInDegree(graph.getNode(i).getIncomingEdges().length);
            }
            kahnAlgorithm(graph);
            Thread thread = new Thread(graph);
            thread.start();
//            createCache(absoluteFilePath, graph);
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
     * Parser for the cache file.
     * @param graph The NodeGraph the cache is parsed into.
     * @param cache The file containing the cached data.
     * @return A NodeGraph containing the data cached in the file.
     */
    public NodeGraph parseCache(NodeGraph graph, File cache) {
        try {
            FileInputStream inFile = new FileInputStream(cache);
            FSTObjectInput in = new FSTObjectInput(inFile);
            System.out.println("starting deserialization" + System.currentTimeMillis());
            graph = (NodeGraph) in.readObject();
            inFile.close();
            in.close();



//            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(cache)));
//            String line = in.readLine();
//            int graphSize = Integer.parseInt(line);
//            graph.getNodes().ensureCapacity(graphSize);
//            for (int i = 0; i < graphSize; i++) {
//                line = in.readLine();
//                int length = Integer.parseInt(line);
//                line = in.readLine();
//                int x = Integer.parseInt(line);
//                line = in.readLine();
//                int y = Integer.parseInt(line);
//                line = in.readLine();
//                int outLength = Integer.parseInt(line);
//                int[] outgoing = new int[outLength];
//                for (int j = 0; j < outLength; j++) {
//                    line = in.readLine();
//                    outgoing[j] = Integer.parseInt(line);
//                }
//                Node temp = new Node(length, outgoing, new int[0]);
//                temp.setX(x);
//                temp.setY(y);
//                graph.addNodeCache(i, temp);
//            }
//            in.close();
        } catch (IOException e) {
            System.out.println("Error while reading cache");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error finding class");
        } catch (Exception e) {
            System.out.println("Error with FST");
        }
        return graph;
    }


    /**
     * Uses Kahn's Algorithm to determine the coordinates of nodes.
     * @param graph The NodeGraph for which the coordinates of nodes are computed.
     */
    private void kahnAlgorithm(NodeGraph graph) {
        Queue<Node> q = new ArrayDeque<>();
        int x = 503;
        int y;
        int child;
        for (int i = 0; i < graph.getSize(); i++) {
            if (graph.getNode(i).getIncomingEdges().length == 0) {
                q.add(graph.getNode(i));
            }
        }
        while (!q.isEmpty()) {
            Node current = q.poll();
            if (current.getChild() == 0) {
                x += 40;
            }
            y = 291 + 40 * current.getChild();
            current.setX(x);
            current.setY(y);
            child = 0;
            for (int i : current.getOutgoingEdges()) {
                graph.getNode(i).setChild(child);
                graph.getNode(i).setInDegree(graph.getNode(i).getInDegree() - 1);
                if (graph.getNode(i).getInDegree() == 0) {
                    q.add(graph.getNode(i));
                }
                child += 1;
            }
        }
    }

    /**
     * Creates cache file.
     * @param filename the name of the file.
     * @param graph the graph to be cached.
     */
    public void createCache(String filename, NodeGraph graph) {
        try {
            File file = new File(filename + ".txt");
//            int graphSize = graph.getSize();
//            OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
//            BufferedWriter writer = new BufferedWriter(ow);
            FileOutputStream outFile = new FileOutputStream(file);
            FSTObjectOutput out = new FSTObjectOutput(outFile);
            System.out.println("starting serialization" + System.currentTimeMillis());
            out.writeObject(graph);



//            writer.write(Integer.toString(graphSize));
//            writer.newLine();
//            int size;
//            for (int i = 0; i < graphSize; i++) {
//                Node temp = graph.getNode(i);
//                writer.write(Integer.toString(temp.getLength()));
//                writer.newLine();
//                writer.write(Integer.toString(temp.getX()));
//                writer.newLine();
//                writer.write(Integer.toString(temp.getY()));
//                writer.newLine();
//                int[] tempList = temp.getOutgoingEdges();
//                size = tempList.length;
//                writer.write(Integer.toString(size));
//                writer.newLine();
//                for (int j = 0; j < size; j++) {
//                    writer.write(Integer.toString(tempList[j]));
//                    writer.newLine();
//                }
//            }
//            writer.close();
            out.close();
            outFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

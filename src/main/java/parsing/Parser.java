package parsing;

import datastructure.Node;
import datastructure.NodeGraph;
import datastructure.SegmentDB;
import javafx.application.Platform;
import screens.Window;

import java.io.*;

/**
 * Created by 101010.
 */
public final class Parser {
    /**
     * Initial Parser.
     */
    private static Parser instance = null;

    /**
     * Thread the parser is running in.
     */
    private static Thread parser;

    /**
     * Constructor of the parser.
     */
    private Parser() {}

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
            return parseCache(graph, cache);
        }

        return parse(file, graph);
    }

    /**
     * Parses a .gfa file to a graph.
     * @param file  The name of the target .gfa file.
     * @param graph The graph the data gets put into.
     * @return The graph created from the .gfa file.
     */
    public NodeGraph parse(final File file, NodeGraph graph) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line = in.readLine();

            line = line.substring(line.indexOf('\t') + 1);
            String line1 = line.replaceAll(":", "");

            String absoluteFilePath = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 4);

            String sDB = absoluteFilePath + "Segments.txt";
            graph.setSegmentDB(new SegmentDB(sDB));
            File segments = new File(sDB);

            segments.createNewFile();

            BufferedWriter out = new BufferedWriter(new FileWriter(segments));

            parser = new Thread(() -> {
                try {
                    int lineCounter = 1;
                    LineNumberReader lnr = new LineNumberReader(new FileReader(file));
                    lnr.skip(Long.MAX_VALUE);
                    int nol = lnr.getLineNumber() + 1;
                    lnr.close();
                    String line2 = line1;
                    while (line2 != null) {
                        try {
                            if (line2.startsWith("S")) {
                                int id;
                                String segment;
                                line2 = line2.substring(line2.indexOf('\t') + 1);
                                id = Integer.parseInt(line2.substring(0, line2.indexOf('\t'))) - 1;
                                line2 = line2.substring(line2.indexOf('\t') + 1);
                                segment = line2.substring(0, line2.indexOf('\t'));
                                graph.addNode(id, new Node(segment.length(), new int[0], new int[0]));
                                out.write(segment + "\n");
                                out.flush();
                                line2 = in.readLine();
                                lineCounter++;
                                while (line2 != null && line2.startsWith("L")) {
                                    int from;
                                    int to;
                                    line2 = line2.substring(line2.indexOf('\t') + 1);
                                    from = Integer.parseInt(line2.substring(0, line2.indexOf('\t'))) - 1;
                                    line2 = line2.substring(line2.indexOf('+') + 2);
                                    to = Integer.parseInt(line2.substring(0, line2.indexOf('\t'))) - 1;
                                    graph.addEdge(from, to);
                                    line2 = in.readLine();
                                    lineCounter++;
                                }
                            } else {
                                line2 = in.readLine();
                                lineCounter++;
                            }
                            int finalCount = lineCounter;
                            if (finalCount % (nol / 100) == 0) {
                                Platform.runLater(() -> Window.setProgress((double) finalCount / (double) nol));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    in.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            parser.start();

            new Thread(() -> {
                try {
                    parser.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                createCache(absoluteFilePath, graph);
            }).start();
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
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(cache)));
            int graphSize = Integer.parseInt(in.readLine());
            graph.getNodes().ensureCapacity(graphSize);

            parser = new Thread(() -> {
                int lineCounter = 0;
                try {
                    LineNumberReader lnr = new LineNumberReader(new FileReader(cache));
                    lnr.skip(Long.MAX_VALUE);
                    int nol = lnr.getLineNumber() + 1;
                    lnr.close();
                    for (int i = 0; i < graphSize; i++) {
                        int length = Integer.parseInt(in.readLine());
                        int outLength = Integer.parseInt(in.readLine());
                        int[] outgoing = new int[outLength];
                        String[] tempLine = in.readLine().split("\t");
                        for (int j = 0; j < outLength; j++) {
                            outgoing[j] = Integer.parseInt(tempLine[j]);
                        }
                        int inLength = Integer.parseInt(in.readLine());
                        int[] incoming = new int[inLength];
                        tempLine = in.readLine().split("\t");
                        for (int j = 0; j < inLength; j++) {
                            incoming[j] = Integer.parseInt(tempLine[j]);
                        }
                        Node temp = new Node(length, outgoing, incoming);
                        graph.addNodeCache(i, temp);

                        lineCounter = lineCounter + 5;
                        int finalCount = lineCounter;
                        if (finalCount % (nol / 100) == 0) {
                            Platform.runLater(() -> Window.setProgress((double) finalCount / (double) nol));
                        }
                    }
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            parser.start();
        } catch (IOException e) {
            System.out.println("Error while reading cache");
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
        try {
            File file = new File(filename + ".txt");
            int graphSize = graph.getSize();
            OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            BufferedWriter writer = new BufferedWriter(ow);
            writer.write(Integer.toString(graphSize) + "\n");
            int size;
            for (int i = 0; i < graphSize; i++) {
                Node temp = graph.getNode(i);
                writer.write(Integer.toString(temp.getLength()) + "\n");
                int[] tempList = temp.getOutgoingEdges();
                size = tempList.length;
                writer.write(Integer.toString(size) + "\n");
                for (int j = 0; j < size; j++) {
                    writer.write(Integer.toString(tempList[j]) + "\t");
                }
                writer.newLine();
                tempList = temp.getIncomingEdges();
                size = tempList.length;
                writer.write(Integer.toString(size) + "\n");
                for (int j = 0; j < size; j++) {
                    writer.write(Integer.toString(tempList[j]) + "\t");
                }
                writer.newLine();
            }
            writer.close();
            ow.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the thread the parser is running in.
     * @return thread in which parser is running.
     */
    public static Thread getThread() {
        return parser;
    }
}

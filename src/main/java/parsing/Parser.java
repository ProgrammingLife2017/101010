package parsing;

import datastructure.Node;
import datastructure.NodeGraph;
import datastructure.SegmentDB;
import javafx.application.Platform;
import screens.Window;
import window.FileSelector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;

/**
 * Created by 101010.
 */
public class Parser {
    /**
     * Initial Parser.
     */
    private static Parser instance = null;

    /**
     * Thread the parser is running in.
     */
    private static Thread parser;

    /**
     * The name of the currently selected file.
     */
    private String currentFile;

    /**
     * The number of genome paths contained in the file.
     */
    private double noOfGenomes;

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
        currentFile = file.getName().substring(0, file.getName().length() - 4);
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
            line = in.readLine();

            final String line1 = line;
            line = line.substring(line.indexOf('\t') + 1);
            String absoluteFilePath = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 4);

            String sDB = absoluteFilePath + "Segments.txt";
            String genomesName = absoluteFilePath + "Genomes.txt";
            graph.setSegmentDB(new SegmentDB(sDB));
            File segments = new File(sDB);
            File genomes = new File(genomesName);

            segments.createNewFile();
            genomes.createNewFile();

            BufferedWriter out = new BufferedWriter(new FileWriter(segments));
            BufferedWriter gw = new BufferedWriter(new FileWriter(genomes));

            noOfGenomes = (double) addGenomes(gw, line);

            parser = new Thread(() -> {
                try {
                    int lineCounter = 1;
                    int nol = getNumberOfLine(file);
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
                                Node node = new Node(segment.length(), new int[0], new int[0]);
                                graph.addNode(id, node);
                                out.write(segment + "\n");
                                out.flush();
                                line2 = line2.substring(line2.indexOf('\t') + 1);
                                line2 = line2.substring(line2.indexOf('\t') + 1);
                                String nodeGenomes = line2.substring(0, line2.indexOf('\t'));
                                node.setWeight(addGenomes(gw, nodeGenomes) / noOfGenomes);
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
                            updateProgressBar(lineCounter, nol);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    in.close();
                    out.close();
                    gw.close();
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
                    int nol = getNumberOfLine(cache);
                    String path = cache.getAbsolutePath();
                    path = path.substring(0, path.length() - 4);
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path + "Genomes.txt")));
                    noOfGenomes = Double.parseDouble(br.readLine().split("\t")[0]);
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
                        setWeights(br, temp);
                        updateProgressBar(lineCounter, nol);
                    }
                    br.close();
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
     * Writes alle genomes in the string to the file given by the writer.
     * @param gw Writer that writes the string.
     * @param str String with the genomes.
     */
    private int addGenomes(BufferedWriter gw, String str) {
        str = str.substring(str.indexOf(':') + 1);
        str = str.substring(str.indexOf(':') + 1);
        String[] genomeTemp = str.split(";");
        try {
            gw.write(genomeTemp.length + "\t");
            for (String string : genomeTemp) {
                gw.write(string.substring(0, string.length() - 6) + "\t");
            }
            gw.write("\n");
            gw.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("adding genomes failed");
        }
        return genomeTemp.length;
    }

    /**
     * Returns the number of lines in the given file.
     * @param file The file we want to know the number of line of.
     * @return The number of lines the given file contains.
     * @throws IOException If the file cant be found this exception will be thrown.
     */
    private int getNumberOfLine(File file) throws IOException {
        LineNumberReader lnr = new LineNumberReader(new FileReader(file));
        lnr.skip(Long.MAX_VALUE);
        int nol = lnr.getLineNumber() + 1;
        lnr.close();

        return nol;
    }

    /**
     * Update the progressbar if enough progress is made.
     * @param lineCount The current line the parser is within the file.
     * @param nol The total number of lines in the file that is currently being parsed.
     */
    private void updateProgressBar(int lineCount, int nol) {
        if (nol < 100 || lineCount % (nol / 100) == 0) {
            Platform.runLater(() -> Window.setProgress((double) lineCount / (double) nol));
        }
    }

    /**
     * Gets the thread the parser is running in.
     * @return thread in which parser is running.
     */
    public static Thread getThread() {
        return parser;
    }

    /**
     * Sets the weights of nodes when reading in from the cache file.
     * @param br the reader that reads the cached file.
     * @param node the node a weight is being set to.
     */
    private void setWeights(BufferedReader br, Node node) {
        try {
            node.setWeight(Double.parseDouble(br.readLine().split("\t")[0]) / noOfGenomes);
        } catch (Exception e) {
            System.out.println("Error when reading in genome cache");
            e.printStackTrace();
        }
    }
}

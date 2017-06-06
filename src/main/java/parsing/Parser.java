package parsing;

import datastructure.Node;
import datastructure.NodeGraph;
import datastructure.SegmentDB;
import javafx.application.Platform;
import screens.Window;

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
            while (!line.startsWith("H\tORI")) {
                line = in.readLine();
            }

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

            boolean integerBased = true;

            String[] allGenomes = generateGenomes(gw, line);

            line = in.readLine();
            integerBased = determineBasis(line, allGenomes);
            final String line1 = line;
            final boolean threadIntegerBased = integerBased;



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
                                graph.addNode(id, new Node(segment.length(), new int[0], new int[0]));
                                out.write(segment + "\n");
                                out.flush();
                                line2 = line2.substring(line2.indexOf('\t') + 1);
                                line2 = line2.substring(line2.indexOf('\t') + 1);
                                if (line2.contains("\t")) {
                                    line2 = line2.substring(0, line2.indexOf("\t"));
                                }
                                addGenomes(gw, line2, threadIntegerBased, allGenomes);
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

                        updateProgressBar(lineCounter, nol);
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
     * Writes all genomes in the string to the file given by the writer.
     * @param gw Writer that writes the string.
     * @param str String with the genomes.
     * @param hasInt true iff the genomes displayed as integers instead of names.
     * @param genomeList list of all genomes in the gfa file.
     */
    private void addGenomes(BufferedWriter gw, String str, boolean hasInt, String[] genomeList) {
        str = str.substring(str.indexOf(':') + 1);
        str = str.substring(str.indexOf(':') + 1);
        String[] genomeTemp = str.split(";");
        try {
            gw.write(genomeTemp.length + "\t");
            for (String string : genomeTemp) {
                if (hasInt) {
                    gw.write(string + "\t");
                } else {
                    for (int i = 0; i < genomeList.length; i++) {
                        if (string.equals(genomeList[i])) {
                            gw.write(i + "\t");
                            break;
                        }
                    }
                }
            }
            gw.write("\n");
            gw.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("adding genomes failed");
        }
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
     * Reads all genomes of the gfa file and caches them.
     * @param gw the writer used to write to the cache.
     * @param line the line on which all genomes are listed.
     * @throws IOException when the writer can't write to the file.
     * @return the array of all genomes in the gfa file.
     */
    private String[] generateGenomes(BufferedWriter gw, String line) throws IOException {
        String str = line.substring(line.indexOf(':') + 1);
        str = str.substring(str.indexOf(':') + 1);
        if (str.contains("\t")) {
            str = str.substring(0, str.indexOf("\t"));
        }
        String[] allGenomes = str.split(";");
        gw.write(allGenomes.length + "\t");
        for (int i = 0; i < allGenomes.length; i++) {
            gw.write(allGenomes[i] + "\t");
        }
        gw.write("\n");
        gw.flush();
        return allGenomes;
    }

    /**
     * Determines if the gfa file displays genomes as ints or names.
     * @param line a line of the gfa file that is to be determined.
     * @param allGenomes all the gemones of the gfa file.
     * @return true iff the genomes are displayed as integers.
     */
    private boolean determineBasis(String line, String[] allGenomes) {
        boolean result = true;
        String str = line.substring(line.indexOf(':') + 1);
        str = str.substring(str.indexOf(':') + 1);
        if (str.contains("\t")) {
            str = str.substring(0, str.indexOf("\t"));
        }
        str = str.split(";")[0];

        for (int i = 0; i < allGenomes.length; i++) {
            if (str.equals(allGenomes[i])) {
                result = false;
                break;
            }
        }
        return result;
    }
}

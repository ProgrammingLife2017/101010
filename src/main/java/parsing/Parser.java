package parsing;

import datastructure.Node;
import datastructure.NodeGraph;
import datastructure.SegmentDB;
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
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 101010.
 */
public final class Parser {
    /**
     * Initial Parser.
     */
    private static Parser instance = null;

    /**
     * The filename of the currently selected file.
     */
    private String currentFile;

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
     * @param file The name of the target .gfa file.
     * @param graph The graph the data gets put into.
     * @return The graph created from the .gfa file.
     */
    public NodeGraph parse(final File file, NodeGraph graph) {

        try {
            BufferedReader in = new BufferedReader(
                    new FileReader(file));
            String line = in.readLine();
            line = in.readLine();
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

            addGenomes(gw, line);

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
                    line = line.substring(line.indexOf('\t') + 1);
                    line = line.substring(line.indexOf('\t') + 1);
                    String nodeGenomes = line.substring(0, line.indexOf('\t'));
                    addGenomes(gw, nodeGenomes);
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
            gw.close();
            createCache(absoluteFilePath, graph);
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
            }
            in.close();
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
     * Reads the genome cache file and returns the node ID's with a certain genomeName.
     * @param genomeName the name of the genome of which occurring nodes are selected.
     * @return a set of all occurring nodes in the genome.
     */
    public Set<Integer> getGenomeNodes(String genomeName) {
        Set<Integer> genNodes = new HashSet<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(FileSelector.getDirectory() + currentFile + "Genomes.txt")));
            br.readLine();
            for (int i = 0; i < NodeGraph.getCurrentInstance().getNodes().size(); i++) {
                String line = br.readLine();
                String[] genomes = line.split("\t");
                for (int j = 1; j < genomes.length; j++) {
                    if (genomes[j].equals(genomeName)) {
                        genNodes.add(i);
                    }
                }
            }
            br.close();
            return genNodes;
        } catch (IOException e) {
            System.out.println("Can't find genome cache.");
            e.printStackTrace();
        }
        return null;
    }

     /** Writes all genomes in the string to the file given by the writer.
     * @param gw Writer that writes the string.
     * @param str String with the genomes.
     */
    private void addGenomes(BufferedWriter gw, String str) {
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
    }
}

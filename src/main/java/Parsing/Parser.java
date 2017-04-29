package Parsing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.TreeMap;

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

    public TreeMap segments(String filename) {
        TreeMap result = new TreeMap();
//        Link
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));
            String line = in.readLine();
            String key;
            while (line != null) {
                if (line.startsWith("S")) {
                    line = line.substring(2);
                    key = line.substring(0, line.indexOf("\t"));
                    line = line.substring(line.indexOf("\t") + 1);
                    if (line.indexOf("\t") != -1) {
                        line = line.substring(0, line.indexOf("\t"));
                    }
                    result.put(Integer.parseInt(key), new Node(line));
                } else if (line.startsWith("L")) {
                    line = line.substring(2);
                    System.out.println(line);
                }
                line = in.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String args[]) {
        Parser parser = Parser.getInstance();
        TreeMap map = parser.segments("/test.gfa");
        Node first = (Node) map.get(map.firstKey());
        System.out.println(first.getSegment());
        first.reverseComplement();
        System.out.println(first.getSegment());
    }
}

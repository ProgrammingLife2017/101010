package Parsing;

import java.io.BufferedReader;
import java.io.FileReader;
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
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));
            String line = in.readLine();
            String key;
            while (line != null) {
                if (line.startsWith("S")) {
                    line = line.substring(2);
                    key = line.substring(0, line.indexOf("\t"));
                    line = line.substring(line.indexOf("\t"));
                    //TODO fix for actual file
                    result.put(key, line);
                }
                line = in.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

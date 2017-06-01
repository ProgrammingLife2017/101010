package datastructure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by 101010.
 */
public class SegmentDB {
    /**
     * The location of the database.
     */
    private String dbLoc;

    /**
     * Empty constructor for SegmentDB.
     */
    public SegmentDB() {
        dbLoc = "/resources/GenomeDB.txt";
    }

    /**
     * Constructor for SegmentDB.
     *
     * @param dbLocation the location of the database.
     */
    public SegmentDB(String dbLocation) {
        this.dbLoc = dbLocation;
    }

    /**
     * Gets the segment of the provided node
     * from the database.
     *
     * @param id The id of the node.
     * @return The segment corresponding to the id.
     */
    public String getSegment(int id) {
        String res = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(dbLoc));

            res = br.readLine();

            while (id > 0 && res != null) {
                res = br.readLine();
                id--;
            }
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return res;
    }
}

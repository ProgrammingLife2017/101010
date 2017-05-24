package datastructure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by 101010.
 */
public class SegmentDB implements Serializable {
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
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    public boolean equals(Object other) {
        if (other instanceof SegmentDB) {
            SegmentDB that = (SegmentDB) other;
            return this.dbLoc.equals(that.dbLoc);
        }
        return false;
    }

    public int hashCode() {
        return dbLoc.hashCode();
    }

    public String getdbLoc() {
        return dbLoc;
    }
}

package datastructure;


import javax.sql.rowset.serial.SerialClob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

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
        dbLoc = "jdbc:h2:~/h2/genomedb";
        dbInit();
    }

    /**
     * Constructor for SegmentDB.
     *
     * @param newDbLoc the location of the database.
     */
    public SegmentDB(final String newDbLoc) {
        if (newDbLoc.startsWith("jdbc:h2:~/")) {
            this.dbLoc = newDbLoc;
        } else {
            this.dbLoc = "jdbc:h2:~/" + newDbLoc;
        }
        dbInit();
    }

    /**
     * Initializes the database by creating a new empty database
     * and creating the necessary tables in that database.
     * The following tables are created:
     * segment - PRIMARY KEY:    node_id (INT)
     *           OTHER:          segment (CLOB, NOT NULL)
     */
    private void dbInit() {
        try {
            Class.forName("org.h2.Driver");
            Connection con;
            con = DriverManager.getConnection(dbLoc);
            con.prepareStatement("DROP TABLE IF EXISTS segment").execute();
            con.prepareStatement("CREATE TABLE segment(node_id INT PRIMARY KEY, "
                    + "segment CLOB NOT NULL)").execute();
            con.close();
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Inserts a node and edges into the database
     * based on the provided parameters.
     *
     * @param id             the id of the node.
     * @param segment        the segment of the node.
     */
    public void addSegment(final int id,
                        final String segment) {
        try {
            String iSegment =
                    "INSERT INTO SEGMENT \n"
                            + "VALUES (?, ?)";

            Class.forName("org.h2.Driver");
            Connection con = DriverManager.getConnection(dbLoc);

            PreparedStatement insertNode = con.prepareStatement(iSegment);
            insertNode.setInt(1, id);
            insertNode.setClob(2, new SerialClob(segment.toCharArray()));
            insertNode.execute();

            con.close();
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Gets the segment of the provided node
     * from the database.
     *
     * @param id The id of the node.
     * @return The segment corresponding to the id.
     */
    public String getSegment(final int id) {
        String res = "";

        try {
            Class.forName("org.h2.Driver");
            Connection con = DriverManager.getConnection(dbLoc);
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT segment FROM SEGMENT WHERE node_id = ?"
            );
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.first();
            Clob tempClob = rs.getClob(1);
            res = tempClob.getSubString(1, (int) tempClob.length());
            con.close();
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return res;
    }
}

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
public class NodeDB {
    /**
     * The location of the database.
     */
    private String dbLoc;

    /**
     * Empty constructor for NodeDB.
     */
    public NodeDB() {
        dbLoc = "jdbc:h2:~/h2/genomedb";
        dbInit();
    }

    /**
     * Constructor for NodeDB.
     *
     * @param newDbLoc the location of the database.
     */
    public NodeDB(final String newDbLoc) {
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
     * node -  PRIMARY KEY:    id (INT)
     * OTHER:          segment (CLOB, NOT NULL)
     * length (INT, NOT NULL)
     * edges - PRIMARY KEY:    from (INT, FOREIGN KEY node(id))
     * to (INT)
     */
    private void dbInit() {
        try {
            Class.forName("org.h2.Driver");
            Connection con;
            con = DriverManager.getConnection(dbLoc);
            con.prepareStatement("DROP TABLE IF EXISTS node").execute();
            con.prepareStatement("DROP TABLE IF EXISTS edges").execute();
            con.prepareStatement("CREATE TABLE node(id INT PRIMARY KEY, "
                    + "segment CLOB NOT NULL, "
                    + "length INT NOT NULL)").execute();
            con.prepareStatement("CREATE TABLE edges(from_id INT NOT NULL, "
                    + "FOREIGN KEY (from_id) REFERENCES node(id), "
                    + "to_id INT  NOT NULL,"
                    + "PRIMARY KEY(from_id, to_id))").execute();
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
     * Returns the Node with id as id from the database.
     *
     * @param id the id of the requested node.
     * @return A resultset containing all nodes at the given coordinate.
     */
    public Node getNode(final int id) {
        Node res = new Node();

        try {
            Class.forName("org.h2.Driver");
            Connection con = DriverManager.getConnection(dbLoc);
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT length FROM NODE WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.first();
            int length = rs.getInt("length");

            stmt = con.prepareStatement(
                    "SELECT DISTINCT from_id FROM EDGES WHERE to_id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            rs.last();
            int[] icedges = new int[rs.getRow()];
            rs = stmt.executeQuery();
            rs.first();

            for (int i = 0; i < icedges.length; i++) {
                icedges[i] = rs.getInt("from_id");
                rs.next();
            }

            stmt = con.prepareStatement(
                    "SELECT DISTINCT to_id FROM EDGES WHERE from_id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            rs.last();
            int[] ogedges = new int[rs.getRow()];
            rs = stmt.executeQuery();
            rs.first();

            for (int i = 0; i < ogedges.length; i++) {
                ogedges[i] = rs.getInt("to_id");
                rs.next();
            }

            con.close();
            res = new Node(id, length, ogedges, icedges);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Inserts a node and edges into the database
     * based on the provided parameters.
     *
     * @param id             the id of the node.
     * @param segment        the segment of the node.
     * @param destinationIDs the ids of the nodes the outgoing edges of this node go to.
     */
    public void addNode(final int id,
                        final String segment,
                        final int[] destinationIDs) {
        try {
            String iNode =
                    "INSERT INTO node \n"
                            + "VALUES (?, ?, ?)";
            String iEdges =
                    "INSERT INTO edges \n"
                            + "VALUES (?, ?)";

            Class.forName("org.h2.Driver");
            Connection con = DriverManager.getConnection(dbLoc);

            PreparedStatement insertNode = con.prepareStatement(iNode);
            insertNode.setInt(1, id);
            insertNode.setClob(2, new SerialClob(segment.toCharArray()));
            insertNode.setInt(2 + 1, segment.length());
            insertNode.execute();

            PreparedStatement insertEdges = con.prepareStatement(iEdges);
            insertEdges.setInt(1, id);
            for (int i = 0; i < destinationIDs.length; i++) {
                insertEdges.setInt(2, destinationIDs[i]);
                insertEdges.execute();
            }

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
                    "SELECT segment FROM NODE WHERE id = ?"
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

package Datastructure;

import com.koloboke.collect.map.hash.HashIntObjMaps;
import com.koloboke.collect.map.hash.HashIntObjMap;
import java.sql.*;

/**
 * Created by 101010.
 */
public class NodeMap {
    HashIntObjMap<int[]> map;
    String dbLoc;

    public NodeMap() {
        this.map = HashIntObjMaps.newUpdatableMap();
        dbLoc = "jdbc:h2:~/h2/genomedb";
        dbInit();
    }

    public NodeMap(HashIntObjMap<int[]> map, String dbLoc) {
        this.map = map;
        if(dbLoc.startsWith("jdbc:h2:~/")) {
            this.dbLoc = dbLoc;
        }
        else {
            this.dbLoc = "jdbc:h2:~/" + dbLoc;
        }
        dbInit();
    }

    /**
     * Initializes the database by creating a new empty database and creating the necessary tables in that database.
     * The following tables are created:
     *      node -  PRIMARY KEY:    id (INT)
     *              OTHER:          segment (CLOB, NOT NULL)
     *                              coordinate (INT, NOT NULL)
     *      edges - PRIMARY KEY:    from_id (INT, FOREIGN KEY node(id))
     *                              to_id (INT)
     */
    private void dbInit() {
        try {
            Class.forName("org.h2.Driver");
            Connection con = DriverManager.getConnection(dbLoc);
            con.prepareStatement("DROP TABLE IF EXISTS node").execute();
            con.prepareStatement("DROP TABLE IF EXISTS edges").execute();
            con.prepareStatement("CREATE TABLE node(id INT PRIMARY KEY, coordinate INT NOT NULL, segment CLOB NOT NULL)").execute();
            con.prepareStatement("CREATE TABLE edges(from_id INT PRIMARY KEY, FOREIGN KEY (from_id) REFERENCES node(id), to_id INT PRIMARY KEY)").execute();
            con.close();
        }
        catch(ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Returns a ResultSet of all nodes at a certain coordinate.
     * @param coordinate - the coordinate of the requested nodes.
     * @return A resultset containing all nodes at the given coordinate.
     */
    private ResultSet getNodes(int coordinate) {
        try {
            Class.forName("org.h2.Driver");
            Connection con = DriverManager.getConnection(dbLoc);
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM NODE WHERE coordinate = ?");
            stmt.setInt(1, coordinate);
            ResultSet res = stmt.executeQuery();
            con.close();
            return res;
        }
        catch(ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Inserts a node and edges into the database based on the provided parameters.
     * @param id - the id of the node.
     * @param coordinate - the coordinate of the node.
     * @param segment - the segment of the node.
     * @param destinationIDs - the ids of the nodes the outgoing edges of this node go to.
     */
    public void addNode(int id, int coordinate, String segment, int[] destinationIDs) {
        try {
            String iNode =
                    "INSERT INTO NODE" +
                    "VALUES (?, ?, ?)";
            String iEdges =
                    "INSERT INTO EDGES" +
                    "VALUES (?, ?)";

            Class.forName("org.h2.Driver");
            Connection con = DriverManager.getConnection(dbLoc);

            PreparedStatement insertNode = con.prepareStatement(iNode);
            insertNode.setInt(1, id);
            insertNode.setInt(2, coordinate);
            insertNode.setString(3, segment);
            insertNode.execute();

            PreparedStatement insertEdges = con.prepareStatement(iEdges);
            insertEdges.setInt(1, id);
            for(int i = 0; i < destinationIDs.length; i++) {
                insertEdges.setInt(2, destinationIDs[i]);
                insertEdges.execute();
            }

            con.close();
        }
        catch(ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}

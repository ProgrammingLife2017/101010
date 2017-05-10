package datastructure;

import com.koloboke.collect.map.hash.HashIntObjMaps;
import com.koloboke.collect.map.hash.HashIntObjMap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Created by 101010.
 */
public class NodeMap {
    /**
     * The HashMap that returns all the ids of nodes with the same coordinate.
     */
    private HashIntObjMap<int[]> map;
    /**
     * The location of the database.
     */
    private String dbLoc;

    /**
     * Empty constructor for NodeMap.
     */
    public NodeMap() {
        this.map = HashIntObjMaps.newUpdatableMap();
        dbLoc = "jdbc:h2:~/h2/genomedb";
        dbInit();
    }

    /**
     * Constructor for NodeMap.
     * @param newMap the hashmap with coordinates.
     * @param newDbLoc the location of the database.
     */
    public NodeMap(final HashIntObjMap<int[]> newMap, final String newDbLoc) {
        this.map = newMap;
        if (newDbLoc.startsWith("jdbc:h2:~/")) {
            this.dbLoc = newDbLoc;
        }
        else {
            this.dbLoc = "jdbc:h2:~/" + newDbLoc;
        }
        dbInit();
    }

    /**
     * Initializes the database by creating a new empty database and creating the necessary tables in that database.
     * The following tables are created:
     *      node -  PRIMARY KEY: id (INT)
     *              OTHER: segment (CLOB, NOT NULL)
     *      edges - PRIMARY KEY: from (INT, FOREIGN KEY node(id)), to (INT, FOREIGN KEY node(id))
     */
    private void dbInit() {
        try {
            Class.forName("org.h2.Driver");
            Connection con;
            con = DriverManager.getConnection(dbLoc);
            con.prepareStatement("DROP TABLE IF EXISTS node").execute();
            con.prepareStatement("DROP TABLE IF EXISTS edges").execute();
            con.prepareStatement("CREATE TABLE node(id INT PRIMARY KEY, segment CLOB NOT NULL)").execute();
            con.prepareStatement("CREATE TABLE edges(from_id INT PRIMARY KEY, FOREIGN KEY (from_id) REFERENCES node(id), to_id INT NOT NULL, FOREIGN KEY (to_id) REFERENCES node(id))").execute();
            con.close();
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Gets the ids of the nodes that have outgoing edges to current node.
     * @param id the id of the node in question.
     * @return
     */
    public ResultSet getIncomingEdges(final int id) {
        ResultSet result = null;
        try{
            Connection con;
            con = DriverManager.getConnection(dbLoc);
            Statement temp = con.createStatement();
            result = temp.executeQuery("SELECT from_id FROM EDGES WHERE to_id='" + id + "'");
            con.close();
        } catch (SQLException e){
            System.out.println("Database was corrupted");
        }
        return result;
    }

    /**
     * Gets the ids of the nodes that the current node has outgoing edges to.
     * @param id hte id of the node in question.
     * @return
     */
    public ResultSet getOutgoingEdges(final int id) {
        ResultSet result = null;
        try{
            Connection con;
            con = DriverManager.getConnection(dbLoc);
            Statement temp = con.createStatement();
            result = temp.executeQuery("SELECT to_id FROM EDGES WHERE from_id='" + id + "'");
            con.close();
        } catch (SQLException e){
            System.out.println("Database was corrupted");
        }
        return result;
    }


}

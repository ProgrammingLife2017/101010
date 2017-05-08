package Datastructure;

import com.koloboke.collect.map.hash.HashIntObjMaps;
import com.koloboke.collect.map.hash.HashIntObjMap;
import java.sql.*;

/**
 * Created by DexterQuintin on 5/8/2017.
 */
public class NodeMap {
    HashIntObjMap<int[]> map;
    String dbLoc;
    Connection con;

    public NodeMap() {
        this.map = HashIntObjMaps.<int[]>newUpdatableMap(0);
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
     *      node -  PRIMARY KEY: id (INT)
     *              OTHER: segment (CLOB, NOT NULL)
     *      edges - PRIMARY KEY: from (INT, FOREIGN KEY node(id)), to (INT, FOREIGN KEY node(id))
     */
    private void dbInit() {
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(dbLoc, "", "");
            con.prepareStatement("CREATE TABLE node(id INT PRIMARY KEY, segment CLOB NOT NULL)").execute();
            con.prepareStatement("CREATE TABLE edges(from INT NOT NULL, FOREIGN KEY (from) REFERENCES node(id), to INT NOT NULL, FOREIGN KEY (to) REFERENCES node(id))").execute();
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

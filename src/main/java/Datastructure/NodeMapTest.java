package Datastructure;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by 101010.
 */
class NodeMapTest {
    NodeMap nodemap = null;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        nodemap = new NodeMap();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        nodemap = null;
    }

    @Test
    void constructorTest(){
        try{
            Connection temp = DriverManager.getConnection("jdbc:h2:~/h2/genomedb", "", "");
            temp.close();
            assertEquals(nodemap.con.getMetaData(), temp.getMetaData());
        } catch (SQLException e) {
                e.printStackTrace();
        }

        assertEquals(nodemap.dbLoc,"jdbc:h2:~/h2/genomedb");
    }
}
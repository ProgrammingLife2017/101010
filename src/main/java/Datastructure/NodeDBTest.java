package Datastructure;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by 101010.
 */
class NodeDBTest {
    NodeDB nodedb = null;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        nodedb = new NodeDB();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        nodedb = null;
    }

}
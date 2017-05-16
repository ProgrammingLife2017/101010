package datastructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by 101010.
 */
class NodeTest {
    private Node node = null;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        node = new Node();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        node = null;
    }

    @org.junit.jupiter.api.Test
    void getLength() {
        assertEquals(0, node.getLength());
    }

    @org.junit.jupiter.api.Test
    void getOutgoingEdges() {
        assertEquals(0, node.getOutgoingEdges().length);
    }

    @org.junit.jupiter.api.Test
    void getIncomingEdges() {
        assertEquals(0, node.getIncomingEdges().length);
    }

    @org.junit.jupiter.api.Test
    void equals() {
        assertTrue(node.equals(new Node(0, new int[0], new int[0])));
    }

    @org.junit.jupiter.api.Test
    void equalsNull() {
        assertFalse(node.equals(null));
    }
}
package datastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Created by 101010.
 */
class NodeTest {
    /**
     * The node used to test.
     */
    private Node node = null;

    /**
     * Before each test we set the node to a new node.
     */
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        node = new Node();
    }

    /**
     * After each test we set the node back to null.
     */
    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        node = null;
    }

    /**
     * Check if a new node has length 0.
     */
    @org.junit.jupiter.api.Test
    void getLength() {
        assertEquals(0, node.getLength());
    }

    @org.junit.Test
    /**
     * Check if a new node has no outgoing edges.
     */
    @org.junit.jupiter.api.Test
    void getOutgoingEdges() {
        assertEquals(0, node.getOutgoingEdges().length);
    }

    /**
     * Check if a new node has no incoming edges.
     */
    @org.junit.jupiter.api.Test
    void getIncomingEdges() {
        assertEquals(0, node.getIncomingEdges().length);
    }

    /**
     * Check if the default constructor is the same as constructing with size 0 integer arrays.
     */
    @org.junit.jupiter.api.Test
    void equals() {
        assertTrue(node.equals(new Node(0, new int[0], new int[0])));
    }

    /**
     * Check if a new node is not null.
     */
    @org.junit.jupiter.api.Test
    void equalsNull() {
        assertFalse(node.equals(null));
    }
}


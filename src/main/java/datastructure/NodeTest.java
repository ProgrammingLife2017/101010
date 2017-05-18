package datastructure;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;

/**
 * Created by 101010.
 */
class NodeTest {
    private Node node = null;

    @org.junit.Before
    void setUp() {
        node = new Node();
    }

    @org.junit.After
    void tearDown() {
        node = null;
    }

    @org.junit.Test
    void getLength() {
        assertEquals(0, node.getLength());
    }

    @org.junit.Test
    void getOutgoingEdges() {
        assertEquals(0, node.getOutgoingEdges().length);
    }

    @org.junit.Test
    void getIncomingEdges() {
        assertEquals(0, node.getIncomingEdges().length);
    }

    @org.junit.Test
    void equals() {
        assertTrue(node.equals(new Node(0, new int[0], new int[0])));
    }

    @org.junit.Test
    void equalsNull() {
        assertFalse(node.equals(null));
    }

}
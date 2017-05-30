package datastructure;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
    @Before
    void setUp() {
        node = new Node();
    }

    /**
     * After each test we set the node back to null.
     */
    @After
    void tearDown() {
        node = null;
    }

    /**
     * Check if a new node has length 0.
     */
    @Test
    void getLength() {
        assertEquals(0, node.getLength());
    }

    /**
     * Check if a new node has no outgoing edges.
     */
    @Test
    void getOutgoingEdges() {
        assertEquals(0, node.getOutgoingEdges().length);
    }

    /**
     * Check if a new node has no incoming edges.
     */
    @Test
    void getIncomingEdges() {
        assertEquals(0, node.getIncomingEdges().length);
    }

    /**
     * Test the addIncomingEdgeMethod of Node.
     */
    @Test
    void addIncomingEdgeTest() {
        assertEquals(0, node.getIncomingEdges().length);
        node.addIncomingEdge(5);
        node.addIncomingEdge(2);
        assertEquals(5, node.getIncomingEdges()[0]);
        assertEquals(2, node.getIncomingEdges()[1]);
    }

    /**
     * Test the addOutgoingEdgeMethod of Node.
     */
    @Test
    void addOutgoingEdgeTest() {
        assertEquals(0, node.getOutgoingEdges().length);
        node.addOutgoingEdge(5);
        node.addOutgoingEdge(2);
        assertEquals(5, node.getOutgoingEdges()[0]);
        assertEquals(2, node.getOutgoingEdges()[1]);
    }

    /**
     * Check if the default constructor is the same as constructing with size 0 integer arrays.
     */
    @Test
    void equalsTrue() {
        assertTrue(node.equals(new Node(0, new int[0], new int[0])));
    }

    /**
     * Check if a new node is not null.
     */
    @Test
    void equalsNull() {
        assertFalse(node == null);
    }

    /**
     * Test the getX and getY method of Node.
     */
    @Test
    void testGetXY() {
        assertEquals(0, node.getX());
        assertEquals(0, node.getY());
    }

    /**
     * Test the setX and setY method of Node.
     */
    @Test
    void testSetXY() {
        node.setX(100);
        node.setY(300);
        assertEquals(100, node.getX());
        assertEquals(300, node.getY());
    }

    /**
     * Test the getChild method of Node.
     */
    @Test
    void testGetChild() {
        assertEquals(0, node.getChild());
    }

    /**
     * Test the getChild method of Node.
     */
    @Test
    void testSetChild() {
        node.setChild(2);
        assertEquals(2, node.getChild());
    }

    /**
     * Test the getChild method of Node.
     */
    @Test
    void testGetSetInDegree() {
        assertEquals(0, node.getInDegree());
        node.setInDegree(4);
        assertEquals(4, node.getInDegree());
    }

    /**
     * Test the getChild method of Node.
     */
    @Test
    void testSetInDegree() {
        node.setChild(2);
        assertEquals(node.getChild(), 2);
    }
}

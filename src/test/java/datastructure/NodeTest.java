package datastructure;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

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
     * Check if computeLength properly sets lengths below 100 to 10.
     */
    @Test
    void computeLengthMin() {
        assertEquals(0, node.getLength());
        node.computeLength();
        assertEquals(10, node.getLength());
    }

    /**
     * Check if computeLength properly sets lengths above 255^2 to 255.
     */
    @Test
    void computeLengthMax() {
        node = new Node(Integer.MAX_VALUE, new int[0], new int[0]);
        assertEquals(Integer.MAX_VALUE, node.getLength());
        node.computeLength();
        assertEquals(255, node.getLength());
    }

    /**
     * Check whether negative lengths are handled properly by computeLength.
     */
    @Test
    void computeLengthNeg() {
        node = new Node(Integer.MIN_VALUE, new int[0], new int[0]);
        assertEquals(Integer.MIN_VALUE, node.getLength());
        node.computeLength();
        assertEquals(10, node.getLength());
    }

    /**
     * Checks whether computeLength functions properly with lengths between 100 and 255^2.
     */
    @Test
    void computeLengthSt() {
        int rInt = new Random().nextInt(65015) + 10;
        node = new Node(rInt, new int[0], new int[0]);
        assertEquals(rInt, node.getLength());
        node.computeLength();
        assertEquals((int) Math.sqrt((double) rInt), node.getLength());
    }

    /**
     * Checks whether setIncomingEdges properly sets the incomingedges field.
     */
    @Test
    void setIncomingEdgesTest() {
        Random r = new Random();
        int[] rIntA = new int[r.nextInt(300000000)];
        for (int i = 0; i < rIntA.length; i++) {
            rIntA[i] = r.nextInt();
        }
        node.setIncomingEdges(rIntA);
        int[] edges = node.getIncomingEdges();
        for (int i = 0; i < rIntA.length; i++) {
            assertEquals(rIntA[i], edges[i]);
        }
    }

    /**
     * Checks whether the hashCodes of two equal nodes are equal.
     */
    @Test
    void hashCodeSame() {
        assertEquals(new Node(0, new int[0], new int[0]).hashCode(), node.hashCode());
    }

    /**
     * Generates two random equal nodes and checks whether their hashcodes are equal.
     */
    @Test
    void hashCodeRandom() {
        Random r = new Random();
        int l = r.nextInt();
        int i = r.nextInt(75000);
        int j = r.nextInt(75000);
        int[] iEdges = new int[i];
        int[] jEdges = new int[j];
        for (int k = 0; k < i; k++) {
            iEdges[k] = r.nextInt();
        }
        for (int k = 0; k < j; k++) {
            jEdges[k] = r.nextInt();
        }
        node = new Node(l, iEdges, jEdges);
        assertEquals(new Node(l, iEdges, jEdges).hashCode(), node.hashCode());
    }
}


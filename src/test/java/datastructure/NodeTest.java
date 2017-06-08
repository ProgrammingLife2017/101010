package datastructure;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by 101010.
 */
public class NodeTest {
    /**
     * The node used to test.
     */
    public Node node = null;

    /**
     * Before each test we set the node to a new node.
     */
    @Before
    public void setUp() {
        node = new datastructure.Node();
    }

    /**
     * After each test we set the node back to null.
     */
    @After
    public void tearDown() {
        node = null;
    }

    /**
     * Check if the default constructor is the same as constructing with size 0 integer arrays.
     */
    @Test
    public void equalsTrue() {
        assertTrue(node.equals(new Node(0, new int[0], new int[0])));
    }

    /**
     * Check if a new node is not null.
     */
    @Test
    public void equalsNull() {
        assertFalse(node == null);
    }

    /**
     * Check if computeLength properly sets lengths below 100 to 10.
     */
    @Test
    public void computeLengthMin() {
        assertEquals(0, node.getLength());
        node.computeLength();
        assertEquals(10, node.getLength());
    }

    /**
     * Check if computeLength properly sets lengths above 255^2 to 255.
     */
    @Test
    public void computeLengthMax() {
        node = new Node(Integer.MAX_VALUE, new int[0], new int[0]);
        assertEquals(Integer.MAX_VALUE, node.getLength());
        node.computeLength();
        assertEquals(255, node.getLength());
    }

    /**
     * Check whether negative lengths are handled properly by computeLength.
     */
    @Test
    public void computeLengthNeg() {
        node = new Node(Integer.MIN_VALUE, new int[0], new int[0]);
        assertEquals(Integer.MIN_VALUE, node.getLength());
        node.computeLength();
        assertEquals(10, node.getLength());
    }

    /**
     * Checks whether computeLength functions properly with lengths between 100 and 255^2.
     */
    @Test
    public void computeLengthSt() {
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
    public void setIncomingEdgesTest() {
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
    public void hashCodeSame() {
        assertEquals(new Node(0, new int[0], new int[0]).hashCode(), node.hashCode());
    }

    /**
     * Generates two random equal nodes and checks whether their hashcodes are equal.
     */
    @Test
    public void hashCodeRandom() {
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

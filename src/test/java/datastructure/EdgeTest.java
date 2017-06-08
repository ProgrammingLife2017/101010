package datastructure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by 101010.
 */
public class EdgeTest {
    private Edge edge;
    @Before
    public void setUp() {
        edge = new Edge(1,3);
    }

    @After
    public void tearDown() {
        edge = null;
    }

    @Test
    public void getParent() throws Exception {
        assertEquals(edge.getParent(), 1);
    }

    @Test
    public void getChild() throws Exception {
        assertEquals(edge.getChild(), 3);
    }

    @Test
    public void getWeight() throws Exception {
        assertEquals(edge.getWeight(), 2);
    }

    @Test
    public void setWeight() throws Exception {
        edge.setWeight(4);
        assertEquals(edge.getWeight(), 4);
    }

    @Test
    public void equals() throws Exception {
        Edge edge2 = new Edge(2, 3);
        assertFalse(edge.equals(edge2));
        Edge edge3 = new Edge(1, 3);
        assertEquals(edge3, edge);
    }

    @Test
    public void hashCodeTest() throws Exception {
        assertEquals(edge.hashCode(), 31 + 3);
    }
}
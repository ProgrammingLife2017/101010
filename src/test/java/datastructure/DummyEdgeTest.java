package datastructure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by 101010.
 */
public class DummyEdgeTest {
    private DummyEdge dummyEdge;

    @Before
    public void setUp() {
        dummyEdge = new DummyEdge(0, 1);
    }

    @After
    public void tearDown() {
        dummyEdge = null;
    }

    @Test
    public void addFirstXYEmpty() {
        int x = 10;
        int y = 15;
        dummyEdge.addFirst(x, y);
        assertEquals(x, dummyEdge.getFirstX());
        assertEquals(y, dummyEdge.getFirstY());
        assertEquals(x, dummyEdge.getLastX());
        assertEquals(y, dummyEdge.getLastY());
    }

    @Test
    public void addFirstXYNonEmpty() {
        int x = 2;
        int y = 5;
        int x2 = 7;
        int y2 = 3;
        dummyEdge.addFirst(2, 5);
        dummyEdge.addFirst(x2, y2);
        assertEquals(x2, dummyEdge.getFirstX());
        assertEquals(y2, dummyEdge.getFirstY());
        assertEquals(x, dummyEdge.getLastX());
        assertEquals(y, dummyEdge.getLastY());
    }

    @Test
    public void addFirstEmpty() {
        try {
            dummyEdge.addFirst();
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("No ArrayIndexOutOfBoundsException should have been thrown.");
        }
    }

    @Test
    public void addFirstNonEmpty() {
        int x = 2;
        int y = 5;
        dummyEdge.addFirst(2, 5);
        dummyEdge.addFirst();
        assertEquals(-98, dummyEdge.getFirstX());
        assertEquals(y, dummyEdge.getFirstY());
        assertEquals(x, dummyEdge.getLastX());
        assertEquals(y, dummyEdge.getLastY());
    }
}

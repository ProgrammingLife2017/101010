package datastructure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
    public void getParent() {
        assertEquals(0, dummyEdge.getParent());
    }

    @Test
    public void getChild() {
        assertEquals(1, dummyEdge.getChild());
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
        dummyEdge.addFirst(x, y);
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
            fail("Expected no ArrayIndexOutOfBoundsException, but \"" + e.getMessage() + "\" was thrown.");
        }
    }

    @Test
    public void addFirstNonEmpty() {
        int x = 2;
        int y = 5;
        dummyEdge.addFirst(x, y);
        dummyEdge.addFirst();
        assertEquals(-98, dummyEdge.getFirstX());
        assertEquals(y, dummyEdge.getFirstY());
        assertEquals(x, dummyEdge.getLastX());
        assertEquals(y, dummyEdge.getLastY());
    }

    @Test
    public void addLastXYEmpty() {
        int x = 8;
        int y = 7;
        dummyEdge.addLast(x, y);
        assertEquals(x, dummyEdge.getFirstX());
        assertEquals(y, dummyEdge.getFirstY());
        assertEquals(x, dummyEdge.getLastX());
        assertEquals(y, dummyEdge.getLastY());
    }

    @Test
    public void addLastXYNonEmpty() {
        int x = 99;
        int y = 12;
        int x2 = 672;
        int y2 = -991;
        dummyEdge.addLast(x, y);
        dummyEdge.addLast(x2, y2);
        assertEquals(x, dummyEdge.getFirstX());
        assertEquals(y, dummyEdge.getFirstY());
        assertEquals(x2, dummyEdge.getLastX());
        assertEquals(y2, dummyEdge.getLastY());
    }

    @Test
    public void addLastEmpty() {
        try {
            dummyEdge.addLast();
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("Expected no ArrayIndexOutOfBoundsException, but \"" + e.getMessage() + "\" was thrown.");
        }
    }

    @Test
    public void addLastNonEmpty() {
        int x = 42;
        int y = 49;
        dummyEdge.addLast(x, y);
        dummyEdge.addLast();
        assertEquals(x, dummyEdge.getFirstX());
        assertEquals(y, dummyEdge.getFirstY());
        assertEquals(x + 100, dummyEdge.getLastX());
        assertEquals(y, dummyEdge.getLastY());
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void getXEmpty() {
        dummyEdge.getX(0);
    }

    @Test
    public void getX() {
        int x1 = 10;
        int x2 = 21;
        int x3 = 999;
        int x4 = -512;
        int x5 = 0;
        dummyEdge.addLast(x1, 0);
        dummyEdge.addLast(x2, 0);
        dummyEdge.addLast(x3, 0);
        dummyEdge.addLast(x4, 0);
        dummyEdge.addLast(x5, 0);
        assertEquals(x1, dummyEdge.getX(0));
        assertEquals(x2, dummyEdge.getX(1));
        assertEquals(x3, dummyEdge.getX(2));
        assertEquals(x4, dummyEdge.getX(3));
        assertEquals(x5, dummyEdge.getX(4));
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void getYEmpty() {
        dummyEdge.getY(0);
    }

    @Test
    public void getY() {
        int y1 = -9;
        int y2 = 777;
        int y3 = 0;
        int y4 = -275;
        int y5 = 38;
        dummyEdge.addLast(0, y1);
        dummyEdge.addLast(100, y2);
        dummyEdge.addLast(200, y3);
        dummyEdge.addLast(300, y4);
        dummyEdge.addLast(400, y5);
        assertEquals(y1, dummyEdge.getY(0));
        assertEquals(y2, dummyEdge.getY(1));
        assertEquals(y3, dummyEdge.getY(2));
        assertEquals(y4, dummyEdge.getY(3));
        assertEquals(y5, dummyEdge.getY(4));
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void setXEmpty() {
        dummyEdge.setX(0, 100);
    }

    @Test
    public void setX() {
        int x1 = 0;
        int x2 = 100;
        dummyEdge.addFirst(x1, 0);
        int y = dummyEdge.getFirstY();
        dummyEdge.setX(0,x2);
        assertEquals(y, dummyEdge.getFirstY());
        assertEquals(x2, dummyEdge.getFirstX());
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void setYEmpty() {
        dummyEdge.setY(0, 0);
    }

    @Test
    public void setY() {
        int y1 = -1024;
        int y2 = 81;
        dummyEdge.addFirst(0, y1);
        int x = dummyEdge.getFirstX();
        dummyEdge.setY(0, y2);
        assertEquals(x, dummyEdge.getFirstX());
        assertEquals(y2, dummyEdge.getFirstY());
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void getFirstXEmpty() {
        dummyEdge.getFirstX();
    }

    @Test
    public void getFirstX() {
        int x1 = 72;
        int x2 = 99;
        dummyEdge.addFirst(x1, 0);
        dummyEdge.addLast(x2, 50);
        assertEquals(x1, dummyEdge.getFirstX());
        assertEquals(dummyEdge.getX(0), dummyEdge.getFirstX());
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void getFirstYEmpty() {
        dummyEdge.getFirstY();
    }

    @Test
    public void getFirstY() {
        int y1 = 720;
        int y2 = -1;
        dummyEdge.addLast(100, y2);
        dummyEdge.addFirst(0, y1);
        assertEquals(y1, dummyEdge.getFirstY());
        assertEquals(dummyEdge.getY(0), dummyEdge.getFirstY());
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void getLastXEmpty() {
        dummyEdge.getLastX();
    }

    @Test
    public void getLastX() {
        int x1 = 66;
        int x2 = 7;
        dummyEdge.addFirst(x2, 0);
        dummyEdge.addFirst(x1, 50);
        assertEquals(x2, dummyEdge.getLastX());
        assertEquals(dummyEdge.getX(1), dummyEdge.getLastX());
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void getLastYEmpty() {
        dummyEdge.getLastY();
    }

    @Test
    public void getLastY() {
        int y1 = 0;
        int y2 = 21;
        dummyEdge.addLast(200, y1);
        dummyEdge.addLast(300, y2);
        assertEquals(y2, dummyEdge.getLastY());
        assertEquals(dummyEdge.getY(1), dummyEdge.getLastY());
    }

    @Test
    public void getLength() {
        assertEquals(0, dummyEdge.getLength());
        dummyEdge.addFirst(0, 0);
        assertEquals(1, dummyEdge.getLength());
    }

    @Test
    public void isEmpty() {
        assertTrue(dummyEdge.isEmpty());
        dummyEdge.addLast(0, 0);
        assertFalse(dummyEdge.isEmpty());
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void traversesLayerEmpty() {
        dummyEdge.traversesLayer(0);
    }

    @Test
    public void traversesLayer() {
        int x1 = -512;
        int x2 = 1024;
        dummyEdge.addLast(x1, 0);
        dummyEdge.addLast(x2, 0);
        assertTrue(dummyEdge.traversesLayer(x1));
        assertTrue(dummyEdge.traversesLayer(x2));
        assertFalse(dummyEdge.traversesLayer(x1 - 1));
        assertFalse(dummyEdge.traversesLayer(x2 + 1));
        assertTrue(dummyEdge.traversesLayer((int) (Math.random() * (x2 - x1) + x1)));
    }

    @Test
    public void indexOfLayerEmpty() {
        assertEquals(-1, dummyEdge.indexOfLayer(0));
    }

    @Test
    public void indexOfLayer() {
        int startX = -200;
        int endX = 800;
        int y = 50;
        dummyEdge.addLast(startX, y);
        while (dummyEdge.getLastX() < endX) {
            dummyEdge.addLast();
        }
        assertEquals(0, dummyEdge.indexOfLayer(startX));
        assertEquals((endX - startX) / 100, dummyEdge.indexOfLayer(endX));
        assertEquals(-1, dummyEdge.indexOfLayer(endX + 1));
        assertEquals(-1, dummyEdge.indexOfLayer(startX - 1));
    }

    @Test
    public void setYOfLayerEmpty() {
        try {
            dummyEdge.setYOfLayer(0, 0);
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("Expected no ArrayIndexOutOfBoundsException, but \"" + e.getMessage() + "\" was thrown.");
        }
    }

    @Test
    public void setYOfLayer() {
        int x1 = 0;
        int y1 = 50;
        int x2 = 100;
        int y2 = 100;
        dummyEdge.addLast(x1, y1);
        dummyEdge.addLast(x2, y2);
        dummyEdge.setYOfLayer(x1, y2);
        assertEquals(y2, dummyEdge.getYOfLayer(x1));
        dummyEdge.setYOfLayer(x2, y1);
        assertEquals(y1, dummyEdge.getYOfLayer(x2));
    }

    @Test
    public void getYOfLayerEmpty() {
        try {
            dummyEdge.getYOfLayer(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("Expected no ArrayIndexOutOfBoundsException, but \"" + e.getMessage() + "\" was thrown.");
        }
    }

    @Test
    public void getYOfLayer() {
        int x1 = 900;
        int y1 = 75;
        int x2 = 1000;
        int y2 = 50;
        int x3 = 1100;
        int y3 = 25;
        dummyEdge.addLast(x1, y1);
        dummyEdge.addLast(x2, y2);
        dummyEdge.addLast(x3, y3);
        assertEquals(y1, dummyEdge.getYOfLayer(x1));
        assertEquals(y2, dummyEdge.getYOfLayer(x2));
        assertEquals(y3, dummyEdge.getYOfLayer(x3));
    }

    @Test
    public void removeFirstEmpty() {
        try {
            dummyEdge.removeFirst();
        } catch (NegativeArraySizeException e) {
            fail("Expected no NegativeArraySizeException, but \"" + e.getMessage() + "\" was thrown.");
        }
    }

    @Test
    public void removeFirst() {
        int x1 = 0;
        int y1 = 0;
        int x2 = 100;
        int y2 = 50;
        dummyEdge.addLast(x1, y1);
        dummyEdge.addLast(x2, y2);
        assertEquals(2, dummyEdge.getLength());
        dummyEdge.removeFirst();
        assertEquals(1, dummyEdge.getLength());
        assertEquals(x2, dummyEdge.getFirstX());
        assertEquals(y2, dummyEdge.getFirstY());
        dummyEdge.removeFirst();
        assertTrue(dummyEdge.isEmpty());
    }

    @Test
    public void removeLastEmpty() {
        try {
            dummyEdge.removeLast();
        } catch (NegativeArraySizeException e) {
            fail("Expected no NegativeArraySizeException, but \"" + e.getMessage() + "\" was thrown.");
        }
    }

    @Test
    public void removeLast() {
        int x1 = -200;
        int y1 = 100;
        int x2 = -100;
        int y2 = 50;
        dummyEdge.addLast(x1, y1);
        dummyEdge.addLast(x2, y2);
        assertEquals(2, dummyEdge.getLength());
        dummyEdge.removeLast();
        assertEquals(1, dummyEdge.getLength());
        assertEquals(x1, dummyEdge.getLastX());
        assertEquals(y1, dummyEdge.getLastY());
        dummyEdge.removeLast();
        assertTrue(dummyEdge.isEmpty());
    }

    @Test
    public void equalsOtherSame() {
        DummyEdge other = new DummyEdge(0, 1);
        assertTrue(dummyEdge.equals(other));
        assertEquals(dummyEdge.equals(other), other.equals(dummyEdge));
    }

    @Test
    public void equalsOtherWithNodes() {
        dummyEdge.addLast(0, 0);
        DummyEdge other = new DummyEdge(0, 1);
        assertTrue(dummyEdge.equals(other));
        assertEquals(dummyEdge.equals(other), other.equals(dummyEdge));
        other.addLast(1, 1);
        assertTrue(dummyEdge.equals(other));
        assertEquals(dummyEdge.equals(other), other.equals(dummyEdge));
    }

    @Test
    public void equalsOtherDifferentChild() {
        DummyEdge other = new DummyEdge(0, 2);
        assertFalse(dummyEdge.equals(other));
        assertEquals(dummyEdge.equals(other), other.equals(dummyEdge));
    }

    @Test
    public void equalsOtherDifferentParent() {
        DummyEdge other = new DummyEdge(1, 1);
        assertFalse(dummyEdge.equals(other));
        assertEquals(dummyEdge.equals(other), other.equals(dummyEdge));
    }

    @Test
    public void equalsOtherDifferent() {
        DummyEdge other = new DummyEdge(1, 2);
        assertFalse(dummyEdge.equals(other));
        assertEquals(dummyEdge.equals(other), other.equals(dummyEdge));
    }

    @Test
    public void equalsObject() {
        assertFalse(dummyEdge.equals(new Object()));
    }

    @Test
    public void hashCodeConsistency() {
        int parent = (int) (Math.random() * 2048) - 1024;
        int child = (int) (Math.random() * 2048) - 1024;
        assertEquals(new DummyEdge(parent, child).hashCode(), new DummyEdge(parent, child).hashCode());
    }

    @Test
    public void hashCodeOtherWithNodes() {
        DummyEdge other = new DummyEdge(0, 1);
        other.addLast(255, -512);
        assertEquals(dummyEdge.hashCode(), other.hashCode());
        dummyEdge.addLast(-1024, 32);
        assertEquals(other.hashCode(), dummyEdge.hashCode());
    }
}

package datastructure;


import java.util.Random;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by 101010.
 */
public class DummyNodeTest {
    private DummyNode dummyNode;
    private static Random random;
    private int id;
    private int from;
    private int to;
    private int x;
    private int y;

    @BeforeClass
    public static void beforeAll() {
        random = new Random();
    }

    @AfterClass
    public static void afterAll() {
        random = null;
    }

    @Before
    public void setUp() {
        id = random.nextInt();
        from = Math.abs(random.nextInt());
        to = Math.abs(random.nextInt());
        x = random.nextInt();
        y = random.nextInt();
        dummyNode = new DummyNode(id, from, to, x, y);
    }

    @After
    public void tearDown() {
        dummyNode = null;
    }

    @Test
    public void getId() {
        assertEquals(id, dummyNode.getId());
    }

    @Test
    public void getAbsId() {
        assertEquals(Double.parseDouble(Integer.toString(id) + Integer.toString(from) +Integer.toString(to)), dummyNode.getAbsId(), 0);
        DummyNode node = new DummyNode(1,2,3,4,5);
        assertEquals(123, node.getAbsId(), 0);
    }
    @Test
    public void getFrom() {
        assertEquals(from, dummyNode.getFrom());
    }

    @Test
    public void setFrom() {
        int newFrom = random.nextInt();
        dummyNode.setFrom(newFrom);
        assertEquals(newFrom, dummyNode.getFrom());
    }

    @Test
    public void getTo() {
        assertEquals(to, dummyNode.getTo());
    }

    @Test
    public void setTo() {
        int newTo = random.nextInt();
        dummyNode.setTo(newTo);
        assertEquals(newTo, dummyNode.getTo());
    }

    @Test
    public void getX() {
        assertEquals(x, dummyNode.getX());
    }

    @Test
    public void setX() {
        int newX = random.nextInt();
        dummyNode.setX(newX);
        assertEquals(newX, dummyNode.getX());
    }

    @Test
    public void getY() {
        assertEquals(y, dummyNode.getY());
    }

    @Test
    public void setY() {
        int newY = random.nextInt();
        dummyNode.setY(newY);
        assertEquals(newY, dummyNode.getY());
    }

    @Test
    public void nextInEdge() {
        DummyNode temp = new DummyNode(1,2,3,4,5);
        dummyNode = new DummyNode(0,2,3, random.nextInt(), random.nextInt());
        assertTrue(dummyNode.nextInEdge(temp));
        temp  = new DummyNode(2,2,3,4,5);
        assertFalse(dummyNode.nextInEdge(temp));
        temp  = new DummyNode(1,3,3,4,5);
        assertFalse(dummyNode.nextInEdge(temp));
        temp  = new DummyNode(1,2,2,4,5);
        assertFalse(dummyNode.nextInEdge(temp));
    }

    @Test
    public void prevInEdge() {
        DummyNode temp = new DummyNode(0, 1, 2,3 , 4);
        DummyNode temp2 = new DummyNode(-1, 1, 2,3  - 100, 4);
        assertEquals(temp2, temp.prevInEdge());
    }

    @Test
    public void hashcode() {
        assertEquals(((dummyNode.getId() * 31) + dummyNode.getFrom()) * 31 + dummyNode.getTo(), dummyNode.hashCode());
    }

    @Test
    public void equals() {
        DummyNode temp = new DummyNode(dummyNode.getId(), dummyNode.getFrom(), dummyNode.getTo(), dummyNode.getX(), dummyNode.getY());
        assertTrue(dummyNode.equals(temp));
        temp = new DummyNode(dummyNode.getId(), dummyNode.getFrom(), dummyNode.getTo() + 1, dummyNode.getX(), dummyNode.getY());
        assertFalse(dummyNode.equals(temp));
        temp = new DummyNode(dummyNode.getId(), dummyNode.getFrom() + 1, dummyNode.getTo(), dummyNode.getX(), dummyNode.getY());
        assertFalse(dummyNode.equals(temp));
        temp = new DummyNode(dummyNode.getId() + 1, dummyNode.getFrom(), dummyNode.getTo(), dummyNode.getX(), dummyNode.getY());
        assertFalse(dummyNode.equals(temp));
    }
}

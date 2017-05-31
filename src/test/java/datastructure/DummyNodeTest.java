package datastructure;


import java.util.Random;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import static org.junit.Assert.assertEquals;


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
        from = random.nextInt();
        to = random.nextInt();
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
    public void testGetFrom() {
        assertEquals(from, dummyNode.getFrom());
    }

    @Test
    public void testSetFrom() {
        int newFrom = random.nextInt();
        dummyNode.setFrom(newFrom);
        assertEquals(newFrom, dummyNode.getFrom());
    }

    @Test
    public void testGetTo() {
        assertEquals(to, dummyNode.getTo());
    }

    @Test
    public void testSetTo() {
        int newTo = random.nextInt();
        dummyNode.setTo(newTo);
        assertEquals(newTo, dummyNode.getTo());
    }

    @Test
    public void testGetX() {
        assertEquals(x, dummyNode.getX());
    }

    @Test
    public void testSetX() {
        int newX = random.nextInt();
        dummyNode.setX(newX);
        assertEquals(newX, dummyNode.getX());
    }

    @Test
    public void testGetY() {
        assertEquals(y, dummyNode.getY());
    }

    @Test
    public void testSetY() {
        int newY = random.nextInt();
        dummyNode.setY(newY);
        assertEquals(newY, dummyNode.getY());
    }
}

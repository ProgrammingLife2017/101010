package datastructure;

import org.junit.jupiter.api.*;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by 101010.
 */
public class DummyNodeTest {
    private DummyNode dummyNode;
    private static Random random;
    private int id;
    private int from;
    private int to;

    @BeforeAll
    static void beforeAll() {
        random = new Random();
    }

    @AfterAll
    static void afterAll() {
        random = null;
    }

    @BeforeEach
    void setUp() {
        id = random.nextInt();
        from = random.nextInt();
        to = random.nextInt();
        dummyNode = new DummyNode(id, from, to);
    }

    @AfterEach
    void tearDown() {
        dummyNode = null;
    }

    @Test
    void testGetFrom() {
        assertEquals(from, dummyNode.getFrom());
    }

    @Test
    void testSetFrom() {
        int newFrom = random.nextInt();
        dummyNode.setFrom(newFrom);
        assertEquals(newFrom, dummyNode.getFrom());
    }

    @Test
    void testGetTo() {
        assertEquals(to, dummyNode.getTo());
    }

    @Test
    void testSetTo() {
        int newTo = random.nextInt();
        dummyNode.setTo(newTo);
        assertEquals(newTo, dummyNode.getTo());
    }
}

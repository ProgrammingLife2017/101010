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
public class DrawNodeTest {
    private DrawNode drawNode;

    @Before
    public void setUp() {
        drawNode = new DrawNode(0, 100);
    }

    @After
    public void tearDown() {
        drawNode = null;
    }

    @Test
    public void constructorConsistency() {
        DrawNode drawNode1 = new DrawNode(0);
        DrawNode drawNode2 = new DrawNode(0, 0);
        assertEquals(drawNode1.getLayer(), drawNode2.getLayer());
        assertEquals(drawNode1, drawNode2);
    }

    @Test
    public void constructorXInit() {
        assertEquals(100.0, drawNode.getX(), 0.0);
    }

    @Test
    public void getIndex() {
        assertEquals(0, drawNode.getIndex());
    }

    @Test
    public void getLayer() {
        assertEquals(100, drawNode.getLayer());
    }

    @Test
    public void setLayerSame() {
        double x = drawNode.getX();
        drawNode.setLayer(100);
        assertEquals(x, drawNode.getX(), 0.0);
    }

    @Test
    public void setLayer() {
        drawNode.setLayer(200);
        assertEquals(200.0, drawNode.getX(), 0.0);
        assertEquals(200, drawNode.getLayer());
    }

    @Test
    public void moveRightOffset() {
        drawNode.moveRight(1);
        assertEquals(101.0, drawNode.getX(), 0.0);
        assertEquals(101, drawNode.getLayer());
    }

    @Test
    public void moveRight() {
        DrawNode drawNode1 = new DrawNode(0, 100);
        drawNode.moveRight();
        drawNode1.moveRight(100);
        assertEquals(200.0, drawNode.getX(), 0.0);
        assertEquals(200, drawNode.getLayer());
        assertEquals(drawNode1.getLayer(), drawNode.getLayer());
    }

    @Test
    public void moveLeftOffset() {
        drawNode.moveLeft(9);
        assertEquals(91.0, drawNode.getX(), 0.0);
        assertEquals(91, drawNode.getLayer());
    }

    @Test
    public void moveLeft() {
        DrawNode drawNode1 = new DrawNode(0, 100);
        drawNode.moveLeft();
        drawNode1.moveLeft(100);
        assertEquals(0.0, drawNode.getX(), 0.0);
        assertEquals(0, drawNode.getLayer());
        assertEquals(drawNode1.getLayer(), drawNode.getLayer());
    }

    @Test
    public void equalsSame() {
        DrawNode drawNode1 = new DrawNode(0, 100);
        assertTrue(drawNode.equals(drawNode1));
        assertTrue(drawNode1.equals(drawNode));
    }

    @Test
    public void equalsOtherLayer() {
        DrawNode drawNode1 = new DrawNode(0);
        assertTrue(drawNode.equals(drawNode1));
        assertTrue(drawNode1.equals(drawNode));
    }

    @Test
    public void equalsOtherIndex() {
        DrawNode drawNode1 = new DrawNode(1, 100);
        assertFalse(drawNode.equals(drawNode1));
        assertFalse(drawNode1.equals(drawNode));
    }

    @Test
    public void equalsOtherDifferent() {
        DrawNode drawNode1 = new DrawNode(1);
        assertFalse(drawNode.equals(drawNode1));
        assertFalse(drawNode1.equals(drawNode));
    }

    @Test
    public void equalsObject() {
        assertFalse(drawNode.equals(new Object()));
    }

    @Test
    public void hashCodeConsistency() {
        assertEquals(drawNode.hashCode(), drawNode.hashCode());
    }

    @Test
    public void hashCodeSame() {
        DrawNode drawNode1 = new DrawNode(0, 100);
        assertEquals(drawNode.hashCode(), drawNode1.hashCode());
    }

    @Test
    public void hashCodeOtherLayer() {
        DrawNode drawNode1 = new DrawNode(0);
        assertEquals(drawNode.hashCode(), drawNode1.hashCode());
    }
}
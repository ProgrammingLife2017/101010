package datastructure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by 101010.
 */
public class NodeGraphTest {
    /**
     * The NodeGraph used to test.
     */
    private NodeGraph nodeGraph = null;

    @Mock
    ArrayList<Node> nodes;

    @Mock
    LinkedList<DrawNode> drawNodes;

    @Mock
    SegmentDB segmentDB;

    @Mock
    Node node;

    @Mock
    LinkedList<DummyNode> dummyNodes;

    @Mock
    DrawNode drawNode;

    @Mock
    DummyNode dummyNode;

    @Mock
    Iterator<DrawNode> iterator;

    @Mock
    Iterator<DrawNode> riterator;

    /**
     * Before each test we set the nodeGraph to a new NodeGraph.
     */
    @Before
    public void setUp() {
        nodes = mock(new ArrayList<Node>().getClass());
        drawNodes = mock(new LinkedList<DrawNode>().getClass());
        segmentDB = mock(SegmentDB.class);
        node = mock(Node.class);
        drawNode = mock(DrawNode.class);
        iterator = mock(Iterator.class);
        riterator = mock(Iterator.class);
        dummyNodes = mock(new LinkedList<DummyNode>().getClass());
        when(node.getIncomingEdges()).thenReturn(new int[0]);
        when(node.getOutgoingEdges()).thenReturn(new int[0]);
        when(segmentDB.getSegment(anyInt())).thenReturn("Segment");
        when(nodes.get(anyInt())).thenReturn(node);
        when(drawNodes.get(anyInt())).thenReturn(drawNode);
        when(drawNode.getIndex()).thenReturn(0);
        when(drawNodes.iterator()).thenReturn(iterator);
        when(drawNodes.descendingIterator()).thenReturn(riterator);
        when(iterator.next()).thenReturn(drawNode);
        when(iterator.hasNext()).thenReturn(true).thenReturn(false);
        when(riterator.next()).thenReturn(drawNode);
        when(riterator.hasNext()).thenReturn(true).thenReturn(false);
        nodeGraph = new NodeGraph(nodes, segmentDB, drawNodes, dummyNodes);
    }

    /**
     * After each test we set the nodeGraph back to null.
     */
    @After
    public void tearDown() {
        nodeGraph.setCurrentInstance(null);
        nodeGraph = null;
        nodes = null;
        drawNodes = null;
        segmentDB = null;
        node = null;
    }

    @Test
    public void addNode() {
        nodeGraph = new NodeGraph(new ArrayList<Node>(0), segmentDB, drawNodes, new LinkedList<>());
        nodeGraph.addNode(100, node);
        assertEquals(101, nodeGraph.getSize());
        assertEquals(node, nodeGraph.getNode(100));
        verify(node, times(1)).computeLength();
        verify(node, times(1)).setIncomingEdges(any());
    }

    @Test
    public void addNodeCache() {
        nodeGraph = new NodeGraph(new ArrayList<>(0), segmentDB, drawNodes, new LinkedList<>());
        nodeGraph.addNodeCache(100, node);
        assertEquals(101, nodeGraph.getSize());
        assertEquals(node, nodeGraph.getNode(100));
        verify(node, never()).computeLength();
        verify(node, never()).setIncomingEdges(any());
    }

    @Test
    public void addEdge() {
        nodeGraph = new NodeGraph(new ArrayList<>(0), segmentDB, drawNodes, new LinkedList<>());
        Node node2 = mock(Node.class);

        nodeGraph.addNode(0, node);
        nodeGraph.addNode(1, node2);
        nodeGraph.addEdge(0, 1);

        verify(node, times(1)).addOutgoingEdge(1);
        verify(node, never()).addIncomingEdge(anyInt());
        verify(node2, never()).addOutgoingEdge(anyInt());
        verify(node2, times(1)).addIncomingEdge(0);
    }

    @Test
    public void getSegment() {
        int r = new Random().nextInt();
        assertEquals("Segment", nodeGraph.getSegment(r));
        verify(segmentDB, times(1)).getSegment(r);
    }

    @Test
    public void getNode() {
        assertEquals(node, nodeGraph.getNode(0));
        verify(nodes, times(1)).get(0);
    }

    @Test
    public void getCurrentInstance() {
        assertNull(nodeGraph.getCurrentInstance());
    }

    @Test
    public void setCurrentInstance() {
        nodeGraph.setCurrentInstance(nodeGraph);
        assertEquals(nodeGraph.getDrawNodes(), nodeGraph.getCurrentInstance().getDrawNodes());
        assertEquals(nodeGraph.getNodes(), nodeGraph.getCurrentInstance().getNodes());
        assertEquals(nodeGraph.getSegment(0), nodeGraph.getCurrentInstance().getSegment(0));
    }

    @Test
    public void getSize() {
        assertEquals(nodes.size(), nodeGraph.getSize());
        verify(nodes, atLeastOnce()).size();
    }

    @Test
    public void setSegmentDB() {
        SegmentDB s = mock(SegmentDB.class);
        when(s.getSegment(anyInt())).thenReturn("otherSegment");
        assertEquals("Segment", nodeGraph.getSegment(0));
        nodeGraph.setSegmentDB(s);
        assertEquals("otherSegment", nodeGraph.getSegment(0));
    }

    @Test
    public void getNodes() {
        assertEquals(nodes, nodeGraph.getNodes());
    }

    @Test
    public void generateDrawNodes() {
        ArrayList<Node> nodes2 = mock(new ArrayList<Node>().getClass());
        when(nodes2.size()).thenReturn(1);
        when(nodes2.get(anyInt())).thenReturn(node);
        nodeGraph = new NodeGraph(nodes2, segmentDB, new LinkedList<>(), new LinkedList<>());
        nodeGraph.generateDrawNodes(0, 500);
        assertEquals(1, nodeGraph.getDrawNodes().size());
        verify(node, atLeastOnce()).getIncomingEdges();
        verify(node, atLeastOnce()).getOutgoingEdges();
        assertEquals(0, nodeGraph.getDrawNodes().get(0).getIndex());
    }

    @Test
    public void getDrawNodes() {
        assertEquals(drawNodes, nodeGraph.getDrawNodes());
    }


    @Test
    public void getDrawNode() {
        assertEquals(drawNode, nodeGraph.getDrawNode(0));
    }

    @Test
    public void getDummyNodes() {
        assertEquals(dummyNodes, nodeGraph.getDummyNodes());
    }

    @Test
    public void addEdges() {

    }

    @Test
    public void topoSort() {

    }

    @Test
    public void topoSortUtil() {

    }

    @Test
    public void assignLayers() {

    }

    @Test
    public void computeDummyNodes() {
        DrawNode dN1 = mock(DrawNode.class);
        when(dN1.getIndex()).thenReturn(0);
        dN1.setX(900.0);
        DrawNode dN2 = mock(DrawNode.class);
        when(dN2.getIndex()).thenReturn(1);
        dN2.setX(1000.0);
        DrawNode dN3 = mock(DrawNode.class);
        when(dN3.getIndex()).thenReturn(2);
        dN3.setX(1100.0);
        DrawNode dN4 = mock(DrawNode.class);
        when(dN4.getIndex()).thenReturn(3);
        dN4.setX(1200.0);
        Node n1 = mock(Node.class);
        when(n1.getIncomingEdges()).thenReturn(new int[0]);
        when(n1.getOutgoingEdges()).thenReturn(new int[] {1, 3});
        Node n2 = mock(Node.class);
        when(n2.getIncomingEdges()).thenReturn(new int[] {0});
        when(n2.getOutgoingEdges()).thenReturn(new int[] {2});
        Node n3 = mock(Node.class);
        when(n3.getIncomingEdges()).thenReturn(new int[] {1});
        when(n3.getOutgoingEdges()).thenReturn(new int[] {3});
        Node n4 = mock(Node.class);
        when(n4.getIncomingEdges()).thenReturn(new int[] {0, 2});
        when(n4.getOutgoingEdges()).thenReturn(new int[0]);
        ListIterator<DrawNode> lit = mock(ListIterator.class);
        when(lit.hasNext()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(lit.next()).thenReturn(dN4).thenReturn(dN3).thenReturn(dN2).thenReturn(dN1);
        ArrayList<Node> n = mock(new ArrayList<>().getClass());
        when(n.get(0)).thenReturn(n1);
        when(n.get(1)).thenReturn(n2);
        when(n.get(2)).thenReturn(n3);
        when(n.get(3)).thenReturn(n4);
        when(n.size()).thenReturn(4);
        LinkedList<DrawNode> drawN = mock(new LinkedList<>().getClass());
        when(drawN.get(0)).thenReturn(dN4);
        when(drawN.get(1)).thenReturn(dN3);
        when(drawN.get(2)).thenReturn(dN2);
        when(drawN.get(3)).thenReturn(dN1);
        when(drawN.size()).thenReturn(4);
        when(drawN.listIterator()).thenReturn(lit);
        LinkedList<DummyNode> dummyN = mock(new LinkedList<>().getClass());
        NodeGraph nG = new NodeGraph(n, segmentDB, drawN, dummyN);
        nG.generateDrawNodes(0, 4);
        dummyN = new LinkedList<>();
        dummyN.addLast(new DummyNode(-1, 0, 3, 1100, 50));
        dummyN.addLast(new DummyNode(-2, 0, 3, 1000, 50));
        assertEquals(dummyN, nG.getDummyNodes());
    }

    @Test
    public void verticalSpacing() {
        LinkedList<DummyNode> dummies = new LinkedList<>();
        LinkedList<DrawNode> drawies = new LinkedList<>();
        DrawNode drawNode1 = mock(DrawNode.class);
        DrawNode drawNode2 = mock(DrawNode.class);
        DummyNode dummyNode = mock(DummyNode.class);
        DummyNode dummyNode1 = mock(DummyNode.class);
        drawNode.setX(100);
        drawNode1.setX(200);
        drawNode2.setX(200);
        when(dummyNode.getX()).thenReturn(200);
        when(dummyNode1.getX()).thenReturn(200);
        when(dummyNode.getY()).thenReturn(50);
        when(dummyNode1.getY()).thenReturn(100);
        dummies.add(dummyNode);
        dummies.add(dummyNode1);
        drawies.add(drawNode);
        drawies.add(drawNode1);
        drawies.add(drawNode2);
        nodeGraph = new NodeGraph(nodes, segmentDB, drawies, dummies);
        Class[] classes = new Class[0];
        try {
            Method method = NodeGraph.class.getDeclaredMethod("verticalSpacing", classes);
            method.setAccessible(true);
            method.invoke(nodeGraph);
            assertEquals(50.0, drawNode.getY(), 0);
            assertEquals(50.0, dummyNode.getY(), 0);
            assertEquals(100.0, dummyNode1.getY(), 0);
            assertEquals(150.0, drawNode1.getY(), 0);
            assertEquals(200.0, drawNode2.getY(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void retrieveDrawNodes() {
        DrawNode drawNode1 = mock(DrawNode.class);
        DrawNode drawNode2 = mock(DrawNode.class);
        drawNode.setX(100.0);
        drawNode1.setX(100.0);
        drawNode2.setX(0.0);
        Iterator<DummyNode> it = mock(Iterator.class);
        Iterator<DummyNode> rit = mock(Iterator.class);
        when(dummyNodes.iterator()).thenReturn(it);
        when(dummyNodes.descendingIterator()).thenReturn(rit);
        when(it.hasNext()).thenReturn(false);
        when(rit.hasNext()).thenReturn(false);
        when(drawNodes.getFirst()).thenReturn(drawNode);
        when(drawNodes.getLast()).thenReturn(drawNode2);
        when(drawNode.getIndex()).thenReturn(0);
        when(drawNode1.getIndex()).thenReturn(1);
        when(drawNode2.getIndex()).thenReturn(2);
        when(iterator.hasNext()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(riterator.hasNext()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(iterator.next()).thenReturn(drawNode).thenReturn(drawNode1).thenReturn(drawNode2);
        when(riterator.next()).thenReturn(drawNode2).thenReturn(drawNode1).thenReturn(drawNode);
        nodeGraph = new NodeGraph(nodes, segmentDB, drawNodes, dummyNodes);
        Class[] classes = new Class[0];
        try {
            Method method = NodeGraph.class.getDeclaredMethod("retrieveEdgeNodes", classes);
            method.setAccessible(true);
            method.invoke(nodeGraph);
            assertTrue(nodeGraph.getLeafNodes().contains(0.0));
            assertTrue(nodeGraph.getLeafNodes().contains(1.0));
            assertFalse(nodeGraph.getLeafNodes().contains(2.0));
            assertTrue(nodeGraph.getRootNodes().contains(2.0));
            assertFalse(nodeGraph.getRootNodes().contains(1.0));
            assertFalse(nodeGraph.getRootNodes().contains(0.0));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void retrieveDummies() {
        dummyNode = mock(DummyNode.class);
        DrawNode drawNode1 = mock(DrawNode.class);
        DummyNode dummyNode1 = mock(DummyNode.class);
        drawNode.setX(100.0);
        drawNode1.setX(0.0);
        Iterator<DummyNode> it = mock(Iterator.class);
        Iterator<DummyNode> rit = mock(Iterator.class);
        when(dummyNodes.iterator()).thenReturn(it);
        when(dummyNodes.descendingIterator()).thenReturn(rit);
        when(dummyNode.getX()).thenReturn(100);
        when(dummyNode.getAbsId()).thenReturn(-1.0);
        when(dummyNode1.getX()).thenReturn(0);
        when(dummyNode1.getAbsId()).thenReturn(-2.0);
        when(it.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(it.next()).thenReturn(dummyNode).thenReturn(dummyNode1);
        when(rit.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rit.next()).thenReturn(dummyNode1).thenReturn(dummyNode);
        when(drawNodes.getFirst()).thenReturn(drawNode);
        when(drawNodes.getLast()).thenReturn(drawNode1);
        nodeGraph = new NodeGraph(nodes, segmentDB, drawNodes, dummyNodes);
        Class[] classes = new Class[0];
        try {
            Method method = NodeGraph.class.getDeclaredMethod("retrieveEdgeNodes", classes);
            method.setAccessible(true);
            method.invoke(nodeGraph);
            assertTrue(nodeGraph.getLeafNodes().contains(-1.0));
            assertFalse(nodeGraph.getRootNodes().contains(-1.0));
            assertFalse(nodeGraph.getLeafNodes().contains(-2.0));
            assertTrue(nodeGraph.getRootNodes().contains(-2.0));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}

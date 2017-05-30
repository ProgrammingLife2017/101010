package datastructure;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

/**
 * Created by 101010.
 */
class NodeGraphTest {
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

    /**
     * Before each test we set the nodeGraph to a new NodeGraph.
     */
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        nodes = mock(new ArrayList<Node>().getClass());
        drawNodes = mock(new LinkedList<DrawNode>().getClass());
        segmentDB = mock(SegmentDB.class);
        node = mock(Node.class);
        when(node.getIncomingEdges()).thenReturn(new int[0]);
        when(node.getOutgoingEdges()).thenReturn(new int[0]);
        when(segmentDB.getSegment(anyInt())).thenReturn("Segment");
        when(nodes.get(anyInt())).thenReturn(node);
        nodeGraph = new NodeGraph(nodes, segmentDB, drawNodes, new LinkedList<>());
    }

    /**
     * After each test we set the nodeGraph back to null.
     */
    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        nodeGraph.setCurrentInstance(null);
        nodeGraph = null;
        nodes = null;
        drawNodes = null;
        segmentDB = null;
        node = null;
    }

    @Test
    void testGetNodes() {
        assertEquals(nodes, nodeGraph.getNodes());
    }

    @Test
    void testGetDrawNodes() {
        assertEquals(drawNodes, nodeGraph.getDrawNodes());
    }

    @Test
    void testSetSegment() {
        nodeGraph = new NodeGraph();
        nodeGraph.setSegmentDB(segmentDB);
        nodeGraph.getSegment(0);
        verify(segmentDB, times(1)).getSegment(0);
    }

    @Test
    void testGetSegment() {
        int r = new Random().nextInt();
        assertEquals("Segment", nodeGraph.getSegment(r));
        verify(segmentDB, times(1)).getSegment(r);
    }

    @Test
    void testAddNode() {
        nodeGraph = new NodeGraph(new ArrayList<Node>(0), segmentDB, drawNodes, new LinkedList<>());
        nodeGraph.addNode(100, node);
        assertEquals(101, nodeGraph.getSize());
        assertEquals(node, nodeGraph.getNode(100));
        verify(node, times(1)).computeLength();
        verify(node, times(1)).setIncomingEdges(any());
    }

    @Test
    void testAddNodeCache() {
        nodeGraph = new NodeGraph(new ArrayList<>(0), segmentDB, drawNodes, new LinkedList<>());
        nodeGraph.addNodeCache(100, node);
        assertEquals(101, nodeGraph.getSize());
        assertEquals(node, nodeGraph.getNode(100));
        verify(node, never()).computeLength();
        verify(node, never()).setIncomingEdges(any());
    }

    @Test
    void testAddEdge() {
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
    void testGetSize() {
        assertEquals(nodes.size(), nodeGraph.getSize());
        verify(nodes, atLeastOnce()).size();
    }

    @Test
    void testGetNode() {
        assertEquals(node, nodeGraph.getNode(0));
        verify(nodes, times(1)).get(0);
    }

    @Test
    void testGetCurrentInstance() {
        assertNull(nodeGraph.getCurrentInstance());
    }

    @Test
    void testSetCurrentInstance() {
        nodeGraph.setCurrentInstance(nodeGraph);
        assertEquals(nodeGraph.getDrawNodes(), nodeGraph.getCurrentInstance().getDrawNodes());
        assertEquals(nodeGraph.getNodes(), nodeGraph.getCurrentInstance().getNodes());
        assertEquals(nodeGraph.getSegment(0), nodeGraph.getCurrentInstance().getSegment(0));
    }

    @Test
    void testSetSegmentDB() {
        SegmentDB s = mock(SegmentDB.class);
        when(s.getSegment(anyInt())).thenReturn("otherSegment");
        assertEquals("Segment", nodeGraph.getSegment(0));
        nodeGraph.setSegmentDB(s);
        assertEquals("otherSegment", nodeGraph.getSegment(0));
    }

    @Test
    void testGenerateDrawNodes() {
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
}

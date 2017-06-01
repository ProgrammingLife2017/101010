package datastructure;

import java.lang.reflect.Method;
import java.util.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import org.mockito.Mock;
import sun.awt.image.ImageWatched;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
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
    Iterator<DrawNode> iterator;

    @Mock
    Queue<Integer> queue;

    @Mock
    LinkedList<DrawNode> sorted;

    /**
     * Before each test we set the nodeGraph to a new NodeGraph.
     */
    @Before
    public void setUp() {
        nodes = mock(new ArrayList<Node>().getClass());
        drawNodes = mock(new LinkedList<DrawNode>().getClass());
        segmentDB = mock(SegmentDB.class);
        node = mock(Node.class);
        queue = mock(Queue.class);
        drawNode = mock(DrawNode.class);
        iterator = mock(Iterator.class);
        sorted = mock(LinkedList.class);
        dummyNodes = mock(new LinkedList<DummyNode>().getClass());
        when(segmentDB.getSegment(anyInt())).thenReturn("Segment");
        when(nodes.get(anyInt())).thenReturn(node);
        when(drawNode.getIndex()).thenReturn(0);
        when(drawNodes.iterator()).thenReturn(iterator);
        nodeGraph = new NodeGraph(nodes, segmentDB, drawNodes, dummyNodes);
    }

    /**
     * After each test we set the nodeGraph back to null.
     */
    @After
    public void tearDown() {
        validateMockitoUsage();
        nodeGraph.setCurrentInstance(null);
        nodeGraph = null;
        nodes = null;
        drawNodes = null;
        segmentDB = null;
        node = null;
        queue = null;
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

        when(node.getIncomingEdges()).thenReturn(new int[]{1});
        when(node.getOutgoingEdges()).thenReturn(new int[]{-1});
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
        when(iterator.hasNext()).thenReturn(true).thenReturn(false);
        when(iterator.next()).thenReturn(drawNode);
        assertEquals(drawNode, nodeGraph.getDrawNode(0));
    }

    @Test
    public void getDummyNodes() {
        assertEquals(dummyNodes, nodeGraph.getDummyNodes());
    }

    @Test
    public void addEdges() {
        TreeSet<Integer> set = new TreeSet<>();

        when(node.getIncomingEdges()).thenReturn(new int[]{1});
        when(node.getOutgoingEdges()).thenReturn(new int[]{-1});
        Class[] classes = new Class[]{int.class, Queue.class, TreeSet.class};
        try {
            Method method = NodeGraph.class.getDeclaredMethod("addEdges", classes);
            method.setAccessible(true);
            method.invoke(nodeGraph, 0, queue, set);
            verify(queue, times(1)).add(-1);
            verify(queue, times(1)).add(1);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void topoSort() {
        when(drawNodes.get(anyInt())).thenReturn(drawNode);
        when(drawNodes.getLast()).thenReturn(drawNode);
        when(drawNodes.getFirst()).thenReturn(drawNode);
        when(iterator.next()).thenReturn(drawNode);
        when(drawNode.getIndex()).thenReturn(1);
        when(drawNodes.isEmpty()).thenReturn(false).thenReturn(true);
        when(node.getIncomingEdges()).thenReturn(new int[]{1});
        when(node.getOutgoingEdges()).thenReturn(new int[]{-1});
        try {
            Method method = NodeGraph.class.getDeclaredMethod("topoSort", new Class[0]);
            method.setAccessible(true);
            method.invoke(nodeGraph);
            verify(drawNodes, times(1)).remove(drawNode);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void topoSortUtil() {
        DrawNode drawNode1 = mock(DrawNode.class);
        DrawNode drawNode2 = mock(DrawNode.class);
        DrawNode drawNode3 = mock(DrawNode.class);
        when(iterator.next()).thenReturn(drawNode3).thenReturn(drawNode2).thenReturn(drawNode1).thenReturn(drawNode);
        when(iterator.hasNext()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(drawNode.getIndex()).thenReturn(0);
        when(drawNode1.getIndex()).thenReturn(1);
        when(drawNode2.getIndex()).thenReturn(2);
        when(drawNode3.getIndex()).thenReturn(3);
        when(drawNodes.get(0)).thenReturn(drawNode);
        when(drawNodes.get(1)).thenReturn(drawNode1);
        when(drawNodes.get(2)).thenReturn(drawNode2);
        when(drawNodes.get(3)).thenReturn(drawNode3);

        Node node1 = mock(Node.class);
        Node node2 = mock(Node.class);
        Node node3 = mock(Node.class);
        when(nodes.get(0)).thenReturn(node);
        when(nodes.get(1)).thenReturn(node1);
        when(nodes.get(2)).thenReturn(node2);
        when(nodes.get(3)).thenReturn(node3);
        when(drawNodes.iterator()).thenReturn(iterator);

        when(node.getOutgoingEdges()).thenReturn(new int[]{1});
        when(node1.getOutgoingEdges()).thenReturn(new int[]{2, 3});
        when(node2.getOutgoingEdges()).thenReturn(new int[0]);
        when(node3.getOutgoingEdges()).thenReturn(new int[0]);

        when(iterator.next()).thenReturn(drawNode);
        Class[] classes = new Class[]{DrawNode.class, LinkedList.class};

        when(node.getIncomingEdges()).thenReturn(new int[]{1});
        when(node.getOutgoingEdges()).thenReturn(new int[]{-1});
        when(drawNode.getIndex()).thenReturn(1);
        try {
            Method method = NodeGraph.class.getDeclaredMethod("topoSortUtil", classes);
            method.setAccessible(true);
            method.invoke(nodeGraph, drawNode, sorted);
            verify(drawNodes, times(1)).remove(drawNode);
            verify(sorted, times(1)).addLast(drawNode);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void assignLayers() {
        DrawNode drawNode1 = mock(DrawNode.class);
        DrawNode drawNode2 = mock(DrawNode.class);
        DrawNode drawNode3 = mock(DrawNode.class);
        when(iterator.next()).thenReturn(drawNode3).thenReturn(drawNode2).thenReturn(drawNode1).thenReturn(drawNode);
        when(iterator.hasNext()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(drawNode.getIndex()).thenReturn(0);
        when(drawNode1.getIndex()).thenReturn(1);
        when(drawNode2.getIndex()).thenReturn(2);
        when(drawNode3.getIndex()).thenReturn(3);
        when(drawNodes.get(0)).thenReturn(drawNode);
        when(drawNodes.get(1)).thenReturn(drawNode1);
        when(drawNodes.get(2)).thenReturn(drawNode2);
        when(drawNodes.get(3)).thenReturn(drawNode3);

        Node node1 = mock(Node.class);
        Node node2 = mock(Node.class);
        Node node3 = mock(Node.class);
        when(nodes.get(0)).thenReturn(node);
        when(nodes.get(1)).thenReturn(node1);
        when(nodes.get(2)).thenReturn(node2);
        when(nodes.get(3)).thenReturn(node3);
        when(drawNodes.iterator()).thenReturn(iterator);

        when(node.getOutgoingEdges()).thenReturn(new int[]{1});
        when(node1.getOutgoingEdges()).thenReturn(new int[]{2, 3});
        when(node2.getOutgoingEdges()).thenReturn(new int[0]);
        when(node3.getOutgoingEdges()).thenReturn(new int[0]);
        drawNode.setX(9999999.0);
        drawNode1.setX(99999.0);
        drawNode2.setX(199999.0);
        drawNode3.setX(1009999.0);
        when(nodes.size()).thenReturn(4);
        when(drawNodes.size()).thenReturn(4);

        try {
            Method method = NodeGraph.class.getDeclaredMethod("assignLayers", new Class[0]);
            method.setAccessible(true);
            method.invoke(nodeGraph);
            assertEquals(900.0, drawNode.getX(), 0);
            assertEquals(1000.0, drawNode1.getX(), 0);
            assertEquals(1100.0, drawNode2.getX(), 0);
            assertEquals(1100.0, drawNode3.getX(), 0);
//            verify(iterator).next();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void computeDummyNodes() {

    }

    @Test
    public void verticalSpacing() {

    }

    @Test
    public void retrieveEdgeNodes() {

    }

    @Test
    public void retrieveDrawNodes() {

    }

    @Test
    public void retrieveDummies() {

    }

}

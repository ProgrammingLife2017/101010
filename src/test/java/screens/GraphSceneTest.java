package screens;

import datastructure.DrawNode;
import datastructure.Node;
import datastructure.NodeGraph;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.swing.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Tests the GraphScene class.
 */
@RunWith(MockitoJUnitRunner.class)
public class GraphSceneTest {

    @Mock
    Stage stage;

    @Mock
    Label label;

    Group group = mock(Group.class);

    @Mock
    Scene scene;

    FXElementsFactory fact = mock(FXElementsFactory.class);

    @Mock
    ObservableList list = mock(ObservableList.class);

    GraphInfo gi = new GraphInfo();

    ArrayList<Node> nodes = new ArrayList<>();

    NodeGraph ngTest = new NodeGraph();

    GraphScene gs;

    /**
     * Initialize the JavaFX toolkit, so its services can be tested.
     * @throws InterruptedException that triggers when the drawing is interrupted.
     */
    @BeforeClass
    public static void initToolkit() throws InterruptedException
    {
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            new JFXPanel();
            latch.countDown();
        });
        if (!latch.await(5L, TimeUnit.SECONDS))
            throw new ExceptionInInitializerError();
    }

    @Before
    public void setUp() {
        Node n1 = new Node();
        n1.addOutgoingEdge(1);
        Node n2 = new Node();
        n2.addIncomingEdge(0);
        int[][] paths = new int[2][3];
        paths[0][0] = 1;
        paths[0][1] = 2;
        paths[0][2] = 3;
        paths[1][0] = 1;
        paths[1][1] = 3;
        nodes.add(n1);
        nodes.add(n2);
        ngTest = new NodeGraph(nodes, null, null, null);
        when(fact.createStage()).thenReturn(stage);
        when(fact.createLabel(anyString())).thenReturn(label);
        when(fact.createGroup()).thenReturn(group);
        when(group.getChildren()).thenReturn(list);
        when(fact.createScene(group, 150, 100)).thenReturn(scene);
        when(fact.setScene(stage, scene)).thenReturn(stage);
        gi.setPaths(paths);
        GraphInfo.setInstance(gi);
        NodeGraph.setCurrentInstance(ngTest);
        gs = new GraphScene(fact);
    }

    @After
    public void tearDown() {
        gs = null;
    }

    @Test
    public void switchToCenter() {
        gs.switchToCenter();
        assertEquals(gs.getCenter(), gs.getState());
    }

    @Test
    public void switchToInfo() {
        assertEquals(gs.getInfo(), gs.getState());
        gs.switchToCenter();
        assertEquals(gs.getCenter(), gs.getState());
        gs.switchToInfo();
        assertEquals(gs.getInfo(), gs.getState());
    }

    @Test
    public void drawGraphInRadius() {
        gs.drawGraph(0, 20);
        verify(fact, never()).createGroup();
        verify(fact, never()).createLabel(anyString());
        verify(fact, never()).createStage();
        verify(fact, never()).createScene(group, 150, 100);
        verify(fact, never()).setScene(stage, scene);
        verify(group, never()).getChildren();
        verify(fact, never()).show(stage);
    }

    @Test
    public void getNumberOfDuplicates() {
        try {
            Method method = GraphScene.class.getDeclaredMethod("getNumberOfDuplicates", int.class, int.class);
            method.setAccessible(true);
            int result = (int) method.invoke(gs, 0, 1);
            assertEquals(2, result);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void determineEdgeWidth() {
        try {
            int[] out0 = {1, 2};
            int[] out1 = {2};
            int[] in1 = {0};
            int[] in2 = {0, 1};
            int[][] paths = new int[3][3];
            paths[1][0] = 1;
            paths[1][1] = 3;
            ArrayList<Node> nodes = new ArrayList<>();
            nodes.add(new Node(0, out0, new int[0]));
            nodes.add(new Node(0, out1, in1));
            nodes.add(new Node(0, new int[0], in2));
            LinkedList<DrawNode> dNodes = new LinkedList<>();
            for (int i = 0; i < 3; i++) {
                DrawNode dNode = new DrawNode(i);
                dNode.setX(i);
                dNodes.add(dNode);
                paths[0][i] = i;
                paths[2][i] = i;
            }
            gi.setPaths(paths);
            GraphInfo.setInstance(gi);
            NodeGraph ng = new NodeGraph(nodes, null, dNodes, null);
            NodeGraph.setCurrentInstance(ng);
            Method method = GraphScene.class.getDeclaredMethod("determineEdgeWidth", Node.class, int.class);
            method.setAccessible(true);
            double[] result = (double[]) method.invoke(gs, nodes.get(0), 0);
            assertEquals(2, result[0], 0.00001);
            assertEquals(3, result[1], 0.00001);
            assertEquals(2, result.length);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}

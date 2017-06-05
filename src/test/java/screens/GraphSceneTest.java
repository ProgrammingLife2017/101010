package screens;

import datastructure.DrawNode;
import datastructure.Node;
import datastructure.NodeGraph;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
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
        nodes.add(n1);
        nodes.add(n2);
        ngTest = new NodeGraph(nodes, null, null, null);
        when(fact.createStage()).thenReturn(stage);
        when(fact.createLabel(anyString())).thenReturn(label);
        when(fact.createGroup()).thenReturn(group);
        when(group.getChildren()).thenReturn(list);
        when(fact.createScene(group, 150, 100)).thenReturn(scene);
        when(fact.setScene(stage, scene)).thenReturn(stage);
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
    public void drawGraphTestInRadius() {
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
    public void genomeSelect() {
        LinkedList<DrawNode> drawNodes = new LinkedList<DrawNode>();
        for (int i = -10; i < 25; i++) {
            drawNodes.add(new DrawNode(i));
        }
        ngTest = new NodeGraph(null, null, drawNodes, null);
        NodeGraph.setCurrentInstance(ngTest);
        Set<Integer> genomes = new HashSet<Integer>();
        for (int i = 0; i < 15 ; i++) {
            genomes.add(i);
        }
        gs.genomeSelect(genomes);
        for (int i = 0; i < 35; i++) {
            if (drawNodes.get(i).getIndex() >= 0 && drawNodes.get(i).getIndex() < 15) {
                assertEquals(Color.GREEN, drawNodes.get(i).getFill());
            } else {
                assertEquals(Color.CRIMSON, drawNodes.get(i).getFill());
            }
        }
    }

    @Test
    public void clearGenomeSelect() {
        LinkedList<DrawNode> drawNodes = new LinkedList<DrawNode>();
        for (int i = -10; i < 25; i++) {
            drawNodes.add(new DrawNode(i));
        }
        ngTest = new NodeGraph(null, null, drawNodes, null);
        NodeGraph.setCurrentInstance(ngTest);
        Set<Integer> genomes = new HashSet<Integer>();
        for (int i = 0; i < 15 ; i++) {
            genomes.add(i);
        }
        gs.genomeSelect(genomes);
        gs.clearGenomeSelect();
        for (int i = 0; i < 35; i++) {
            assertEquals(Color.CRIMSON, drawNodes.get(i).getFill());
        }
    }
}

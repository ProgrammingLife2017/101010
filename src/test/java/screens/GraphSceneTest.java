package screens;

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
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by Martijn on 24-5-2017.
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

    @Mock
    NodeGraph ngMock;

    ArrayList<Node> nodes = new ArrayList<Node>();

    NodeGraph ngTest = new NodeGraph();

    GraphScene gs;

    /**
     * Initialize the JavaFX toolkit, so its services can be tested.
     * @throws InterruptedException
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
    public void drawGraphTestNotInRadius() {
        gs.drawGraph(0,0);
        verify(fact).createGroup();
        verify(fact).createLabel(anyString());
        verify(fact).createStage();
        verify(fact).createScene(group, 150, 100);
        verify(fact).setScene(stage, scene);
        verify(group).getChildren();
        verify(fact).show(stage);
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
}

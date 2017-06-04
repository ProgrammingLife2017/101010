package screens;

import datastructure.Node;
import datastructure.NodeGraph;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
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
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import parsing.Parser;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests the GraphScene class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Platform.class, Parser.class})
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

    @Mock
    Thread thread;

    /**
     * Initialize the JavaFX toolkit, so its services can be tested.
     * @throws InterruptedException that triggers when the drawing is interrupted.
     */
    @BeforeClass
    public static void initToolkit() throws InterruptedException {
        new Thread(() -> {
            Application.launch(Window.class, new String[0]);
        }).start();
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
    public void drawGraphTestInRadius() throws Exception {
        PowerMockito.mockStatic(Platform.class);
        PowerMockito.doNothing().when(Platform.class);
        Platform.runLater(any());

        thread = mock(Thread.class);
        PowerMockito.mockStatic(Parser.class);
        PowerMockito.when(Parser.getThread()).thenReturn(thread);

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

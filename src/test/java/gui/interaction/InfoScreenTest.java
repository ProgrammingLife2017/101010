package gui.interaction;

import datastructure.DrawNode;
import datastructure.NodeGraph;
import gui.FXElementsFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.spy;

/**
 * Test class for the information window.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({NodeGraph.class, InfoScreen.class, DrawNode.class})
public class InfoScreenTest {
    private InfoScreen infoScreen;
    private TextArea textArea;
    private Stage stage;
    private Group group;
    private FXElementsFactory factory;
    private NodeGraph nodeGraph;
    private DrawNode node;
    private Line line;
    List<Node> list = new ArrayList<>();
    ObservableList<Node> observableList = FXCollections.observableList(list);
    HashMap<Object, Object> map = new HashMap<>();
    ObservableMap<Object, Object> observableMap = FXCollections.observableMap(map);
    Label label;
    String segment = "AAAAA";
    String edgeNodes = "1 - 2";
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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() throws Exception {

        int stubbedInt = 0;
        textArea = mock(TextArea.class);
        factory = mock(FXElementsFactory.class);
        stage = mock(Stage.class);
        group = mock(Group.class);
        nodeGraph = mock(NodeGraph.class);
        node = mock(DrawNode.class);
        label = mock(Label.class);
        line = mock(Line.class);
        when(factory.createStage()).thenReturn(stage);
        when(factory.createGroup()).thenReturn(group);
        when(factory.createLabel(anyString())).thenReturn(label);
        //when(label.getProperties()).thenReturn(observableMap);
        when(group.getChildren()).thenReturn(observableList);
        when(node.getIndex()).thenReturn(stubbedInt);
        when(nodeGraph.getSegment(anyInt())).thenReturn(segment);
        when(line.getId()).thenReturn(edgeNodes);
        infoScreen = Whitebox.invokeConstructor(InfoScreen.class, factory);
        Whitebox.setInternalState(infoScreen, "textArea", textArea);
    }

    @Test
    public void testConstructor() {
        assertEquals(textArea, Whitebox.getInternalState(infoScreen, "textArea"));
        assertEquals(factory, Whitebox.getInternalState(infoScreen, "fxElementsFactory"));
    }

    @Test
    public void testDisplayNodeInfo() throws Exception {
        InfoScreen infoScreen1 = spy(infoScreen);
        PowerMockito.mockStatic(NodeGraph.class);
        PowerMockito.when(NodeGraph.class, "getCurrentInstance").thenReturn(nodeGraph);
        PowerMockito.doNothing().when(infoScreen1).add(anyObject(), anyInt(), anyInt());
        PowerMockito.doNothing().when(infoScreen1).add(anyObject(), anyInt(), anyInt(), anyInt(), anyInt());
        infoScreen1.displayNodeInfo(node);
        verify(textArea, times(1)).appendText(segment);
    }

    @Test
    public void testDisplayNodeInfoNoGraph() {
        thrown.expect(AssertionError.class);
        infoScreen.displayNodeInfo(node);
    }

    @Test
    public void testDisplayLineInfo() throws Exception {
        InfoScreen infoScreen2 = spy(infoScreen);
        PowerMockito.mockStatic(NodeGraph.class);
        PowerMockito.when(NodeGraph.class, "getCurrentInstance").thenReturn(nodeGraph);
        PowerMockito.doNothing().when(infoScreen2).add(anyObject(), anyInt(), anyInt());
        infoScreen2.displayLineInfo(line);
        verify(factory, times(3)).createLabel(anyString());
    }

    @Test
    public void testDisplayLineInfoNoGraph() {
        thrown.expect(AssertionError.class);
        infoScreen.displayLineInfo(line);
    }

}


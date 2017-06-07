package gui.scenehandler;

import datastructure.DrawNode;
import gui.GraphScene;
import gui.interaction.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Test class for the NodeCenter class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Controller.class})
public class NodeCenterTest {
    private NodeCenter nodeCenter;
    private GraphScene graphScene;
    private DrawNode node;
    private int mockedNodeId = 1;
    private int mockedRadius = 300;

    List<Node> list = new ArrayList<>();
    ObservableList<Node> observableList = FXCollections.observableList(list);
    List<Node> list2 = new ArrayList<>();
    ObservableList<Node> observableList2 = FXCollections.observableList(list2);

//    /**
//     * Initialize the JavaFX toolkit, so its services can be tested.
//     * @throws InterruptedException
//     */
//    @BeforeClass
//    public static void initToolkit() throws InterruptedException
//    {
//        final CountDownLatch latch = new CountDownLatch(1);
//        SwingUtilities.invokeLater(() -> {
//            new JFXPanel();
//            latch.countDown();
//        });
//        if (!latch.await(5L, TimeUnit.SECONDS))
//            throw new ExceptionInInitializerError();
//    }

    @Before
    public void setup() throws Exception {
        graphScene = mock(GraphScene.class);
        node = mock(DrawNode.class);
        when(node.getIndex()).thenReturn(mockedNodeId);
        nodeCenter = new NodeCenter(graphScene);
    }

    @Test
    public void testConstructor() {
        assertEquals(graphScene, Whitebox.getInternalState(nodeCenter, "graphScene"));
    }

    @Test
    public void testHandle() throws Exception {
        PowerMockito.mockStatic(Controller.class);
        when(Controller.class, "getRadius").thenReturn(mockedRadius);
        nodeCenter.handleNode(node);
        verify(graphScene, times(1)).drawGraph(mockedNodeId, mockedRadius);
    }

}


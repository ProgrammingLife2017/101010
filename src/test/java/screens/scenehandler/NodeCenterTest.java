package screens.scenehandler;

import datastructure.DrawNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import screens.FXElementsFactory;
import screens.GraphScene;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;

/**
 * Test class for the NodeCenter class.
 */
public class NodeCenterTest {
    private NodeCenter nodeCenter;
    private GraphScene graphScene;
    private FXElementsFactory factory;
    private DrawNode node;
    private Stage stage;
    private Group group;
    private VBox container;

    List<Node> list = new ArrayList<>();
    ObservableList<Node> observableList = FXCollections.observableList(list);
    List<Node> list2 = new ArrayList<>();
    ObservableList<Node> observableList2 = FXCollections.observableList(list2);

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
    public void setup() throws Exception {
        graphScene = mock(GraphScene.class);
        factory = mock(FXElementsFactory.class);
        node = mock(DrawNode.class);
        stage = mock(Stage.class);
        group = mock(Group.class);
        container = mock(VBox.class);

        when(factory.createStage()).thenReturn(stage);
        when(factory.createGroup()).thenReturn(group);
        when(group.getChildren()).thenReturn(observableList);
        when(container.getChildren()).thenReturn(observableList2);


        nodeCenter = new NodeCenter(graphScene, factory);
        Whitebox.setInternalState(nodeCenter, "box", container);
    }

    @Test
    public void testConstructor() {
        assertEquals(graphScene, Whitebox.getInternalState(nodeCenter, "graphScene"));
        assertEquals(factory, Whitebox.getInternalState(nodeCenter, "fxElementsFactory"));
    }

    @Test
    public void testHandle() {
        nodeCenter.handle(node);
        verify(factory, times(1)).createStage();
        verify(factory, times(1)).createGroup();
        verify(factory, times(1)).show(stage);
    }

}

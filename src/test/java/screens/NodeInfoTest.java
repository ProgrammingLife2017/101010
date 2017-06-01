package screens;

import datastructure.DrawNode;
import datastructure.NodeGraph;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextArea;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by Michael on 5/26/2017.
 */
public class NodeInfoTest {
    private NodeInfo nodeInfo;
    private InfoScreen infoScreen;
    private TextArea textArea;
    private DrawNode drawNode;
    private NodeGraph graph;

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
    public void setup() {
        String resultText = "hello world";
        int resultInt = 100;

        infoScreen = mock(InfoScreen.class);
        textArea = mock(TextArea.class);
        drawNode = mock(DrawNode.class);
        graph = mock(NodeGraph.class);
        when(graph.getSegment(anyInt())).thenReturn(resultText);
        when(drawNode.getIndex()).thenReturn(resultInt);
        when(infoScreen.getTextArea()).thenReturn(textArea);

        nodeInfo = new NodeInfo(infoScreen);
    }

    @Test
    public void constructorTest() {
        InfoScreen expected = Whitebox.getInternalState(nodeInfo, "infoScreen");
        assertEquals(expected, infoScreen);
    }

}

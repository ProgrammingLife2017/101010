package gui.scenehandler;

import datastructure.DrawNode;
import gui.Window;
import gui.interaction.InfoScreen;
import javafx.embed.swing.JFXPanel;
import javafx.scene.shape.Line;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Michael on 5/26/2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Window.class})
public class NodeInfoTest {
    private NodeInfo nodeInfo;
    private InfoScreen infoScreen;
    private DrawNode drawNode;
    private Line line;

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

        infoScreen = mock(InfoScreen.class);
        drawNode = mock(DrawNode.class);
        line = mock(Line.class);
        PowerMockito.mockStatic(Window.class);
        PowerMockito.when(Window.class, "getInfoScreen").thenReturn(infoScreen);
        nodeInfo = new NodeInfo();
    }

    @Test
    public void testHandleNode() throws Exception {
        PowerMockito.doNothing().when(infoScreen).displayNodeInfo(drawNode);
        nodeInfo.handleNode(drawNode);
        verify(infoScreen, times(1)).displayNodeInfo(drawNode);
    }

    @Test
    public void testHandleLine() throws Exception {
        PowerMockito.doNothing().when(infoScreen).displayLineInfo(line);
        nodeInfo.handleLine(line);
        verify(infoScreen, times(1)).displayLineInfo(line);
    }

}


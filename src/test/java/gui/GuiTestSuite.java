package gui;

import gui.interaction.InfoScreenTest;
import gui.scenehandler.NodeCenterTest;
import gui.scenehandler.NodeInfoTest;
import javafx.embed.swing.JFXPanel;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Michael on 6/7/2017.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        InfoScreenTest.class,
        NodeCenterTest.class,
        NodeInfoTest.class,
        BacklogTest.class,
        GraphSceneTest.class
})
public class GuiTestSuite {
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
}

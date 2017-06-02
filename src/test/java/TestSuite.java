import javafx.embed.swing.JFXPanel;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import screens.*;
import screens.scenehandler.NodeCenterTest;
import screens.scenehandler.NodeInfoTest;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Martijn on 29-5-2017.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BacklogTest.class,
        GraphSceneTest.class,
        InfoScreenTest.class,
        NodeCenterTest.class,
        NodeInfoTest.class
})

public class TestSuite {
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

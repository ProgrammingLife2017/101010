package screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

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
 * Test class for the information window.
 */
@RunWith(PowerMockRunner.class)
public class InfoScreenTest {
    private InfoScreen infoScreen;
    private TextArea textArea;
    private Stage stage;
    private Group group;
    private FXElementsFactory factory;
    List<Node> list = new ArrayList<>();
    ObservableList<Node> observableList = FXCollections.observableList(list);

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
        textArea = mock(TextArea.class);
        factory = mock(FXElementsFactory.class);
        stage = mock(Stage.class);
        group = mock(Group.class);
        when(factory.createStage()).thenReturn(stage);
        when(factory.createGroup()).thenReturn(group);
        when(group.getChildren()).thenReturn(observableList);

        infoScreen = Whitebox.invokeConstructor(InfoScreen.class, factory);
        Whitebox.setInternalState(infoScreen, "textArea", textArea);
    }

    @Test
    public void textGetTextArea() {
        assertEquals(textArea, infoScreen.getTextArea());
    }

    @Test
    public void testShow() {
        infoScreen.show();
        verify(factory, times(1)).show(stage);
    }

}

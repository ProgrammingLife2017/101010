package screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Test class for the Window class.
 */
public class WindowTest {
    private Window window;
    private Menu menu1, menu2, menu3;
    private Stage stage;
    private Group group;
    private Label label;
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
    public void setup() {
        double width = 100.0;
        stage = mock(Stage.class);
        menu1 = mock(Menu.class);
        menu2 = mock(Menu.class);
        menu3 = mock(Menu.class);
        label = mock(Label.class);
        factory = mock(FXElementsFactory.class);
        group = PowerMockito.mock(Group.class);
        Mockito.when(factory.createStage()).thenReturn(stage);
        Mockito.when(factory.createGroup()).thenReturn(group);
        Mockito.when(factory.createLabel(anyString())).thenReturn(label);
        Mockito.when(group.getChildren()).thenReturn(observableList);
        window = mock(Window.class);
        Whitebox.setInternalState(window, "factory", factory);
    }

    @Test
    public void testCreateMenuBar() throws Exception {
        Window window2 = mock(Window.class);
        when(window2.addFileSelector(stage)).thenReturn(menu1);
        when(window2.addController()).thenReturn(menu2);
        when(window2.addClear()).thenReturn(menu3);

        MenuBar menuBar = Whitebox.invokeMethod(window2, "createMenuBar", stage);
        assertEquals(menu1, menuBar.getMenus().get(0));
        assertEquals(menu2, menuBar.getMenus().get(1));
        assertEquals(menu3, menuBar.getMenus().get(2));
    }

}

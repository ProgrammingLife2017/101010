package screens;

import javafx.scene.control.TextArea;
import org.junit.Before;
import org.powermock.reflect.Whitebox;

import static org.powermock.api.mockito.PowerMockito.mock;

/**
 * Test class for the backlog window.
 */
public class BacklogTest {
    private Backlog log;
    private TextArea area;

    @Before
    public void setup() {
        log = new Backlog();
        area = mock(TextArea.class);
        Whitebox.setInternalState(log, "textArea", area);
    }
}

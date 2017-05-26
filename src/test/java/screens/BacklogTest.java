package screens;

import javafx.scene.control.TextArea;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;

/**
 * Test class for the backlog window.
 */
@RunWith(PowerMockRunner.class)
public class BacklogTest {
    private Backlog log;
    private TextArea area;

    @Before
    public void setup() throws Exception{
        log = Whitebox.invokeConstructor(Backlog.class);
        area = mock(TextArea.class);
        Whitebox.setInternalState(log, "textArea", area);
    }

    @Test
    public void testPrintContent() {
        String testString = "hello world";
        log.printContent(testString);
        verify(area, times(1)).appendText(testString);
    }
}

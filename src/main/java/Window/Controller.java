package Window;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.text.SimpleDateFormat;

/**
 * Created by 101010 on 8-5-2017.
 */
public class Controller {
    @FXML private TextArea console;

    @FXML protected void handleNode(MouseEvent event) {
        console.appendText("[" + new SimpleDateFormat("HH:mm").format(System.currentTimeMillis()) + "] - " + "ATCTGGTTCATG\n");
    }

    @FXML protected void clearConsole(ActionEvent event) {
        console.clear();
    }

    @FXML protected void exitApp(ActionEvent event) {
        Platform.exit();
    }
}

package Window;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.io.File;
import javafx.stage.FileChooser;
import java.text.SimpleDateFormat;

/**
 * Created by 101010 on 8-5-2017.
 */
public class Controller {

    @FXML private Pane mainPane;

    @FXML private Button browse;

    @FXML private MenuBar menu;

    @FXML private TextArea console;

    @FXML private void openFile(final ActionEvent event) throws IOException {
        Stage stage = (Stage) menu.getScene().getWindow();
        Parent root;
        final FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("GFA files (*.gfa)", "*.gfa");
        fileChooser.getExtensionFilters().add(extFilter);
        final Button openButton = new Button("Open");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            //TODO parse the selected file and visualize the graph with its data
        }
    }

    @FXML private void handleBrowseButton(final ActionEvent event) throws IOException {
        Stage stage = (Stage) browse.getScene().getWindow();
        Parent root;
        if (event.getSource() == browse) {
            final FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("GFA files (*.gfa)", "*.gfa");
            fileChooser.getExtensionFilters().add(extFilter);
            final Button openButton = new Button("Open");
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                stage.setTitle("Graph visualization");
                root = null;
                try {
                     root = FXMLLoader.load(getClass().getResource("/window.fxml"));
                } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setScene(new Scene(root, 1600, 900));
            stage.show();
            }
        }
    }

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

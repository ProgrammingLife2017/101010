package window;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.io.IOException;
import javafx.stage.FileChooser;
import javafx.scene.control.Button;

import java.io.File;
/**
 * Created by 101010 on 8-5-2017.
 */
public class App extends javafx.application.Application {

    /**
     * Method that starts the application.
     * @param args
     */
    public static void main(final String[] args) {
        launch(args);
    }

    /**
     * Initializes the main window of the application.
     * @param primaryStage contains information of the window.
     */
    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Choose a file");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/windowSelect.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 300, 100));
        primaryStage.show();
    }
}

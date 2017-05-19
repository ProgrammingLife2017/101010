package screens;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

/**
 * Created by Michael on 5/19/2017.
 */
public class LoadingScreen extends Stage{

    private final ProgressBar progressBar;

    public LoadingScreen() {
        progressBar = new ProgressBar();
        Group pane = new Group();
        pane.getChildren().add(progressBar);
        Scene scene = new Scene(pane, 200, 100);
        setTitle("Loading bar");
        setScene(scene);
    }

    public void setPercentage(double percentage) {
        this.progressBar.setProgress(percentage);
    }
}

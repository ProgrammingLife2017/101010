package screens;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

/**
 *
 * Implementation of the screen for displaying progress.
 */
public class LoadingScreen extends Stage {

    /**
     * Progress bar for displaying progress during execution.
     */
    private final ProgressBar progressBar;

    /**
     * Constructor.
     */
    public LoadingScreen() {
        progressBar = new ProgressBar();
        Group pane = new Group();
        pane.getChildren().add(progressBar);
        Scene scene = new Scene(pane, 200, 100);
        setTitle("Loading bar");
        setScene(scene);
    }

    /**
     * Sets the number to display on the progress bar.
     *
     * @param percentage Number to display.
     */
    public void setPercentage(double percentage) {
        this.progressBar.setProgress(percentage);
    }
}

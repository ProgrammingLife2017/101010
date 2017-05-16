package screens;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Implementation of the backlog screen to keep track of actions that are performed by the application.
 */
public final class Backlog extends Stage {
    /**
     * Area to write content of the logger.
     */
    private TextArea textArea;

    /**
     * Constructor
     */
    public Backlog() {
        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setVisible(true);
        textArea.textProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        textArea.setScrollTop(Double.MAX_VALUE);
                    }
                }
        );

        Group root = new Group();
        root.getChildren().add(textArea);
        Scene scene = new Scene(root);
        this.setScene(scene);
    }

    /**
     * Prints log message in the textArea.
     *
     * @param cont Message to print.
     */
    public void printContent(final String cont) {
        textArea.appendText(cont + "\n");
    }
}

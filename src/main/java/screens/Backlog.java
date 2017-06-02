package screens;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Implementation of the backlog screen to keep track of actions that are performed by the application.
 */
public final class Backlog extends Stage {
    /**
     * Area to write content of the logger.
     */
    private final TextArea textArea;

    /**
     * Backlog window constructor.
     */
    /* package */ Backlog() {
        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setVisible(true);
        textArea.textProperty().addListener(
                (observable, oldValue, newValue) -> textArea.setScrollTop(Double.MAX_VALUE)
        );
        this.setTitle("Backlog");
        AnchorPane root = new AnchorPane();
        root.setPrefSize(600.0, 300.0);
        AnchorPane.setTopAnchor(textArea, 10.0);
        AnchorPane.setLeftAnchor(textArea, 10.0);
        AnchorPane.setRightAnchor(textArea, 10.0);
        AnchorPane.setBottomAnchor(textArea, 10.0);
        root.getChildren().add(textArea);
        Scene scene = new Scene(root);
        this.setScene(scene);
    }

    /**
     * Prints log message in the textArea.
     * @param cont Message to print.
     */
    public void printContent(final String cont) {
        textArea.appendText(cont + "\n");
    }
}

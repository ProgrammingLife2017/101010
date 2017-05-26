package screens;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Implementation of the backlog screen to keep track of actions that are performed by the application.
 */
public final class Backlog {
    /**
     * Area to write content of the logger.
     */
    private final TextArea textArea;

    private final Stage stage;

    private static double ANCHOR_OFFSET = 10d;

    private static double PREFFERED_HEIGHT = 300d;

    private static double PREFFERED_WIDTH = 600d;
    /**
     * Backlog window constructor.
     */
    /* package */ Backlog() {
        textArea = new TextArea();
        stage = new Stage();
    }

    public void setup() {
        textArea.setEditable(false);
        textArea.setVisible(true);
        textArea.textProperty().addListener(
                (observable, oldValue, newValue) -> textArea.setScrollTop(Double.MAX_VALUE)
        );
        stage.setTitle("Backlog");
        AnchorPane root = new AnchorPane();
        root.setPrefSize(PREFFERED_WIDTH, PREFFERED_HEIGHT);
        AnchorPane.setTopAnchor(textArea, ANCHOR_OFFSET);
        AnchorPane.setLeftAnchor(textArea, ANCHOR_OFFSET);
        AnchorPane.setRightAnchor(textArea, ANCHOR_OFFSET);
        AnchorPane.setBottomAnchor(textArea, ANCHOR_OFFSET);
        root.getChildren().add(textArea);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    /**
     * Prints log message in the textArea.
     * @param cont Message to print.
     */
    public void printContent(final String cont) {
        textArea.appendText(cont + "\n");
    }
}

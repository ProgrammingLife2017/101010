package screens;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Implementation of the backlog screen to keep track of actions that are performed by the application.
 */
public class Backlog extends AnchorPane {
    /**
     * Area to write content of the logger.
     */
    private final TextArea textArea;

    private Stage stage;

    private static double ANCHOR_OFFSET = 10d;

    private static int PREFFERED_HEIGHT = 300;

    private static int PREFFERED_WIDTH = 600;

    private FXElementsFactory factory;
    /**
     * Backlog window constructor.
     */
    /* package */ Backlog(FXElementsFactory fact) {
        textArea = new TextArea();
        this.factory = fact;
        this.setup();
    }

    private void setup() {
        textArea.setEditable(false);
        textArea.setVisible(true);
        textArea.textProperty().addListener(
                (observable, oldValue, newValue) -> textArea.setScrollTop(Double.MAX_VALUE)
        );

        stage = factory.createStage();
        this.setTopAnchor(textArea, ANCHOR_OFFSET);
        this.setLeftAnchor(textArea, ANCHOR_OFFSET);
        this.setRightAnchor(textArea, ANCHOR_OFFSET);
        this.setBottomAnchor(textArea, ANCHOR_OFFSET);
        this.getChildren().add(textArea);
        Group group = factory.createGroup();
        group.getChildren().addAll(this);
        Scene scene = factory.createScene(group, PREFFERED_WIDTH, PREFFERED_HEIGHT);
        factory.setScene(stage, scene);
    }

    /**
     * Prints log message in the textArea.
     * @param cont Message to print.
     */
    public void printContent(final String cont) {
        textArea.appendText(cont + "\n");
    }

    public void show() {
        stage.show();
    }
}

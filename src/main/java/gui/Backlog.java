package gui;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Implementation of the backlog screen to keep track of actions that are performed by the application.
 */
public final class Backlog extends AnchorPane {
    /**
     * Area to write content of the logger.
     */
    private final TextArea textArea;

    private Stage stage;

    private FXElementsFactory fxElementsFactory;

    /**
     * Backlog window constructor.
     */
    /* package */ Backlog(FXElementsFactory factory) {
        this.fxElementsFactory = factory;
        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setVisible(true);
        textArea.textProperty().addListener(
                (observable, oldValue, newValue) -> textArea.setScrollTop(Double.MAX_VALUE)
        );
        stage = factory.createStage();
        stage.setTitle("Backlog");
        this.setPrefSize(600.0, 300.0);
        this.setTopAnchor(textArea, 10.0);
        this.setLeftAnchor(textArea, 10.0);
        this.setRightAnchor(textArea, 10.0);
        this.setBottomAnchor(textArea, 10.0);
        this.getChildren().add(textArea);
        Group group = factory.createGroup();
        group.getChildren().add(this);
        Scene scene = factory.createScene(group);
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
        fxElementsFactory.show(stage);
    }
}

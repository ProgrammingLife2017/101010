package screens;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Implementation of the window that handles showing information of the graph.
 */
public class InfoScreen extends VBox {

    /**
     * Text area for printing information.
     */
    private final TextArea textArea;

    private static int MIN_WIDTH = 300;

    private static int MIN_HEIGHT = 600;

    private Stage stage;

    private FXElementsFactory factory;

    /**
     * Constructor.
     */
    /*package*/ InfoScreen(FXElementsFactory fact) {
        factory = fact;
        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setVisible(true);
        textArea.textProperty().addListener(
                (observable, oldValue, newValue) -> textArea.setScrollTop(Double.MAX_VALUE)
        );
        this.getChildren().add(textArea);
        Group group = factory.createGroup();
        group.getChildren().add(this);
        Scene scene = factory.createScene(group, MIN_WIDTH, MIN_HEIGHT);
        stage = factory.createStage();
        factory.setScene(stage, scene);
        stage.setTitle("Information");
    }

    /**
     * Gets the text area of this object.
     * @return TextArea object.
     */
    public TextArea getTextArea() {
        return textArea;
    }

    public void show() {
        factory.show(stage);
    }

}

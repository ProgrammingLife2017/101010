package screens;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Implementation of the window that handles showing information of the graph.
 */
/*package*/ class InfoScreen extends Stage {

    /**
     * Text area for printing information.
     */
    private final TextArea textArea;

    /**
     * Constructor.
     */
    /*package*/ InfoScreen() {
        VBox vbox = new VBox();
        vbox.setMinSize(300f, 600f);
        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setVisible(true);
        textArea.textProperty().addListener(
                (observable, oldValue, newValue) -> textArea.setScrollTop(Double.MAX_VALUE)
        );
        vbox.getChildren().add(textArea);
        Scene scene = new Scene(vbox);
        this.setTitle("Information");
        this.setScene(scene);
    }

    /**
     * Gets the text area of this object.
     * @return TextArea object.
     */
    public TextArea getTextArea() {
        return textArea;
    }

}

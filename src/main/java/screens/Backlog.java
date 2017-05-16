package screens;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

/**
 * Created by Michael on 5/16/2017.
 */
public final class Backlog extends Pane {
    private TextArea textArea;

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
        this.getChildren().add(textArea);
    }

    public void printContent(final String cont) {
        textArea.appendText(cont);
    }
}

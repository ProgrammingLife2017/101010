package screens;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Created by Michael on 5/16/2017.
 */
public final class Backlog extends Stage {
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

        Group root = new Group();
        root.getChildren().add(textArea);
        Scene scene = new Scene(root);
        this.setScene(scene);
    }

    public void printContent(final String cont) {
        textArea.appendText(cont);
    }
}

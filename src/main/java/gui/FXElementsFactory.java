package gui;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Factory for JavaFX constructors.
 */
public class FXElementsFactory {

    /**
     * Creates a JavaFX stage.
     * @return the created stage.
     */
    public Stage createStage() {
        return new Stage();
    }

    /**
     * Creates a JavaFX label.
     * @param text the text contained in the label.
     * @return the created label.
     */
    public Label createLabel(String text) {
        return new Label(text);
    }

    /**
     * Creates a JavaFX group.
     * @return te created group.
     */
    public Group createGroup() {
        return new Group();
    }

    /**
     * Creates a JavaFX scene.
     * @param g the JavaFX group from which it is made.
     * @param width the width of the scene.
     * @param height the height of the scene.
     * @return the created scene.
     */
    public Scene createScene(Group g, int width, int height) {
        return new Scene(g, width, height);
    }

    /**
     * Sets a JavaFX scene in a JavaFX stage.
     * @param currentStage the stage in which the scene will be set.
     * @param scene the scene that will be set.
     * @return the stage with the new scene.
     */
    public Stage setScene(Stage currentStage, Scene scene) {
        currentStage.setScene(scene);
        return currentStage;
    }

    /**
     * Displays a JavaFX stage.
     * @param stage the stage to display.
     * @return the displayed stage.
     */
    public Stage show(Stage stage) {
        stage.show();
        return stage;
    }

    public void setOnAction(Node node, EventHandler eventHandler) {

    }
}

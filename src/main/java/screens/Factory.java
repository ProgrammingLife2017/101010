package screens;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Created by Martijn on 24-5-2017.
 */
public class Factory {
    public Stage createStage() {
        return new Stage();
    }

    public Label createLabel(String text) {
        return new Label(text);
    }

    public Group createGroup() {
        return new Group();
    }

    public Scene createScene(Group g, int width, int height) {
        return new Scene(g, width, height);
    }

    public Stage setScene(Stage currentStage, Scene scene) {
        currentStage.setScene(scene);
        return currentStage;
    }

    public Stage show(Stage stage) {
        stage.show();
        return stage;
    }
}

package gui.interaction;

import gui.FXElementsFactory;
import gui.GraphScene;
import javafx.scene.layout.VBox;

/**
 * Created by Michael on 6/6/2017.
 */
public class InteractionScene extends VBox {
    private Controller controller;
    private InfoScreen infoScreen;

    private static double MAX_WIDTH = 175d;

    public InteractionScene(FXElementsFactory factory, GraphScene graphScene) {
        infoScreen = new InfoScreen(factory);
        controller = new Controller(factory, graphScene);
        setSettings();
    }

    private void setSettings() {
        this.setMaxWidth(MAX_WIDTH);
        this.getChildren().addAll(controller, infoScreen);
        this.getStyleClass().add("vbox");
    }

    public InfoScreen getInfoScreen() { return  infoScreen; }

}

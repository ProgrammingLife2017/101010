package screens.scenes;

import screens.FXElementsFactory;
import screens.scenes.GraphScene;
import javafx.scene.layout.VBox;
/**
 * Created by Michael on 6/8/2017.
 */
public class InteractionScene extends VBox {
    /**
     * Controller object for interacting with the graph.
     */
    private Controller controller;

    /**
     * Screen to display graph information.
     */
    private InfoScreen infoScreen;

    /**
     * The maximum width of this container.
     */
    private static final double MAXIMUM_WIDTH = 175d;

    /**
     * Constructor.
     * @param factory Factory to create javaFX components.
     * @param graphScene Scene where the graph is drawn in.
     */
    public InteractionScene(FXElementsFactory factory, GraphScene graphScene) {
        infoScreen = new InfoScreen(factory);
        controller = new Controller(factory, graphScene);
        setSettings();
    }

    /**
     * Container settings.
     */
    private void setSettings() {
        this.setMaxWidth(MAXIMUM_WIDTH);
        this.getChildren().addAll(controller, infoScreen);
        this.getStyleClass().add("vbox");
    }

    /**
     * Information screen getter.
     * @return InfoScreen object.
     */
    public InfoScreen getInfoScreen() {
        return  infoScreen;
    }

}

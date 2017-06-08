package screens.scenes;

import javafx.scene.layout.VBox;
import services.ServiceLocator;

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
     * @param serviceLocator ServiceLocator for locating services registered in that object.
     */
    public InteractionScene(ServiceLocator serviceLocator) {
        infoScreen = new InfoScreen(serviceLocator);
        controller = new Controller(serviceLocator);
        setSettings();
    }

    public static void register(ServiceLocator sL) {
        if(sL == null) {
            throw new IllegalArgumentException("The service locator can not be null");
        }
        sL.setInteractionScene(new InteractionScene(sL));
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

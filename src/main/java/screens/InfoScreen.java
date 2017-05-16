package screens;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Implementation of the window that handles showing information of the graph.
 */
public class InfoScreen extends GridPane {

    /**
     * Constructor.
     */
    public InfoScreen() {
        getStyleClass().addAll("pane");
        add(new Label("Graph information:"), 1, 1);
    }

}

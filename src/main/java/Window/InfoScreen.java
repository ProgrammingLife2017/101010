package Window;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Created by Michael on 5/12/2017.
 */
public class InfoScreen extends GridPane {


    public InfoScreen() {
        setMinWidth(200);
        setMinHeight(200);
        getStyleClass().addAll("pane", "vbox");
        add(new Label("Graph information:"), 1, 1);
    }

//    public InfoScreen getDisplay() {
//        if (display == null) {
//            return new InfoScreen();
//        } else {
//            return display;
//        }
//
//    }
}

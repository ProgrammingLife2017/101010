package Window;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by 101010 on 101010.
 */
public class Window extends Application{

    @Override
    public void start(Stage stage) throws Exception{

        BorderPane mainPane = new BorderPane();
        mainPane.setMinSize(1000, 500);
        mainPane.setLeft(createVBoxLeft());
        mainPane.setRight(createVBoxRight());

        //Creating a scene object
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add("layoutstyles.css");

        //Setting title to the Stage
        stage.setTitle("Registration Form");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }

    public VBox createVBoxLeft() {
        VBox box = new VBox();
        box.getStyleClass().addAll("pane", "vbox");
        box.setAlignment(Pos.CENTER);
        box.setMinSize(200, 200);
        return box;
    }

    public VBox createVBoxRight() {
        VBox box = new VBox();
        box.getStyleClass().addAll("pane", "vbox");
        box.setAlignment(Pos.CENTER);
        box.setMinSize(200, 200);
        return box;
    }


    public static void main(String[] args) {
        launch(args);
    }
}

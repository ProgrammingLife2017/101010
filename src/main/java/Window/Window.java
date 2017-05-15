package Window;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by 101010 on 101010.
 */
public class Window extends Application{

    @Override
    public void start(Stage stage) throws Exception{

        BorderPane mainPane = new BorderPane();
        mainPane.setMinSize(1000, 500);

        mainPane.setTop(createMenuBar(stage));
        mainPane.setCenter(new GraphScene());


        //Creating a scene object
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add("layoutstyles.css");

        //Setting title to the Stage
        stage.setTitle("Main window");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }


    public MenuBar createMenuBar(final Stage stage) {
        MenuBar menuBar = new MenuBar();
        Menu menu1 = new Menu("File");
        MenuItem item1 = new MenuItem("New file");
        final FileChooser fileChooser = new FileChooser();
        item1.setOnAction(
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        File file = fileChooser.showOpenDialog(stage);
                    }
                }
        );
        menu1.getItems().add(item1);

        Menu menu2 = new Menu("Console");
        MenuItem item2 = new MenuItem("Info");
        item2.setOnAction(
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        Scene scene = new Scene(new InfoScreen(), 200, 200);
                        Stage newStage = new Stage();
                        newStage.setTitle("Information screen");
                        newStage.setScene(scene);
                        newStage.show();
                    }
                }
        );
        menu2.getItems().add(item2);
        menuBar.getMenus().add(menu1);
        menuBar.getMenus().add(menu2);
        return menuBar;
    }


    public static void main(String[] args) {
        launch(args);
    }
}

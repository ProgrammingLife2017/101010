package Window;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by 101010 on 101010.
 */
public class Window extends Application{

    @Override
    public void start(Stage stage) throws Exception{

        BorderPane mainPane = new BorderPane();
        mainPane.setMinSize(1000, 500);
        mainPane.setRight(new InfoScreen());
        mainPane.setTop(createMenuBar());


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


    public MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menu1 = new Menu("File");
        menuBar.getMenus().add(menu1);
        return menuBar;
    }


    public static void main(String[] args) {
        launch(args);
    }
}

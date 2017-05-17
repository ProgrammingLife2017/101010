package screens;

import filesystem.FileSystem;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logging.Logger;
import logging.LoggerFactory;

import java.io.File;

/**
 * Main application.
 */
@SuppressWarnings("FieldCanBeLocal")
public class Window extends Application{

    /**
     * Logger that keeps track of actions executed by this class.
     */
    private static Logger logger;

    /**
     * Factory that creates all loggers.
     */
    private static LoggerFactory loggerFactory;

    /**
     * Backlog window to print all actions.
     */
    private static Backlog backLog;

    /**
     * Starts the frame.
     *
     * @param stage Main stage where the content is placed.
     * @throws Exception Thrown when application can't be started.
     */
    @Override
    public void start(Stage stage) throws Exception{
        this.setupService();
        backLog = getBackLog();
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
        logger.info("the main application has started");
    }

    /**
     * Sets up the necessary services.
     */
    private void setupService() {
        FileSystem fileSystem = new FileSystem();
        loggerFactory = new LoggerFactory(fileSystem);
        logger = loggerFactory.createLogger(this.getClass());
    }

    /**
     * Gets the backlog of this class.
     *
     * @return BackLog object.
     */
    public static Backlog getBackLog() {
        if (backLog == null) {
            return new Backlog();
        }
        return backLog;
    }

    /**
     * Creates the menu bar with its items.
     *
     * @param stage Main stage.
     * @return The menu bar object.
     */
    private MenuBar createMenuBar(final Stage stage) {
        MenuBar menuBar = new MenuBar();
        Menu menu1 = new Menu("File");
        MenuItem item1 = new MenuItem("New file");
        final FileChooser fileChooser = new FileChooser();
        item1.setOnAction(
                event -> {
                    File file = fileChooser.showOpenDialog(stage);
                }
        );
        menu1.getItems().add(item1);

        Menu menu2 = new Menu("Tools");
        MenuItem item2 = new MenuItem("Info");
        item2.setOnAction(
                event -> {
                    Scene scene = new Scene(new InfoScreen(), 200, 200);
                    Stage newStage = new Stage();
                    newStage.setTitle("Information screen");
                    newStage.setScene(scene);
                    newStage.show();
                    logger.info("a new window has been opened");
                }
        );
        MenuItem item3 = new MenuItem("Console log");
        item3.setOnAction(
                event -> {
                    getBackLog().show();
                    logger.info("a new window has been opened");
                }
        );
        menu2.getItems().add(item2);
        menu2.getItems().add(item3);
        menuBar.getMenus().add(menu1);
        menuBar.getMenus().add(menu2);
        return menuBar;
    }

    /**
     * The initialization of the game.
     *
     * @param args the arguments to run.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

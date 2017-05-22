package screens;

import datastructure.NodeGraph;
import filesystem.FileSystem;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logging.Logger;
import logging.LoggerFactory;
import parsing.Parser;
import window.FileSelector;

import java.io.File;

/**
 * Main application.
 */
@SuppressWarnings("FieldCanBeLocal")
public class Window extends Application {

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
    private static Backlog backLog = null;

    /**
     * Pane used for displaying graphs.
     */
    private static GraphScene graphScene;

    /**
     * Window to print information of nodes or edges.
     */
    private static InfoScreen infoScreen = null;

    /**
     * Window for showing the progress bar.
     */
    private static LoadingScreen loadingScreen;

        /**
     * Starts the frame.
     *
     * @param stage Main stage where the content is placed.
     * @throws Exception Thrown when application can't be started.
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.setupService();
        backLog = getBackLog();
        BorderPane mainPane = new BorderPane();

        mainPane.setMinSize(1200, 700);

        mainPane.setTop(createMenuBar(stage));
        mainPane.setCenter(graphScene);


        //Creating a scene object
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add("layoutstyles.css");

        //Setting title to the Stage
        stage.setTitle("Main window");

        //Adding scene to the stage
        stage.setScene(scene);
        stage.setResizable(false);

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
        graphScene = new GraphScene();
        loadingScreen = new LoadingScreen();
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
     * Creates instance of InfoScreen.
     *
     * @return InfoScreen object.
     */
    public static InfoScreen getInfoScreen() {
        if (infoScreen == null) {
            infoScreen = new InfoScreen();
        }
        return infoScreen;
    }

    /**
     * Gets the loading screen window.
     *
     * @return LoadingScreen object.
     */
    public static LoadingScreen getLoadingScreen() {
        return loadingScreen;
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
        item1.setOnAction(
                event -> {
                    File file = FileSelector.showOpenDialog(stage);
                    if (file != null) {
                        NodeGraph.setCurrentInstance(Parser.getInstance().parse(file));
                        graphScene.drawGraph(0, 200);
                        logger.info("file has been selected");
                    }
                }
        );
        menu1.getItems().add(item1);

        Menu menu2 = new Menu("Tools");
        MenuItem item2 = new MenuItem("Info");
        item2.setOnAction(
                event -> {
                    getInfoScreen().show();
                    logger.info("information screen has been opened");
                }
        );
        MenuItem item3 = new MenuItem("Console log");
        item3.setOnAction(
                event -> {
                    getBackLog().show();
                    logger.info("console window has been opened");
                }
        );
        MenuItem item4 = new MenuItem("Center from click");
        item4.setOnAction(
                event -> {
                    graphScene.switchToCenter();
                    logger.info("state has been switched to center");
                }
        );
        MenuItem item5 = new MenuItem("Center from text");
        item5.setOnAction(
                event -> {
                    Stage newstage = new Stage();
                    newstage.setTitle("Select the radius");
                    GridPane box = new GridPane();
                    TextField textField = new TextField();
                    TextField textField2 = new TextField();
                    Button btn = new Button("Submit");
                    btn.setOnAction(
                            event2 -> {
                                graphScene.drawGraph(Integer.parseInt(textField.getText()), Integer.parseInt(textField2.getText()));
                                graphScene.switchToInfo();
                                newstage.close();
                            }
                    );
                    box.add(new Label("Node Id:"), 1, 1);
                    box.add(textField, 1, 2, 3, 1);
                    box.add(new Label("Radius:"), 1, 3);
                    box.add(textField2, 1, 4, 3, 1);
                    box.add(btn, 1, 5);
                    Scene scene = new Scene(box);
                    newstage.setScene(scene);
                    newstage.show();
                    logger.info("state has been switched to centerId");
                }
        );
        Menu menu3 = new Menu("Clear");
        MenuItem item6 = new MenuItem("Info");
        item6.setOnAction(
                event -> {
                    getInfoScreen().getTextArea().clear();
                    logger.info("info screen has been switched to center");
                }
        );
        MenuItem item7 = new MenuItem("Graph");
        item7.setOnAction(
                event -> {
                    graphScene.drawGraph(0, 200);
                    logger.info("drawing returned to original");
                }
        );
        menu2.getItems().add(item2);
        menu2.getItems().add(item3);
        menu2.getItems().add(item4);
        menu2.getItems().add(item5);
        menu3.getItems().addAll(item6, item7);
        menuBar.getMenus().add(menu1);
        menuBar.getMenus().add(menu2);
        menuBar.getMenus().add(menu3);
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

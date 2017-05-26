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
import java.io.IOException;

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
    private static Backlog backLog;

    /**
     * Pane used for displaying graphs.
     */
    private static GraphScene graphScene;

    /**
     * Window to print information of nodes or edges.
     */
    private static InfoScreen infoScreen = null;

    /**
     * Starts the frame.
     * @param stage Main stage where the content is placed.
     * @throws Exception Thrown when application can't be started.
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.setupService();
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
        stage.setOnCloseRequest(event -> {
            try {
                Window.loggerFactory.getFileSystem().closeWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Displaying the contents of the stage
        stage.show();
        logger.info("the main application has started");
    }

    /**
     * Sets up the necessary services.
     */
    private void setupService() {
        FXElementsFactory fact = new FXElementsFactory();
        graphScene = new GraphScene(fact);
        this.backLog = new Backlog(fact);
        this.infoScreen = new InfoScreen(fact);
        FileSystem fileSystem = new FileSystem();
        loggerFactory = new LoggerFactory(fileSystem);
        logger = loggerFactory.createLogger(this.getClass());

    }

    /**
     * Gets the backlog of this class.
     * @return BackLog object.
     */
    public static Backlog getBackLog() {
        return backLog;
    }

    /**
     * Creates instance of InfoScreen.
     * @return InfoScreen object.
     */
    public static InfoScreen getInfoScreen() {
        return infoScreen;
    }


    /**
     * Creates the menu bar with its items.
     * @param stage Main stage.
     * @return The menu bar object.
     */
    private MenuBar createMenuBar(final Stage stage) {
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(addFileSelector(stage));
        menuBar.getMenus().add(addController());
        menuBar.getMenus().add(addClear());
        return menuBar;
    }

    /**
     * Creates a menu for navigating through the file directory.
     * @param stage The container for these GUI nodes.
     * @return Menu object.
     */
    private Menu addFileSelector(Stage stage) {
        Menu menu = new Menu("File");
        MenuItem item = new MenuItem("New file");
        item.setOnAction(
                event -> {
                    File file = FileSelector.showOpenDialog(stage);
                    if (file != null) {
                        NodeGraph.setCurrentInstance(Parser.getInstance().parse(file));
                        graphScene.drawGraph(0, 200);
                        logger.info("file has been selected");
                    }
                }
        );
        menu.getItems().add(item);
        return menu;
    }

    /**
     * Creates a menu that allows interaction with the graph.
     * @return Menu object.
     */
    private Menu addController() {
        Menu menu = new Menu("Tools");
        MenuItem item1 = new MenuItem("Info");
        item1.setOnAction(
                event -> {
                    getInfoScreen().show();
                    logger.info("information screen has been opened");
                }
        );
        MenuItem item2 = new MenuItem("Console log");
        item2.setOnAction(
                event -> {
                    backLog.show();
                    logger.info("console window has been opened");
                }
        );
        MenuItem item3 = new MenuItem("Center from click");
        item3.setOnAction(
                event -> {
                    graphScene.switchToCenter();
                    logger.info("state has been switched to center");
                }
        );
        MenuItem item4 = new MenuItem("Center from text");
        item4.setOnAction(
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
        menu.getItems().add(item1);
        menu.getItems().add(item2);
        menu.getItems().add(item3);
        menu.getItems().add(item4);
        return menu;
    }

    /**
     * Adds a menu that clears the info screen and returns the graph to the original view.
     * @return Menu object.
     */
    private Menu addClear() {
        Menu menu = new Menu("Clear");
        MenuItem item1 = new MenuItem("Info");
        item1.setOnAction(
                event -> {
                    getInfoScreen().getTextArea().clear();
                    logger.info("info screen has been switched to center");
                }
        );
        MenuItem item2 = new MenuItem("Graph");
        item2.setOnAction(
                event -> {
                    graphScene.drawGraph(0, 200);
                    logger.info("drawing returned to original");
                }
        );
        menu.getItems().addAll(item1, item2);
        return menu;
    }

    /**
     * The initialization of the game.
     * @param args the arguments to run.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

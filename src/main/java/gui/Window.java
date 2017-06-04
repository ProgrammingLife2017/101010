package gui;

import datastructure.DrawNode;
import datastructure.NodeGraph;
import filesystem.FileSystem;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logging.Logger;
import logging.LoggerFactory;
import parsing.Parser;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

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
    private static InfoScreen infoScreen;

    private FXElementsFactory fxElementsFactory;

    /**
     * Starts the frame.
     * @param stage Main stage where the content is placed.
     * @throws Exception Thrown when application can't be started.
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.setupService();
        Pane mainPane = createMainPane(stage);
        setScrolling(mainPane);
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add("layoutstyles.css");
        stage.setScene(scene);
        setStageSettings(stage);
        stage.show();
        logger.info("the main application has started");
    }
    /**
     * Sets up the necessary services.
     */
    private void setupService() {
        backLog = new Backlog();
        loggerFactory = new LoggerFactory(new FileSystem());
        logger = loggerFactory.createLogger(this.getClass());
        fxElementsFactory = new FXElementsFactory();
        graphScene = new GraphScene(fxElementsFactory);
        infoScreen = new InfoScreen(fxElementsFactory);
    }

    private BorderPane createMainPane(Stage stage) {
        BorderPane pane = new BorderPane();
        pane.setTop(createMenuBar(stage));
        pane.setCenter(graphScene);
        pane.setRight(infoScreen);
        pane.toBack();
        pane.setMaxWidth(stage.getWidth() - infoScreen.getWidth());
        return pane;
    }

    private void setStageSettings(Stage stage) {
        stage.setTitle("Main window");
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.setOnCloseRequest(event -> {
            try {
                Window.loggerFactory.getFileSystem().closeWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Gets the backlog of this class.
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
     * Sets a scroll event to the pane that handles the zooming of the graph.
     */
    private void setScrolling(Pane pane) {
        pane.setOnScroll((ScrollEvent event) -> {
            if (NodeGraph.getCurrentInstance() != null) {
                double deltaY = event.getDeltaY();
                if (deltaY < 0) {
                    graphScene.zoomOut(event.getX(), event.getY());
                } else {
                    graphScene.zoomIn(event.getX(), event.getY());
                }
                graphScene.toBack();
            }
        });
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
                    if (file != null && file.exists()) {
                        NodeGraph.setCurrentInstance(Parser.getInstance().parse(file));
                        graphScene.drawGraph(0, 200);
                        graphScene.setTranslateX(-NodeGraph.getCurrentInstance().getDrawNodes().getLast().getX());
                        graphScene.setScaleX(graphScene.getWidth() / (NodeGraph.getCurrentInstance().getDrawNodes().getFirst().getBoundsInLocal().getMaxX() - NodeGraph.getCurrentInstance().getDrawNodes().getLast().getX()));
                        LinkedList<DrawNode> drawNodes = NodeGraph.getCurrentInstance().getDrawNodes();
                        graphScene.setTranslateX((-drawNodes.getLast().getX() + graphScene.getWidth() / 2) * graphScene.getScaleX() - graphScene.getWidth() / 2);
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
        MenuItem item2 = new MenuItem("Console log");
        item2.setOnAction(
                event -> {
                    getBackLog().show();
                    logger.info("console window has been opened");
                }
        );
        menu.getItems().addAll(item2);
        return menu;
    }

    /**
     * Adds a menu that clears the info screen and returns the graph to the original view.
     * @return Menu object.
     */
    private Menu addClear() {
        Menu menu = new Menu("Reset");
        MenuItem item = new MenuItem("Graph");
        item.setOnAction(
                event -> {
                    if (NodeGraph.getCurrentInstance() != null) {
                        graphScene.drawGraph(0, 200);
                        logger.info("drawing returned to original");
                    } else {
                        errorPopup("Please load a graph.");
                    }
                }
        );
        menu.getItems().addAll(item);
        return menu;
    }

    /**
     * Creates a popup containing an error message if the user gives invalid input.
     * @param message The error message.
     */
    private void errorPopup(String message) {
        Stage newStage = new Stage();
        Label label = new Label(message);
        Group group = new Group();
        group.getChildren().add(label);
        newStage.setWidth(label.getWidth());
        newStage.setResizable(false);
        newStage.setTitle("Error");
        newStage.initStyle(StageStyle.UTILITY);
        newStage.setAlwaysOnTop(true);
        Scene scene = new Scene(group, label.getMaxWidth(), Math.max(label.getMaxHeight(), 40));
        newStage.centerOnScreen();
        newStage.setScene(scene);
        newStage.show();
    }
    /**
     * The initialization of the game.
     * @param args the arguments to run.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

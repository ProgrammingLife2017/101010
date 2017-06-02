package screens;

import datastructure.DrawNode;
import datastructure.NodeGraph;
import filesystem.FileSystem;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logging.Logger;
import logging.LoggerFactory;
import parsing.Parser;
import window.FileSelector;

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
    private GraphScene graphScene;

    /**
     * Window to print information of nodes or edges.
     */
    private static InfoScreen infoScreen = null;

    private FXElementsFactory factory;

    private double mouseX, mouseY;

    /**
     * A rectangle that shows where the user is in the the graph.
     */
    private static Rectangle indicator;

    /**
     * The main pane of the application window.
     */
    private static BorderPane mainPane;

    /**
     * Starts the frame.
     * @param stage Main stage where the content is placed.
     * @throws Exception Thrown when application can't be started.
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.setupService();
        Pane mainPane = createMainPane(stage);
        Scene scene = createMainScene(mainPane);
        setStageSettings(stage, scene);
        stage.show();
        logger.info("the main application has started");
    }

    public void setPaneEventHandlers(Pane pane) {
        pane.onMousePressedProperty().set(event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });
        pane.onMouseDraggedProperty().set(event -> {
            double offsetX = event.getSceneX() - mouseX;
            graphScene.setTranslateX(graphScene.getTranslateX() + offsetX);
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
            event.consume();
        });
    }

    /**
     * Creates main pane for placing content.
     * @param stage Main stage.
     * @return Pane object.
     */
    private Pane createMainPane(Stage stage) {
        BorderPane mainPane = new BorderPane();
        mainPane.setMinSize(1500, 900);
        mainPane.setTop(createMenuBar(stage));
        mainPane.setCenter(graphScene);
        setPaneEventHandlers(mainPane);
        return mainPane;
    }

    /**
     * Creates the scene where all the main content is placed.
     * @param pane Pane to place in the new scene.
     * @return Scene object.
     */
    private Scene createMainScene(Pane pane) {
        Rectangle indicatorBar = new Rectangle();
        indicator = new Rectangle();
        mainPane.setBottom(indicatorBar);
        mainPane.setBottom(indicator);

        indicatorBar.setWidth(mainPane.getWidth() - 20);
        indicatorBar.setX(10);
        indicatorBar.setY(mainPane.getHeight() - 15);
        indicatorBar.setHeight(10);
        indicatorBar.setFill(Color.GRAY);
        setScrolling();

        //Creating a scene object
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add("layoutstyles.css");
        scene.setOnKeyPressed(event -> {
            if (graphScene != null) {
                keyPressed(event);
            }
        });
        return scene;
    }

    /**
     * Handles event when a key has been pressed.
     * @param event Key event.
     */
    private void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                graphScene.setTranslateX(graphScene.getTranslateX() + 5.0);
                break;
            case RIGHT:
                graphScene.setTranslateX(graphScene.getTranslateX() - 5.0);
                break;
            case UP:
                graphScene.setTranslateY(graphScene.getTranslateY() - 5.0);
                break;
            case DOWN:
                graphScene.setTranslateY(graphScene.getTranslateY() + 5.0);
                break;
            default:
                break;
        }
    }

    /**
     * Sets the scene and other settings.
     * @param stage Stage to show.
     * @param scene Scene where the main content is placed.
     */
    private void setStageSettings(Stage stage, Scene scene) {
        stage.setTitle("Main window");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            try {
                Window.loggerFactory.getFileSystem().closeWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    /**
     * Sets up the necessary services.
     */
    private void setupService() {
        factory = new FXElementsFactory();
        graphScene = new GraphScene(factory);
        this.backLog = new Backlog(factory);
        this.infoScreen = new InfoScreen(factory);
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
     * Sets a scroll event to the pane that handles the zooming of the graph.
     */
    private void setScrolling() {
        mainPane.setOnScroll((ScrollEvent event) -> {
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
    public Menu addFileSelector(Stage stage) {
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
    public Menu addController() {
        Menu menu = new Menu("Tools");
        menu.getItems().addAll(showInfoScreenItem(), showBacklogItem(), centerClickItem(), centerFromTextItem());
        return menu;
    }

    /**
     * Adds a menu that clears the info screen and returns the graph to the original view.
     * @return Menu object.
     */
    public Menu addClear() {
        Menu menu = new Menu("Reset");
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
                    if (NodeGraph.getCurrentInstance() != null) {
                        graphScene.drawGraph(0, 200);
                        logger.info("drawing returned to original");
                    } else {
                        errorPopup("Please load a graph.");
                    }
                }
        );
        menu.getItems().addAll(item1, item2);
        return menu;
    }

    /**
     * Creates menu item to show the information display.
     * @return MenuItem object.
     */
    private MenuItem showInfoScreenItem() {
        MenuItem item = new MenuItem("Info");
        item.setOnAction(
                event -> {
                    getInfoScreen().show();
                    logger.info("information screen has been opened");
                }
        );
        return item;
    }

    /**
     * Creates menu item to open the backlog window.
     * @return MenuItem object.
     */
    private MenuItem showBacklogItem() {
        MenuItem item = new MenuItem("Console log");
        item.setOnAction(
                event -> {
                    getBackLog().show();
                    logger.info("console window has been opened");
                }
        );
        return item;
    }

    /**
     * Creates menu item switch state of the GraphScene object in order to execute a center query.
     * @return MenuItem object.
     */
    private MenuItem centerClickItem() {
        MenuItem item = new MenuItem("Center from click");
        item.setOnAction(
                event -> {
                    if (NodeGraph.getCurrentInstance() != null) {
                        graphScene.switchToCenter();
                        logger.info("state has been switched to center");
                    } else {
                        errorPopup("Please load a graph.");
                    }
                }
        );
        return item;
    }

    /**
     * Creates menu item open a window to perform a center query.
     * @return MenuItem object.
     */
    private MenuItem centerFromTextItem() {
        MenuItem item = new MenuItem("Center from text");
        item.setOnAction(
                event -> {
                    if (NodeGraph.getCurrentInstance() != null) {
                        Stage newstage = new Stage();
                        newstage.setTitle("Select the radius");
                        GridPane box = new GridPane();
                        TextField textField = new TextField();
                        TextField textField2 = new TextField();
                        Button btn = new Button("Submit");
                        btn.setOnAction(
                                event2 -> {
                                    if (textField.getText().length() == 0 || textField.getText().contains("\\D")) {
                                        errorPopup("Please enter a number as id.");
                                    } else if (textField2.getText().length() == 0 || textField2.getText().contains("\\D")) {
                                        errorPopup("Please enter a number as radius.");
                                    } else {
                                        int center = Integer.parseInt(textField.getText());
                                        int radius = Integer.parseInt(textField2.getText());
                                        if (center < 0 || center >= NodeGraph.getCurrentInstance().getSize()) {
                                            errorPopup("Input center id is out of bounds, \nplease provide a different input id.");
                                        } else if (radius < 5 || radius > 500) {
                                            errorPopup("Input radius is out of bounds, \nplease provide a different radius.");
                                        } else {
                                            graphScene.drawGraph(Integer.parseInt(textField.getText()), Integer.parseInt(textField2.getText()));
                                            graphScene.switchToInfo();
                                            newstage.close();
                                        }
                                    }
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
                    } else {
                        errorPopup("Please load a graph.");
                    }
                }
        );
        return item;
    }

    /**
     * Creates a popup containing an error message if the user gives invalid input.
     * @param message The error message.
     */
    private void errorPopup(String message) {
        Stage newStage = factory.createStage();
        Label label = new Label(message);
        Group group = factory.createGroup();
        group.getChildren().add(label);
        newStage.setWidth(label.getWidth());
        newStage.setResizable(false);
        newStage.setTitle("Error");
        newStage.initStyle(StageStyle.UTILITY);
        newStage.setAlwaysOnTop(true);
        Scene scene = new Scene(group, label.getMaxWidth(), Math.max(label.getMaxHeight(), 40));
        newStage.centerOnScreen();
        factory.setScene(newStage, scene);
        factory.show(newStage);
    }
    /**
     * The initialization of the game.
     * @param args the arguments to run.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

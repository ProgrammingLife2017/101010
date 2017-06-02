package screens;

import datastructure.DrawNode;
import datastructure.Node;
import datastructure.NodeGraph;
import filesystem.FileSystem;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
        backLog = new Backlog();
        mainPane = new BorderPane();

        mainPane.setMinSize(1200, 700);

        mainPane.setTop(createMenuBar(stage));
        mainPane.setCenter(graphScene);

        Rectangle indicatorBar = new Rectangle();
        indicator = new Rectangle();
        mainPane.getChildren().add(indicatorBar);
        mainPane.getChildren().add(indicator);

        setScrolling();

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

        indicatorBar.setWidth(mainPane.getWidth() - 20);
        indicatorBar.setX(10);
        indicatorBar.setY(mainPane.getHeight() - 15);
        indicatorBar.setHeight(10);
        indicatorBar.setFill(Color.GRAY);

        logger.info("the main application has started");
    }
    /**
     * Sets up the necessary services.
     */
    private void setupService() {
        FileSystem fileSystem = new FileSystem();
        loggerFactory = new LoggerFactory(fileSystem);
        logger = loggerFactory.createLogger(this.getClass());
        FXElementsFactory fact = new FXElementsFactory();
        graphScene = new GraphScene(fact);
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
        if (infoScreen == null) {
            infoScreen = new InfoScreen();
        }
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
     * @param scene the GraphScene to which the scroll event is added.
     */
    private void setScrolling() {
        mainPane.setOnScroll((ScrollEvent event) -> {
            if (NodeGraph.getCurrentInstance() != null) {
//                int centerId = NavigationInfo.getInstance().getCurrentCenterNode();
//                int oldRadius = NavigationInfo.getInstance().getCurrentRadius();
//                double transX = getTranslate(event.getX(), graphScene.getWidth());
//                double transY = getTranslate(event.getY(), graphScene.getHeight());
                double deltaY = event.getDeltaY();
                if (deltaY < 0) {
//                    if (oldRadius + 2 > 500) {
//                        graphScene.drawGraph(centerId, 500);
//                    } else {
//                        graphScene.setTranslateX((graphScene.getTranslateX() + transX) / 1.02);
//                        graphScene.setTranslateY((graphScene.getTranslateY() + transY) / 1.02);
//                        centerId = findNewCenterNode(centerId);
//                        graphScene.setScaleX(graphScene.getScaleX() / 1.02);
//                        graphScene.setScaleY(graphScene.getScaleY() / 1.02);
//                        //TODO update graph edge
//                        graphScene.drawGraph(centerId, oldRadius + 2);
                    graphScene.zoomOut(event.getX(), event.getY());
                } else {
//                    if (oldRadius - 2 < 5) {
//                        graphScene.drawGraph(centerId, 5);
//                    } else {
//                        graphScene.setTranslateX((graphScene.getTranslateX() - transX) * 1.02);
//                        graphScene.setTranslateY((graphScene.getTranslateY() - transY) * 1.02);
//                        centerId = findNewCenterNode(centerId);
//                        graphScene.setScaleX(graphScene.getScaleX() * 1.02);
//                        graphScene.setScaleY(graphScene.getScaleY() * 1.02);
//                        //TODO update graph edge
//                        graphScene.drawGraph(centerId, oldRadius - 2);
//                    }
                    graphScene.zoomIn();
                }
                graphScene.toBack();
            }
        });
    }

//    private int findNewCenterNode(int currentCenter) {
//        DrawNode currentNode;
//        NodeGraph ng = NodeGraph.getCurrentInstance();
//        double distance = Double.MAX_VALUE;
//        int centerId = -1;
//        for (int i = 0; i < GraphScene.getDrawnNodes().size(); i++) {
//            currentNode = GraphScene.getDrawnNodes().get(i);
//            Bounds bounds = currentNode.getBoundsInParent();
//            double xDiff = bounds.getMinX() + bounds.getWidth() / 2 - graphScene.getWidth() / 2 + graphScene.getTranslateX();
//            double yDiff = bounds.getMinY() + bounds.getHeight() / 2 - graphScene.getHeight() / 2 + graphScene.getTranslateY();
//            double currentDistance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
////            if (GraphScene.getDrawnNodes().get(i).getIndex() == 16 || GraphScene.getDrawnNodes().get(i).getIndex() == 14 || GraphScene.getDrawnNodes().get(i).getIndex() == 18) {
////                System.out.println(GraphScene.getDrawnNodes().get(i).getIndex());
////                System.out.println(bounds);
////                System.out.println(xDiff + "   " + yDiff);
////                System.out.println(currentDistance);
////            }
//            if (distance > currentDistance) {
//                distance = currentDistance;
//                centerId = GraphScene.getDrawnNodes().get(i).getIndex();
//            }
//        }
//        NavigationInfo.getInstance().setCurrentCenterNode(centerId);
//        //System.out.println(graphScene.getTranslateX() + "    " + centerId);
//        //graphScene.setTranslateY(graphScene.getTranslateY() - (ng.getNode(centerId).getY() - ng.getNode(currentCenter).getY()) * graphScene.getScaleY());
//        return centerId;
//    }

    private double getTranslate(double cursorPos, double screenDimension) {
        double change = screenDimension * 0.02;
        return change * cursorPos / screenDimension - change / 2;
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
                        graphScene.setTranslateX(-NodeGraph.getCurrentInstance().getDrawNodes().getLast().getX());
                        logger.info("file has been selected");
                    }
                }
        );
        menu.getItems().add(item);
        return menu;
    }

    public static void updateIndicator(Node center) {
        //double centerX = center.getX();
        //int max = NodeGraph.getCurrentInstance().getMaxX();
        //double relPos = (centerX - 543) / max * mainPane.getWidth() + 5;
        indicator.setHeight(10);
        indicator.setWidth(5);
        //indicator.setX(relPos);
        indicator.setY(mainPane.getHeight() - 15);
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
                    getBackLog().show();
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
                                int radius = Integer.parseInt(textField2.getText());
                                if (radius < 5 || radius > 500) {
                                    Stage newStage = new Stage();
                                    Group group = new Group();
                                    Label label = new Label("Radius is out of bounds");
                                    group.getChildren().add(label);
                                    Scene scene = new Scene(group, 150, 100);
                                    newStage.setScene(scene);
                                    newStage.show();
                                } else {
                                    graphScene.drawGraph(Integer.parseInt(textField.getText()), Integer.parseInt(textField2.getText()));
                                    graphScene.switchToInfo();
                                    newstage.close();
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

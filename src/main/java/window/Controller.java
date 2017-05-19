package window;

import datastructure.DrawNode;
import datastructure.Node;
import datastructure.NodeGraph;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import parsing.Parser;

import java.io.File;
import java.io.IOException;

/**
 * Implementation of the controller.
 */
public class Controller {

    /**
     * The graph we are displaying.
     */
    private NodeGraph graph;

    /**
     * Main frame making up the window.
     */
    @FXML private Pane drawPane;

    /**
     * Button when clicked allows the user to browse to gfa file in directory.
     */
    @FXML private Button browse;

    /**
     * The menu bar.
     */
    @FXML private MenuBar menu;

    /**
     * TextArea used to print information about the running process.
     */
    @FXML private TextArea console;

    /**
     * Event handler for when a node or edge is clicked.
     */
    private EventHandler<MouseEvent> click = event -> {
        if (event.getSource() instanceof Rectangle) {
            DrawNode rect = (DrawNode) (event.getSource());
            console.appendText(NodeGraph.getCurrentInstance().getSegment(rect.getIndex()) + "\n");
        } else if (event.getSource() instanceof Line) {
            Line l = (Line) (event.getSource());
            String edgeNodes = l.getId();
            console.appendText("Edge from node " + edgeNodes.substring(0, edgeNodes.indexOf("-")) + " to " + edgeNodes.substring(edgeNodes.indexOf("-") + 1, edgeNodes.length()) + "\n");
        }
    };

    /**
     * Handling file opening when the open button in the menu bar is clicked.
     * @param event The event holding information about what the user did in the program.
     * @throws IOException An Exception is thrown when the file can not be found.
     */
    @FXML private void openFile(final ActionEvent event) throws IOException {
        Stage stage = (Stage) menu.getScene().getWindow();
        Parent root;
        final Button openButton = new Button("Open");
        File file = FileSelector.showOpenDialog(stage);
        if (file != null) {
            NodeGraph.setCurrentInstance(Parser.getInstance().parse(file));
        }
    }

    /**
     * Handling file opening when browse button is clicked. Also creates the main program window and opens that window.
     * @param event The event holding information about what the user did in the program.
     * @throws IOException An Exception is thrown when the file can not be found.
     */
    @FXML private void handleBrowseButton(final ActionEvent event) throws IOException {
        Stage stage = (Stage) browse.getScene().getWindow();
        Parent root;
        if (event.getSource() == browse) {
            final Button openButton = new Button("Open");
            File file = FileSelector.showOpenDialog(stage);
            if (file != null) {
                stage.setTitle("Graph visualization");
                NodeGraph.setCurrentInstance(Parser.getInstance().parse(file));
                root = null;
                try {
                     root = FXMLLoader.load(getClass().getResource("/window.fxml"));
                } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setScene(new Scene(root, 1600, 900));
            stage.show();
                stage.setResizable(false);
            }
        }
    }

    /**
     * Handling clearing the console when the clear button is clicked in the menu bar.
     * @param event Information about the event.
     */
    @FXML protected void clearConsole(ActionEvent event) {
        console.clear();
    }

    /**
     * Handling exiting the application.
     * @param event Information about the event.
     */
    @FXML protected void exitApp(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Draw method for drawing the graph.
     */
    @FXML
    public void drawGraph() {
        drawPane.getChildren().clear();
        drawGraphUtil(NodeGraph.getCurrentInstance().getNode(0), 200);
    }

    /**
     * The recursive method used to draw all nodes within the radius from the center node.
     * @param center The center node round which should be drawn.
     * @param radius The maximum depth we want to go.
     */
    private void drawGraphUtil(Node center, int radius) {
        double x = center.getX();
        for (int i = 0; i < NodeGraph.getCurrentInstance().getSize(); i++) {
            Node current = NodeGraph.getCurrentInstance().getNode(i);
            if (current.getX() - 543 > NodeGraph.getCurrentInstance().getMaxX()) {
                NodeGraph.getCurrentInstance().setMaxX(current.getX() - 543);
            }
            if (current.getX() >= x - 40 * radius && current.getX() <= x + 40 * radius) {
                DrawNode newRect = new DrawNode(i);
                newRect.setId(Integer.toString(i));
                newRect.setOnMousePressed(click);
                newRect.setX(current.getX() - x + 503);
                newRect.setY(current.getY());
                newRect.setWidth(20);
                newRect.setHeight(10);
                drawPane.getChildren().add(newRect);
                for (Integer j: current.getOutgoingEdges()) {
                    Node out = NodeGraph.getCurrentInstance().getNode(j);
                    Line l = new Line();
                    l.setId(i + "-" + j);
                    l.setStrokeWidth(2);
                    l.setStartX(newRect.getBoundsInLocal().getMaxX());
                    l.setStartY(newRect.getBoundsInLocal().getMinY() + 5);
                    l.setEndX(out.getX()- x + 503);
                    l.setEndY(out.getY() + 5);
                    l.setOnMousePressed(click);
                    drawPane.getChildren().add(l);
                }
            }
        }
        int max = NodeGraph.getCurrentInstance().getMaxX();
        x = x - 543;
        Rectangle scroll = new Rectangle(5, drawPane.getHeight() - 15, drawPane.getWidth() - 10, 10);
        scroll.setFill(Color.GRAY);
        drawPane.getChildren().add(scroll);
        Rectangle position = new Rectangle(5, 10);
        position.setFill(Color.RED);
        double relPos = x / max * scroll.getWidth() + 5;
        System.out.println(x / max);
        position.setX(relPos);
        position.setY(drawPane.getHeight() - 15);
        drawPane.getChildren().add(position);
    }
}

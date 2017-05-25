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

import javafx.scene.layout.Pane;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import parsing.Parser;
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
            DrawNode node = (DrawNode) (event.getSource());
            console.appendText(NodeGraph.getCurrentInstance().getSegment(node.getIndex()) + "\n");
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
            drawPane.getChildren().clear();
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
        drawGraphUtil(NodeGraph.getCurrentInstance().getNode(0), 200);
    }

    /**
     * The recursive method used to draw all nodes within the radius from the center node.
     * @param center The center node round which should be drawn.
     * @param radius The maximum depth we want to go.
     */
    private void drawGraphUtil(Node center, int radius) {
    }
}

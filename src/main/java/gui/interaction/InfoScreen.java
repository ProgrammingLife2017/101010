package gui.interaction;

import datastructure.DrawNode;
import datastructure.NodeGraph;
import gui.FXElementsFactory;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;

/**
 * Implementation of the window that handles showing information of the graph.
 */
public class InfoScreen extends GridPane {

    /**
     * Text area for printing information.
     */
    private final TextArea textArea;

    /**
     * Factory for creating javaFX components.
     */
    private FXElementsFactory fxElementsFactory;

    /**
     * Minimum width of the window.
     */
    private static double MIN_WIDTH = 200d;

    /**
     * Minimum height of the window.
     */
    private static double MIN_HEIGHT = 150d;

    /**
     * Constructor.
     * @param factory Factory for creating javaFX components.
     */
    /*package*/ InfoScreen(FXElementsFactory factory) {
        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setVisible(true);
        textArea.textProperty().addListener(
                (observable, oldValue, newValue) -> textArea.setScrollTop(Double.MAX_VALUE)
        );
        fxElementsFactory = factory;
        screenSettings();
    }

    /**
     * Applies setting to the window.
     */
    private void screenSettings() {
        this.setMinSize(MIN_WIDTH, MIN_HEIGHT);
        this.toFront();
        this.getStyleClass().add("grid");
    }

    /**
     * Displays information of a node.
     * @param node Node object.
     */
    public void displayNodeInfo(DrawNode node) {
        assert NodeGraph.getCurrentInstance() != null;

        this.getChildren().clear();
        textArea.clear();
        NodeGraph graph = NodeGraph.getCurrentInstance();
        Label label1 = fxElementsFactory.createLabel("Graph Information");
        Label label2 = fxElementsFactory.createLabel("Node id: " + Integer.toString(node.getIndex()));
        textArea.appendText(graph.getSegment(node.getIndex()));
        this.add(label1, 1, 1);
        this.add(label2, 1, 2);
        this.add(textArea, 1, 3);
    }

    /**
     * Displays information of a line/edge.
     * @param line Line object.
     */
    public void displayLineInfo(Line line) {
        assert NodeGraph.getCurrentInstance() != null;
        this.getChildren().clear();
        String edgeNodes = line.getId();
        Label label1 = fxElementsFactory.createLabel("Graph Information");
        Label label2 = fxElementsFactory.createLabel("Parent node:" + edgeNodes.substring(0, edgeNodes.indexOf("-")));
        Label label3 = fxElementsFactory.createLabel("Child node: " + edgeNodes.substring(edgeNodes.indexOf("-") + 1, edgeNodes.length()));
        this.add(label1, 1, 1);
        this.add(label2, 1, 2);
        this.add(label3, 1, 3);
    }

}

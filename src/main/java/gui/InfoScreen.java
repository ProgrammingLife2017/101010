package gui;

import datastructure.DrawNode;
import datastructure.NodeGraph;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;

/**
 * Implementation of the window that handles showing information of the graph.
 */
public final class InfoScreen extends GridPane {

    /**
     * Text area for printing information.
     */
    private final TextArea textArea;

    private FXElementsFactory fxElementsFactory;

    private static double MAX_WIDTH = 200d;

    /**
     * Constructor.
     */
    /*package*/ InfoScreen(FXElementsFactory factory) {
        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setVisible(true);
        textArea.textProperty().addListener(
                (observable, oldValue, newValue) -> textArea.setScrollTop(Double.MAX_VALUE)
        );
        fxElementsFactory = factory;
        Label label = fxElementsFactory.createLabel("Graph Information");
        this.add(label, 1, 1);
        screenSettings();
    }

    private void screenSettings() {
        this.setMaxWidth(MAX_WIDTH);
        this.toFront();
        this.setStyle("-fx-border-color: black;");
    }

    public void displayNodeInfo(DrawNode node) {
        assert NodeGraph.getCurrentInstance() != null;

        this.getChildren().clear();
        textArea.clear();
        NodeGraph graph = NodeGraph.getCurrentInstance();
        Label label1 = fxElementsFactory.createLabel("Graph Information");
        Label label2 = fxElementsFactory.createLabel("Node id:" + Integer.toString(node.hashCode()));
        textArea.appendText(graph.getSegment(node.getIndex()) + "\n");
        this.add(label1, 1, 1);
        this.add(label2, 1, 2);
        this.add(textArea, 1, 3, 3, 1);
    }

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

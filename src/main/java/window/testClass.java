package window;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by Michael on 5/30/2017.
 */
public class testClass extends Application {
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // we use a default pane without layout such as HBox, VBox etc.
        final Pane root = new Pane();

        final Scene scene = new Scene(root, 800, 600, Color.rgb(160, 160, 160));

        final int numNodes = 6; // number of nodes to add
        final double spacing = 30; // spacing between nodes

        // add numNodes instances of DraggableNode to the root pane
        for (int i = 0; i < numNodes; i++) {
            DraggableNode node = new DraggableNode();
            node.setPrefSize(98, 80);
            // define the style via css
            node.setStyle(
                    "-fx-background-color: #334488; "
                            + "-fx-text-fill: black; "
                            + "-fx-border-color: black;");
            // position the node
            node.setLayoutX(spacing * (i + 1) + node.getPrefWidth() * i);
            node.setLayoutY(spacing);
            // add the node to the root pane
            root.getChildren().add(node);
        }

        // finally, show the stage
        primaryStage.setTitle("Draggable Node 01");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

/**
 * Simple draggable node.
 * <p>
 * Dragging code based on
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
class DraggableNode extends Pane {

    // node position
    private double x = 0;
    private double y = 0;
    // mouse position
    private double mousex = 0;
    private double mousey = 0;
    private Node view;
    private boolean dragging = false;
    private boolean moveToFront = true;

    public DraggableNode() {
        init();
    }

    public DraggableNode(Node view) {
        this.view = view;

        getChildren().add(view);
        init();
    }

    private void init() {

        onMousePressedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                // record the current mouse X and Y position on Node
                mousex = event.getSceneX();
                mousey = event.getSceneY();

                x = getLayoutX();
                y = getLayoutY();

                if (isMoveToFront()) {
                    toFront();
                }
            }
        });

        //Event Listener for MouseDragged
        onMouseDraggedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                // Get the exact moved X and Y

                double offsetX = event.getSceneX() - mousex;
                double offsetY = event.getSceneY() - mousey;

                x += offsetX;
                y += offsetY;

                double scaledX = x;
                double scaledY = y;

                setLayoutX(scaledX);
                setLayoutY(scaledY);

                dragging = true;

                // again set current Mouse x AND y position
                mousex = event.getSceneX();
                mousey = event.getSceneY();

                event.consume();
            }
        });

        onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                dragging = false;
            }
        });

    }

    /**
     * @return the dragging
     */
    protected boolean isDragging() {
        return dragging;
    }


    /**
     * @return the view
     */
    public Node getView() {
        return view;
    }

    /**
     * @return the moveToFront
     */
    public boolean isMoveToFront() {
        return moveToFront;
    }

    /**
     * @param moveToFront the moveToFront to set
     */
    public void setMoveToFront(boolean moveToFront) {
        this.moveToFront = moveToFront;
    }

    public void removeNode(Node n) {
        getChildren().remove(n);
    }
}

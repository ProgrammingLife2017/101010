package screens.scenes;

import datastructure.NodeGraph;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import screens.Window;
import services.ServiceLocator;

/**
 * Controller class to allow input that will be used for interacting with the graph.
 */
public class Controller extends GridPane {
    /**
     * Labels.
     */
    private Label currentCenter, centerInput, radius;

    /**
     * Input fields.
     */
    private static TextField centerInputField, radiusInputField, currentCenterField;

    /**
     * Submit button to initiate center queries.
     */
    private Button submitButton;

    /**
     * Scene where the graph is drawn.
     */
    private GraphScene graphScene;

    /**
     * Event handler that checks content in the text fields in this object.
     */
    private EventHandler<ActionEvent> buttonAction = event -> {
        if (NodeGraph.getCurrentInstance() != null) {
            checkTextFields();
        }
    };

    /**
     * Constructor.
     * @param serviceLocator ServiceLocator for locating services registered in that object.
     */
    /*package*/ Controller(ServiceLocator serviceLocator) {
        this.graphScene = serviceLocator.getGraphScene();
        currentCenter = serviceLocator.getFxElementsFactory().createLabel("Current center:");
        centerInput = serviceLocator.getFxElementsFactory().createLabel("Search center:");
        radius = serviceLocator.getFxElementsFactory().createLabel("Radius:");
        currentCenterField = new TextField();
        centerInputField = new TextField();
        radiusInputField = new TextField();
        submitButton = new Button("Submit");
        controllerSettings();
        this.add(currentCenter, 1, 1);
        this.add(currentCenterField, 2, 1);
        this.add(centerInput, 1, 2);
        this.add(centerInputField, 2, 2);
        this.add(radius, 1, 3);
        this.add(radiusInputField, 2, 3);
        this.add(submitButton, 1, 4);
        serviceLocator.setController(this);
    }

    /**
     * Settings for the fields and buttons are set.
     */
    private void controllerSettings() {
        centerInputField.setMaxWidth(75d);
        radiusInputField.setMaxWidth(75d);
        currentCenterField.setMaxWidth(75d);
        currentCenterField.setEditable(false);
        submitButton.setOnAction(buttonAction);
        this.getStyleClass().addAll("grid", "border_bottom");
    }

    /**
     * Checks the content of center node field and radius field.
     */
    private void checkTextFields() {
        if (centerInputField.getText().length() == 0 || centerInputField.getText().contains("\\D")
                || radiusInputField.getText().length() == 0 || radiusInputField.getText().contains("\\D")) {
            Window.errorPopup("Please enter an id or radius.");
        } else if (Integer.parseInt(centerInputField.getText()) < 0
                || Integer.parseInt(centerInputField.getText()) >= NodeGraph.getCurrentInstance().getSize()) {
            Window.errorPopup("Input center id is out of bounds, \nplease provide a different input id.");
        } else if (Integer.parseInt(radiusInputField.getText()) < 5
                || Integer.parseInt(radiusInputField.getText()) > 500) {
            Window.errorPopup("Input radius is out of bounds, \nplease provide a different radius.");
        } else {
            graphScene.drawGraph(Integer.parseInt(centerInputField.getText()), Integer.parseInt(radiusInputField.getText()));
            graphScene.switchToInfo();
        }
    }

    /**
     * Returns the radius in its text field.
     * @return Radius.
     */
    public int getRadius() {
        if (radiusInputField.getText().length() == 0 || !radiusInputField.getText().contains("\\D")
                || Integer.parseInt(radiusInputField.getText()) < 5
                || Integer.parseInt(radiusInputField.getText()) > 500) {
            throw new IllegalArgumentException("Radius is invalid.");
        }
        return Integer.parseInt(radiusInputField.getText());
    }

    /**
     * Enters the index of the current center node in its corresponding text field.
     * @param center Index of the center node.
     */
    public void setCurrentCenter(int center) {
        currentCenterField.setText(Integer.toString(center));
    }

}


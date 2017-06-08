package screens.scenes;

import datastructure.Condition;
import datastructure.GenomeCountCondition;
import datastructure.NodeGraph;
import datastructure.RegexCondition;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import screens.GraphInfo;
import screens.Window;
import services.ServiceLocator;

/**
 * Controller class to allow input that will be used for interacting with the graph.
 */
public class Controller extends GridPane {
    /**
     * Labels.
     */
    @SuppressWarnings("FieldCanBeLocal")
    private Label currentCenter, centerInput, radius, genomeNum, genomeRegex, legend;

    /**
     * Input fields.
     */
    private static TextField centerInputField, radiusInputField, currentCenterField, numGenomesField, regexGenomesField;

    /**
     * Submit button to initiate center queries.
     */
    private Button submitButton;

    /**
     * Submit button to add conditional regarding number of genomes.
     */
    private Button submitButtonNoGen;

    /**
     * Submit button to add conditional regarding genome regex.
     */
    private Button submitRegex;

    /**
     * Button to initiate clearing a selected condition.
     */
    private Button condClear;

    /**
     * List of choices for operations on selecting nodes on numbers of genomes.
     */
    private ChoiceBox choices;

    /**
     * ListView where condition are shown.
     */
    private ListView<Label> legendElements;

    /**
     * The conditions that are shown in the ListView.
     */
    private ObservableList<Label> conditionals = FXCollections.observableArrayList();

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
     * Event handler that executes the clear function.
     */
    private EventHandler<ActionEvent> clearCondition = event -> {
        clearColorCondition();
    };

    /**
     * Event handler that adds a regex conditional to the graph.
     */
    private EventHandler<ActionEvent> regexAction = event -> {
        setRegexCondition();
    };

    /**
     * Event handler that adds a conditional on number of genomes.
     */
    private EventHandler<ActionEvent> numGenomeAction = event -> {
        if (numGenomesField.getText().length() == 0 || numGenomesField.getText().contains("\\D")) {
            Window.errorPopup("Please enter a number as number of genomes.");
        } else {
            setGenomeNumberCondition();
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
        genomeNum = serviceLocator.getFxElementsFactory().createLabel("No. of\ngenomes:");
        genomeRegex = serviceLocator.getFxElementsFactory().createLabel("Genome\nregex:");
        legend = serviceLocator.getFxElementsFactory().createLabel("Legend:");
        currentCenterField = new TextField();
        centerInputField = new TextField();
        radiusInputField = new TextField();
        numGenomesField = new TextField();
        regexGenomesField = new TextField();

        submitButton = new Button("Submit");
        submitButtonNoGen = new Button("Submit");
        submitRegex = new Button("Submit");
        condClear = new Button("Clear");
        choices = new ChoiceBox(FXCollections.observableArrayList(">", "<", ">=", "<="));
        legendElements = new ListView();
        legendElements.setItems(null);
        controllerSettings();
        this.add(currentCenter, 1, 1);
        this.add(currentCenterField, 2, 1);
        this.add(centerInput, 1, 2);
        this.add(centerInputField, 2, 2);
        this.add(radius, 1, 3);
        this.add(radiusInputField, 2, 3);
        this.add(submitButton, 1, 4);
        this.add(genomeNum, 1, 6);
        this.add(choices, 2, 6);
        this.add(numGenomesField, 2, 7);
        this.add(submitButtonNoGen, 1, 7);
        this.add(genomeRegex, 1, 9);
        this.add(regexGenomesField, 2, 9);
        this.add(submitRegex, 1, 10);
        this.add(legend, 1, 12);
        this.add(legendElements, 1, 13);
        this.add(condClear, 1, 14);
        serviceLocator.setController(this);
    }

    /**
     * Settings for the fields and buttons are set.
     */
    private void controllerSettings() {
        centerInputField.setMaxWidth(75d);
        radiusInputField.setMaxWidth(75d);
        currentCenterField.setMaxWidth(75d);
        numGenomesField.setMaxWidth(75d);
        regexGenomesField.setMaxWidth(75d);
        currentCenterField.setEditable(false);
        legendElements.setMaxWidth(90d);
        legendElements.setMaxHeight(200d);
        submitButton.setOnAction(buttonAction);
        submitButtonNoGen.setOnAction(numGenomeAction);
        condClear.setOnAction(clearCondition);
        submitRegex.setOnAction(regexAction);
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
     * Creates a new condition from the inputs for selection on number of genomes.
     */
    private void setGenomeNumberCondition() {
        int number = Integer.parseInt(numGenomesField.getText());
        if (number < 0 || number >= GraphInfo.getInstance().getGenomesNum()) {
            Window.errorPopup("Input number is out of bounds, \nplease provide a different input between 0 and : " + GraphInfo.getInstance().getGenomesNum() + ".");
        } else {
            int index = choices.getSelectionModel().getSelectedIndex();
            GenomeCountCondition gcc;
            Label cond;
            switch (index) {
                case 0:
                    cond = new Label("Num >" + number);
                    if (checkDuplicateCondition("Num >" + number)) {
                        return;
                    }
                    Color color = GraphInfo.getInstance().determineColor();
                    cond.setTextFill(color);
                    gcc = new GenomeCountCondition(number, true, false, color);
                    GraphInfo.getInstance().addCondition(gcc);
                    conditionals.add(cond);
                    break;
                case 1:
                    cond = new Label("Num <" + number);
                    if (checkDuplicateCondition("Num <" + number)) {
                        return;
                    }
                    Color color1 = GraphInfo.getInstance().determineColor();
                    cond.setTextFill(color1);
                    gcc = new GenomeCountCondition(number, false, false, color1);
                    GraphInfo.getInstance().addCondition(gcc);
                    conditionals.add(cond);
                    break;
                case 2:
                    cond = new Label("Num >=" + number);
                    if (checkDuplicateCondition("Num >=" + number)) {
                        return;
                    }
                    Color color2 = GraphInfo.getInstance().determineColor();
                    cond.setTextFill(color2);
                    gcc = new GenomeCountCondition(number, true, true, color2);
                    GraphInfo.getInstance().addCondition(gcc);
                    conditionals.add(cond);
                    break;
                case 3:
                    cond = new Label("Num <=" + number);
                    if (checkDuplicateCondition("Num <=" + number)) {
                        return;
                    }
                    Color color3 = GraphInfo.getInstance().determineColor();
                    cond.setTextFill(color3);
                    gcc = new GenomeCountCondition(number, false, true, color3);
                    GraphInfo.getInstance().addCondition(gcc);
                    conditionals.add(cond);
                    break;
                default:
                    Window.errorPopup("Please select a constraint.");
                    break;
            }
            legendElements.setItems(conditionals);
            graphScene.drawConditions();
        }
    }

    /**
     * Creates a new condition from the inputs for regex on genome names.
     */
    private void setRegexCondition() {
        String regex = regexGenomesField.getText();
        Label cond = new Label("Regex: " + regex);
        if (checkDuplicateCondition("Regex: " + regex)) {
            return;
        }
        Color color = GraphInfo.getInstance().determineColor();
        RegexCondition regCond = new RegexCondition(regex, color);
        GraphInfo.getInstance().addCondition(regCond);
        cond.setTextFill(color);
        conditionals.add(cond);
        legendElements.setItems(conditionals);
        graphScene.drawConditions();
    }

    /**
     * Removes the currently selected condition in the legend.
     */
    private void clearColorCondition() {
        Label remove = legendElements.getSelectionModel().getSelectedItem();
        conditionals.remove(remove);
        Color c = (Color) remove.getTextFill();
        GraphInfo.getInstance().addColor(c);
        ArrayList<Condition> conditions = GraphInfo.getInstance().getConditions();
        for (int i = 0; i < conditions.size(); i++) {
            if (conditions.get(i).getColor().equals(c)) {
                conditions.remove(i);
            }
        }
        legendElements.setItems(conditionals);
        graphScene.drawConditions();
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

    /**
     * Checks whether the condition that will be added was already added before.
     * @param label the String label the condition will get in the legend.
     * @return whether the condition already is checked for.
     */
    private boolean checkDuplicateCondition(String label) {
        for (int i = 0; i < conditionals.size(); i++) {
            if (conditionals.get(i).getText().equals(label)) {
                Window.errorPopup("This conditional is already checked.");
                return true;
            }
        }
        return false;
    }
}


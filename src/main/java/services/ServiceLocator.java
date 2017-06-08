package services;

import filesystem.FileSystem;
import logging.LoggerFactory;
import screens.Backlog;
import screens.FXElementsFactory;
import screens.FileSelector;
import screens.scenes.Controller;
import screens.scenes.GraphScene;
import screens.scenes.InfoScreen;
import screens.scenes.InteractionScene;

/**
 * Implementation of container class that contains references to other services.
 */
public class ServiceLocator {
    // Screens
    /**
     * Backlog object.
     */
    private Backlog backlog;

    /**
     * File selector to select files from directory.
     */
    private FileSelector fileSelector;

    /**
     * controller object for interacting with graphs.
     */
    private Controller controller;

    /**
     * Scene where the graph is drawn in.
     */
    private GraphScene graphScene;

    /**
     * Scene to display graph information.
     */
    private InfoScreen infoScreen;

    /**
     * Container object for all interaction objects.
     */
    private InteractionScene interactionScene;

    /**
     * JavaFX components factory.
     */
    private FXElementsFactory fxElementsFactory;

    /**
     * Factory for creating loggers.
     */
    private LoggerFactory loggerFactory;

    /**
     * Object to write to logger files.
     */
    private FileSystem fileSystem;

    /**
     * Constructor.
     */
    public ServiceLocator() {
        init();
    }

    /**
     * Initialize all necessary services.
     */
    public void init() {
        FileSystem.register(this);
        FXElementsFactory.register(this);
        Backlog.register(this);
        LoggerFactory.register(this);
        FileSelector.register(this);

        GraphScene.register(this);
        InteractionScene.register(this);
    }

    /**
     * Sets the backlog to be used by other objects.
     * @param bL Backlog object.
     */
    public void setBacklog(Backlog bL) {
        assert bL != null;
        backlog = bL;
    }

    /**
     * Sets the file selector to be used by other objects.
     * @param fS File selector object.
     */
    public void setFileSelector(FileSelector fS) {
        assert fS != null;
        fileSelector = fS;
    }

    /**
     * Sets the controller object to be used by other objects.
     * @param control Controller object.
     */
    public void setController(Controller control) {
        assert control != null;
        controller = control;
    }

    /**
     * Sets the scene where graphs are drawn.
     * @param gS GraphScene object.
     */
    public void setGraphScene(GraphScene gS) {
        assert gS != null;
        graphScene = gS;
    }

    /**
     * Sets the display where graph information is shown.
     * @param iS InfoScreen object.
     */
    public void setInfoScreen(InfoScreen iS) {
        assert iS != null;
        infoScreen = iS;
    }

    /**
     * Sets the container object that holds all scenes for interacting with graphs.
     * @param interaction InteractionScene object.
     */
    public void setInteractionScene(InteractionScene interaction) {
        assert interaction != null;
        interactionScene = interaction;
    }

    /**
     * Sets the JavaFX component factory for other objects to use.
     * @param factory FXElementsFactory object.
     */
    public void setFxElementsFactory(FXElementsFactory factory) {
        assert factory != null;
        fxElementsFactory = factory;
    }

    /**
     * Sets the logger factory to create loggers of other objects.
     * @param lF LoggerFactory object.
     */
    public void setLoggerFactory(LoggerFactory lF) {
        assert lF != null;
        loggerFactory = lF;
    }

    /**
     * Sets the object that handles writing to logger files.
     * @param fS FileSystem object.
     */
    public void setFileSystem(FileSystem fS) {
        assert fS != null;
        fileSystem = fS;
    }

    /**
     * Gets the backlog
     * @return Backlog object.
     */
    public Backlog getBacklog() {
        return backlog;
    }

    /**
     * Gets the file selector
     * @return FileSelector object.
     */
    public FileSelector getFileSelector() {
        return fileSelector;
    }

    /**
     * Gets the controller
     * @return Controller for interacting with graphs.
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Gets the graph scene.
     * @return Scene where graphs are drawn.
     */
    public GraphScene getGraphScene() {
        return graphScene;
    }

    /**
     * Gets the information scene.
     * @return scene for graph information display.
     */
    public InfoScreen getInfoScreen() {
        return infoScreen;
    }

    /**
     * Gets the interaction scene.
     * @return Scene for interacting with the graph.
     */
    public InteractionScene getInteractionScene() {
        return interactionScene;
    }

    /**
     * Gets the JavaFX component factory.
     * @return JavaFX component factory.
     */
    public FXElementsFactory getFxElementsFactory() {
        return fxElementsFactory;
    }

    /**
     * Gets the logger factory.
     * @return Logger factory object.
     */
    public LoggerFactory getLoggerFactory() {
        return loggerFactory;
    }

    /**
     * Gets the file system.
     * @return FileSystem object.
     */
    public FileSystem getFileSystem() {
        return fileSystem;
    }

}

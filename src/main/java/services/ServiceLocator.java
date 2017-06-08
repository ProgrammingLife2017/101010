package services;

import datastructure.NodeGraph;
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
 * Created by Michael on 6/8/2017.
 */
public class ServiceLocator {
    // Screens
    private Backlog backlog;

    private FileSelector fileSelector;

    private Controller controller;

    private GraphScene graphScene;

    private InfoScreen infoScreen;

    private InteractionScene interactionScene;

    private FXElementsFactory fxElementsFactory;

    // NodeGraph

    private NodeGraph nodeGraph;

    private LoggerFactory loggerFactory;

    private FileSystem fileSystem;

    public ServiceLocator() {
        init();
    }

    public void init() {
        FileSystem.register(this);
        FXElementsFactory.register(this);
        Backlog.register(this);
        LoggerFactory.register(this);
        FileSelector.register(this);

        GraphScene.register(this);
        InteractionScene.register(this);
    }



    public void setBacklog(Backlog bL) {
        assert bL != null;
        backlog = bL;
    }

    public void setFileSelector(FileSelector fS) {
        assert fS != null;
        fileSelector = fS;
    }

    public void setController(Controller control) {
        assert control != null;
        controller = control;
    }

    public void setGraphScene(GraphScene gS) {
        assert gS != null;
        graphScene = gS;
    }

    public void setInfoScreen(InfoScreen iS) {
        assert iS != null;
        infoScreen = iS;
    }

    public void setInteractionScene(InteractionScene interaction) {
        assert interaction != null;
        interactionScene = interaction;
    }

    public void setFxElementsFactory(FXElementsFactory factory) {
        assert factory != null;
        fxElementsFactory = factory;
    }

    public void setLoggerFactory(LoggerFactory lF) {
        assert lF != null;
        loggerFactory = lF;
    }

    public void setFileSystem(FileSystem fS) {
        assert fS != null;
        fileSystem = fS;
    }

    public Backlog getBacklog() {
        return backlog;
    }

    public FileSelector getFileSelector() {
        return fileSelector;
    }

    public Controller getController() {
        return controller;
    }

    public GraphScene getGraphScene() {
        return graphScene;
    }

    public InfoScreen getInfoScreen() {
        return infoScreen;
    }

    public InteractionScene getInteractionScene() {
        return interactionScene;
    }

    public FXElementsFactory getFxElementsFactory() {
        return fxElementsFactory;
    }

    public LoggerFactory getLoggerFactory() {
        return loggerFactory;
    }

    public FileSystem getFileSystem() {
        return fileSystem;
    }

}

package window;

import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

/**
 * Implementation of window for selecting files in the directory.
 */
public final class FileSelector {

    /**
     * Instance of this singleton class.
     */
    private static FileChooser instance = null;

    /**
     * The last directory accessed during file selection.
     */
    private static SimpleObjectProperty<File> lastDir = new SimpleObjectProperty<>();

    /**
     * Private constructor.
     */
    private FileSelector() { }

    /**
     * Getter for the instance of this singleton class.
     * @return The instance of this class.
     */
    public static FileChooser getInstance() {
        if (instance == null) {
            instance = new FileChooser();
            instance.initialDirectoryProperty().bindBidirectional(lastDir);
            //Set the FileExtensions you want to allow
            instance.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("gfa files (*.gfa)", "*.gfa"));
        }
        return instance;
    }

    /**
     * Non-default showOpenDialog. This method lets the user select the file they want to have visualized.
     * @param ownerWindow The stage from which this method is called.
     * @return The file we want to parse.
     */
    public static File showOpenDialog(Window ownerWindow) {
        File chosenFile = getInstance().showOpenDialog(ownerWindow);
        if (chosenFile != null) {
            lastDir.setValue(chosenFile.getParentFile());
        }
        return chosenFile;
    }
}

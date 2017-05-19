package window;

import javafx.stage.FileChooser;
import java.io.File;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.Window;

/**
 * Created by 101010 on 16-5-2017.
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
     * Default showOpenDialog method which invokes the non-default one with parameter null.
     * @return The file we want to parse.
     */
    public static File showOpenDialog() {
        return showOpenDialog(null);
    }

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

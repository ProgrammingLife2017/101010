package window;

import javafx.stage.FileChooser;
import java.io.File;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.Window;

/**
 * Created by Martijn on 16-5-2017.
 */
public class FileSelector {
    private static FileChooser instance = null;
    private static SimpleObjectProperty<File> lastDir = new SimpleObjectProperty<>();

    private FileSelector() {}

    public static File showOpenDialog() {
        return showOpenDialog(null);
    }

    public static FileChooser getInstance() {
        if (instance == null) {
            instance = new FileChooser();
            instance.initialDirectoryProperty().bindBidirectional(lastDir);
            //Set the FileExtensions you want to allow
            instance.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("gfa files (*.gfa)", "*.gfa"));
        }
        return instance;
    }

    public static File showOpenDialog(Window ownerWindow){
        File chosenFile = getInstance().showOpenDialog(ownerWindow);
        if(chosenFile != null){
            //Set the property to the directory of the chosenFile so the fileChooser will open here next
            lastDir.setValue(chosenFile.getParentFile());

        }
        return chosenFile;
    }

    public static File showSaveDialog(){
        return showSaveDialog(null);
    }

    public static File showSaveDialog(Window ownerWindow){
        File chosenFile = getInstance().showSaveDialog(ownerWindow);
        if(chosenFile != null){
            //Set the property to the directory of the chosenFile so the fileChooser will open here next
            lastDir.setValue(chosenFile.getParentFile());
        }
        return chosenFile;
    }
}

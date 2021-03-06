package screens;


import javafx.stage.FileChooser;
import javafx.stage.Window;
import logging.Logger;
import services.ServiceLocator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Implementation of window for selecting files in the directory.
 */
public final class FileSelector {

    /**
     * Instance of this singleton class.
     */
    private static FileChooser instance = null;

    /**
     * Logger to log events while selecting a file.
     */
    private static Logger logger;

    /**
     * Contains references to other services.
     */
    private static ServiceLocator serviceLocator;

    /**
     * Private constructor.
     */
    private FileSelector() { }

    /**
     * Register a reference of this object in the service locator.
     * @param sL container of references to other services
     */
    public static void register(ServiceLocator sL) {
        if (sL == null) {
            throw new IllegalArgumentException("The service locator can not be null");
        }
        serviceLocator = sL;
        FileSelector.serviceLocator.setFileSelector(new FileSelector());
    }

    /**
     * Getter for the instance of this singleton class.
     * @return The instance of this class.
     */
    private static FileChooser getInstance() {
        if (instance == null) {
            instance = new FileChooser();
            instance.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("gfa files (*.gfa)", "*.gfa"));
        }
        return instance;
    }

    /**
     * Non-default showOpenDialog. This method lets the user select the file they want to have visualized.
     * @param ownerWindow The stage from which this method is called.
     * @return The file we want to parse.
     */
    public File showOpenDialog(Window ownerWindow) {
        logger = serviceLocator.getLoggerFactory().createLogger(FileSelector.class);
        String currentDir = getDirectory();
        if (!currentDir.equals("")) {
            File file = new File(currentDir);
            if (file.exists()) {
                getInstance().setInitialDirectory(file);
            }
        }
        File chosenFile = getInstance().showOpenDialog(ownerWindow);
        if (chosenFile != null && chosenFile.exists()) {
            logger.info("Selected file: " + chosenFile.getName());
        } else if (chosenFile != null) {
            logger.info("The selected file does not exist.");
        } else {
            logger.info("No file was selected.");
        }

        if (chosenFile != null) {
            saveDirectory(chosenFile.getParentFile().getAbsolutePath());
        }
        return chosenFile;
    }

    /**
     * Finds the last used directory from which a file was chosen.
     * @return the path of the directory.
     */
    private static String getDirectory() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("directory.txt")));
            String dir = br.readLine();
            br.close();
            return dir;
        } catch (Exception e) {
            logger.info("No directory path was found.");
            return "";
        }
    }

    /**
     * Saves the last directory from which a file was chosen.
     * @param directory the path of the directory that will be saved.
     */
    private static void saveDirectory(String directory) {
        try {
            File file = new File("directory.txt");
            OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            ow.write(directory);
            logger.info("Directory saved as: " + directory);
            ow.close();
        } catch (Exception e) {
            logger.error("Exception thrown when trying to read the directory.");
            e.printStackTrace();
        }
    }
}

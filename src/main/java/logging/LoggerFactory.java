package logging;

import filesystem.FileSystem;

/**
 * Created by Michael on 5/16/2017.
 */
public final class LoggerFactory {

    private final ILogger logger;

    private final FileSystem fileSystem;


   public LoggerFactory(FileSystem fs) {
        fileSystem = fs;
        fileSystem.clearFile(FileSystem.LOGFILE_NAME);
        logger = new Logger(this.getClass(), fileSystem);
        logger.info("a new logger factory has been created");
   }

    public ILogger createLogger(final Class<?> cl) {
        return new Logger(cl, fileSystem);

    }
}

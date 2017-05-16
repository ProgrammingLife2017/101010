package logging;

import filesystem.FileSystem;

/**
 * Created by Michael on 5/16/2017.
 */
public final class LoggerFactory {
    /**
     * Logger that keeps track of actions executed by the factory.
     */
    private final ILogger logger;

    /**
     * FileSystem for writing to file.
     */
    private final FileSystem fileSystem;

    /**
     * Constructor.
     *
     * @param fs FileSystem for writing to file.
     */
   public LoggerFactory(FileSystem fs) {
        fileSystem = fs;
        fileSystem.clearFile(FileSystem.LOGFILE_NAME);
        logger = new Logger(this.getClass(), fileSystem);
        logger.info("a new logger factory has been created");
   }

    /**
     * Creates a new logger that keeps track of actions performed by the given class.
     *
     * @param cl Class that the new logger serves.
     * @return A new logger object.
     */
    public ILogger createLogger(final Class<?> cl) {
        return new Logger(cl, fileSystem);

    }
}

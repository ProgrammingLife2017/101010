package logging;

import filesystem.FileSystem;

/**
 * Implementation of the factory that creates loggers for other classes.
 */
public final class LoggerFactory {
    /**
     * Logger that keeps track of actions executed by the factory.
     */
    @SuppressWarnings("FieldCanBeLocal")
    private final Logger logger;

    /**
     * FileSystem for writing to file.
     */
    private final FileSystem fileSystem;

    /**
     * Constructor.
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
     * @param cl Class that the new logger serves.
     * @return A new logger object.
     */
    public Logger createLogger(final Class<?> cl) {
        logger.info("a new logger has been created");
        return new Logger(cl, fileSystem);
    }

    /**
     * FileSystem get method.
     * @return FileSystem object.
     */
    public FileSystem getFileSystem() {
        return fileSystem;
    }
}

package logging;

import filesystem.FileSystem;
import services.ServiceLocator;

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

    private static ServiceLocator serviceLocator;

    /**
     * Constructor.
     * @param fs FileSystem for writing to file.
     */
   private LoggerFactory(FileSystem fs) {
        fileSystem = fs;
        fileSystem.clearFile();
        logger = new Logger(this.getClass(), fileSystem, serviceLocator);
        logger.info("a new logger factory has been created");
   }

   public static void register(ServiceLocator sL) {
       if(sL == null) {
           throw new IllegalArgumentException("The service locator can not be null");
       }
       serviceLocator = sL;
       LoggerFactory.serviceLocator.setLoggerFactory(new LoggerFactory(serviceLocator.getFileSystem()));
   }

    /**
     * Creates a new logger that keeps track of actions performed by the given class.
     * @param cl Class that the new logger serves.
     * @return A new logger object.
     */
    public Logger createLogger(final Class<?> cl) {
        logger.info("a new logger has been created");
        return new Logger(cl, fileSystem, serviceLocator);
    }

    /**
     * FileSystem get method.
     * @return FileSystem object.
     */
    public FileSystem getFileSystem() {
        return fileSystem;
    }
}

package logging;

/**
 * An interface for the logger.
 */
public interface ILogger {

    /**
     * Print an error to the log.
     *
     * @param msg The message to the log.
     */
    public void error(String msg);

    /**
     * Print an error from an exception to the log.
     * @param error The message to the log.
     */
    public void error(Exception error);


    /**
     * Print information to the log.
     * @param msg The message to the log.
     */
    public void info(String msg);
}

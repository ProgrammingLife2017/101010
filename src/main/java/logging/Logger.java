package logging;

import filesystem.FileSystem;
import screens.Window;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of the logger that writes to a log file.
 */
public final class Logger {

    /**
     * Reference to the class of the logger.
     */
    private final Class cl;

    /**
     * Reference to the FileSystem to write.
     */
    private final FileSystem fileSystem;

    /**
     * The ThreadPoolExecutor is responsible for executing all logging code on a separate thread to prevent stalling.
     */
    private static final ThreadPoolExecutor LOGGING_THREAD_EXECUTOR = new ThreadPoolExecutor(
            0, 50000, 60L, TimeUnit.SECONDS, new SynchronousQueue<>()
    );

    /**
     * Constructor.
     *
     * @param targetClass The class that this logger is serving.
     * @param fs FileSystem object to write with.
     */
    public Logger(final Class targetClass, FileSystem fs) {
        this.cl = targetClass;
        this.fileSystem = fs;
    }

//    /**
//     * Print an error to the log.
//     *
//     * @param msg The message to the log.
//     */
//    public void error(final String msg) {
//        String str = this.generateMessage("ERROR", msg);
//        this.appendStringToTextFile(str);
//    }
//
//    /**
//     * Print an error from an exception to the log.
//     * @param exception The message to the log.
//     */
//    public void error(final Exception exception) {
//        String str = this.generateMessage("ERROR", exception.getMessage());
//        this.appendStringToTextFile(str);
//    }

    /**
     * Print information to the log.
     * @param msg The message to the log.
     */
    public void info(final String msg) {
        String str = this.generateMessage("INFO", msg);
        this.appendStringToTextFile(str);
    }

    /**
     * Write message at the end of file.
     *
     * @param str message to write.
     */
    private void appendStringToTextFile(final String str) {
        Window.getBackLog().printContent(str);
        Runnable runnable = () -> this.fileSystem.log(str);
        Logger.LOGGING_THREAD_EXECUTOR.execute(runnable);

        long submitted = Logger.LOGGING_THREAD_EXECUTOR.getTaskCount();
        long completed = Logger.LOGGING_THREAD_EXECUTOR.getCompletedTaskCount();
        long notCompleted = submitted - completed;

        this.fileSystem.log("Pending logging tasks: " + notCompleted);
    }

    /**
     * Generate the full message to log.
     *
     * @param type The type of message.
     * @param msg  The message to log.
     * @return The generated message.
     */
    private String generateMessage(String type, String msg) {
        Date date = new Date();
        return new Timestamp(date.getTime()) +
                " | ORIGIN: '" + this.cl.getName() + "' | " + type + ": '" + msg + "'";
    }

}

package logging;

import javax.annotation.Nonnull;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Michael on 5/15/2017.
 */
public final class Logger implements ILogger {

    private final Class cl;

    private final Writer logWriter;


    private static final ThreadPoolExecutor LOGGING_THREAD_EXECUTOR = new ThreadPoolExecutor(
            0, 50000, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>()
    );

    public Logger(final Class targetClass, String logFile) {
        this.cl = targetClass;
        File file = new File(logFile);

        Writer fw = new Writer() {
            public void write(@Nonnull final char[] cbuf, final int off, final int len) throws IOException {}

            public void flush() throws IOException {}

            public void close() throws IOException {}
        };

        try {
            fw = new OutputStreamWriter(new FileOutputStream(file));

        } catch (IOException e) {
            e.printStackTrace();
        }
        logWriter = new BufferedWriter(fw);
    }


    public void error(final String msg) {
        String str = this.generateMessage("ERROR", msg);
        this.appendStringToTextFile(str);
    }

    public void error(final Exception exception) {
        String str = this.generateMessage("ERROR", exception.getMessage());
        this.appendStringToTextFile(str);
    }

    public void info(final String msg) {
        String str = this.generateMessage("INFO", msg);
        this.appendStringToTextFile(str);
    }

    public void log(String content) {
        try {
            this.logWriter.write(content + "\n");
            this.logWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void appendStringToTextFile(final String str) {
        Runnable runnable = () -> this.log(str);
        Logger.LOGGING_THREAD_EXECUTOR.execute(runnable);

        long submitted = Logger.LOGGING_THREAD_EXECUTOR.getTaskCount();
        long completed = Logger.LOGGING_THREAD_EXECUTOR.getCompletedTaskCount();
        long notCompleted = submitted - completed;

        this.log("Pending logging tasks: " + notCompleted);
    }

    private String generateMessage(String type, String msg) {
        Date date = new Date();
        return new Timestamp(date.getTime()) +
                " | ORIGIN: '" + this.cl.getName() + "' | " + type + ": '" + msg + "'";
    }
}

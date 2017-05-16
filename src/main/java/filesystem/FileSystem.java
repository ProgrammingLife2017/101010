package filesystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * Created by Michael on 5/16/2017.
 */
public final class FileSystem {

    private final Writer logWriter;
    public static String LOGFILE_NAME = "logger.log";

    public FileSystem() {
        Writer fw = new Writer() {
            @Override
            public void write(final char[] cbuf, final int off, final int len) throws IOException {
            }

            @Override
            public void flush() throws IOException {
            }

            @Override
            public void close() throws IOException {
            }
        };
        File logFile = new File(FileSystem.LOGFILE_NAME);

        try {
            fw = new OutputStreamWriter(new FileOutputStream(logFile), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.logWriter = new BufferedWriter(fw);
    }

    public void log(String content) {
        try {
            this.logWriter.write(content + "\n");
            this.logWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void clearFile(final String filename) {
        File file = null;
        try {
            file = this.getProjectFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert file != null;
        try (final OutputStream outputStream = new FileOutputStream(file);
             final Writer w = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
             final PrintWriter pw = new PrintWriter(w, false)) {
            pw.flush();
            pw.close();
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getProjectFile(final String filename) throws IOException {
        File file = new File(filename);
        // The only reason we do this is to suppress a FindBugs warning
        final boolean didntExist = file.createNewFile();
        if (didntExist) {
            System.out.println("New file called \"" + filename + "\" created");
        }

        return new File(filename);
    }
}

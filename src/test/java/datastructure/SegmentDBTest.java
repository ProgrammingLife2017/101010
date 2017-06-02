package datastructure;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by 101010.
 */
public class SegmentDBTest {
    @Test
    public void getSegment() throws Exception {
        String workingDirectory = System.getProperty("user.dir");

        String absoluteFilePath = workingDirectory + File.separator + "/src/test/resources/testSegmentDB.txt";
        File file = new File(absoluteFilePath);
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write("A\nB\nC\nD\nE");
        bw.close();
        SegmentDB segments = new SegmentDB(absoluteFilePath);
        assertEquals("A", segments.getSegment(0));
        assertEquals("B", segments.getSegment(1));
        assertEquals("C", segments.getSegment(2));
        assertEquals("D", segments.getSegment(3));
        assertEquals("E", segments.getSegment(4));
        file.delete();
    }

}
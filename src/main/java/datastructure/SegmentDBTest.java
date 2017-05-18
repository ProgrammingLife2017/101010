package datastructure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by 101010.
 */
class SegmentDBTest {
    private SegmentDB data;

    @BeforeEach
    void setUp() {
        String workingDirectory = System.getProperty("user.dir");

        String absoluteFilePath = workingDirectory + File.separator + "test" + File.separator + "testSegments.txt";
        data = new SegmentDB(absoluteFilePath);
    }

    @AfterEach
    void tearDown() {
        data = null;
    }

    @Test
    void getSegment() {
        assertEquals("A", data.getSegment(0));
        assertEquals("T", data.getSegment(1));
        assertEquals("C", data.getSegment(2));
        assertEquals("G", data.getSegment(3));
        assertEquals("N", data.getSegment(4));


    }

}
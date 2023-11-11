import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;


public class FileManagerTest {
    private static final String TEST_FILE_PATH = "testFile.txt";
    private static final String EMPTY_FILE_PATH = "emptyTestFile.txt";
    private static final String TEST_CONTENT = "Hello, world!"+System.lineSeparator()+"This is a test.";
    private static final String NON_EXISTENT_FILE_PATH = "nonExistentFile.txt";

    @BeforeAll
    static void setup() throws IOException {
        FileManager.write(TEST_FILE_PATH, TEST_CONTENT);
        FileManager.write(EMPTY_FILE_PATH, ""); // Creating an empty file
    }

    @Test
    void testFileWritingAndReading() throws IOException {
        String readContent = FileManager.read(TEST_FILE_PATH);
        assertEquals(TEST_CONTENT, readContent.trim(), "The read content should match the written content.");
    }
    @Test
    void testEmptyFileReading() throws IOException {
        String readContent = FileManager.read(EMPTY_FILE_PATH);
        assertTrue(readContent.isEmpty(), "The content of an empty file should be empty.");
    }

    @Test
    void testNonExistentFileReading() {
        assertThrows(IOException.class, () -> FileManager.read(NON_EXISTENT_FILE_PATH),
                "Reading a non-existent file should throw an IOException.");
    }

    @AfterAll
    static void cleanup() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE_PATH));
    }
}

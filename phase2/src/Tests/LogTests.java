package Tests;

import model.Log;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class LogTests {
    @Rule public TemporaryFolder folder = new TemporaryFolder();

    private Log log;
    private final String LOG_FILE_NAME = "log";

    @Before
    public void Before() throws IOException {
        log = new Log(new File(folder.getRoot(), LOG_FILE_NAME));
    }

    @Test
    public void testLogFileIsCreated() {
        File expectedResult = new File(folder.getRoot(), "." + LOG_FILE_NAME + ".log");
        assertTrue(expectedResult.exists());
    }

    @Test
    public void testLogFileIsRenamed() {
        String newName = "newName";
        log.rename("lastName", "newName", "newName");
        File expectedResult = new File(folder.getRoot(), ".newName.log");
        File shouldntExist = new File(folder.getRoot(), "." + LOG_FILE_NAME + ".log");
        assertTrue(expectedResult.exists());
        assertFalse(shouldntExist.exists());
    }
}

package Tests;

import model.Log;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class LogTests {
  @Rule public TemporaryFolder folder = new TemporaryFolder();

  private Log log;
  private final String LOG_FILE_NAME = "log";
  private final String LOG_FILE_FULL_NAME = "." + LOG_FILE_NAME + ".log";

  @Before
  public void Before() throws IOException {
    log = new Log(new File(folder.getRoot(), LOG_FILE_NAME));
  }

  @Test
  public void testLogFileIsCreated() {
    File expectedResult = new File(folder.getRoot(), LOG_FILE_FULL_NAME);
    assertTrue(expectedResult.exists());
  }

  @Test
  public void testLogFileIsRenamed() {
    String newName = "newName";
    log.rename("", "", "newName");
    File expectedResult = new File(folder.getRoot(), ".newName.log");
    File shouldntExist = new File(folder.getRoot(), LOG_FILE_FULL_NAME);
    assertTrue(expectedResult.exists());
    assertFalse(shouldntExist.exists());
  }

  @Test
  public void testGetLog() throws IOException {
    File file = new File(folder.getRoot(), LOG_FILE_FULL_NAME);
    BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
    writer.append("oldname / newname / time");
    writer.close();
    String[] expectedResults = new String[] {"oldname -> newname | time"};
    Assert.assertArrayEquals(expectedResults, log.getLog());
  }
}

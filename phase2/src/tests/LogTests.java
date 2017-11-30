package tests;
// Note: I'm using Junit4 because of the Temporary Folder Rule that it has (and was removed in
// Junit5) to save on headaches
import model.Log;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class LogTests {
  private final String LOG_FILE_NAME = "log";
  private final String LOG_FILE_FULL_NAME = "." + LOG_FILE_NAME + ".log";
  @Rule public TemporaryFolder folder = new TemporaryFolder();
  private Log log;

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
  public void testGetColumnWithAlphaEntries() throws IOException {
    File file = new File(folder.getRoot(), LOG_FILE_FULL_NAME);
    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    writer.append("oldname / newname / time");
    writer.close();
    String[] expectedResultsColumn0 = new String[] {"oldname"};
    String[] expectedResultsColumn1 = new String[] {"newname"};
    String[] expectedResultsColumn2 = new String[] {"time"};
    Assert.assertArrayEquals(expectedResultsColumn0, log.getColumn(0));
    Assert.assertArrayEquals(expectedResultsColumn1, log.getColumn(1));
    Assert.assertArrayEquals(expectedResultsColumn2, log.getColumn(2));
  }

  @Test
  public void testGetColumnWithSpecialEntries() throws IOException {
    File file = new File(folder.getRoot(), LOG_FILE_FULL_NAME);
    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    writer.append("!@#$%^&*() / ,.<>;:'\"[]{}\\|? / `~-_=+");
    writer.close();
    String[] expectedResultsColumn0 = new String[] {"!@#$%^&*()"};
    String[] expectedResultsColumn1 = new String[] {",.<>;:'\"[]{}\\|?"};
    String[] expectedResultsColumn2 = new String[] {"`~-_=+"};
    Assert.assertArrayEquals(expectedResultsColumn0, log.getColumn(0));
    Assert.assertArrayEquals(expectedResultsColumn1, log.getColumn(1));
    Assert.assertArrayEquals(expectedResultsColumn2, log.getColumn(2));
  }

  @Test
  public void testGetLog() throws IOException {
    File file = new File(folder.getRoot(), LOG_FILE_FULL_NAME);
    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    writer.append("oldname / newname / time");
    writer.close();
    String[] expectedResults = new String[] {"oldname / newname / time"};
    Assert.assertArrayEquals(expectedResults, log.getLog());
  }
}

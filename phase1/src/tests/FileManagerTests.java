package tests;

import file_explorer.FileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileManagerTests {
  private static final String TEST_FOLDER_LOCATION = "./testDirectory";
  private FileManager fileManager;

  @BeforeEach
  public void setup() {
    fileManager = new FileManager();
  }

  @Test
  public void testFileManagerGetFilesWithOneFile() {
    File root = new File(TEST_FOLDER_LOCATION, "1.jpg");
    File[] expectedReturn = {root};
    assertEquals(Arrays.toString(expectedReturn), Arrays.toString(fileManager.getAllFiles(root)));
  }

  @Test
  public void testFileManagerGetFilesWithEmptyDirectory() {
    File root = new File(TEST_FOLDER_LOCATION, "empty");
    File[] expectedReturn = {};
    assertEquals(Arrays.toString(expectedReturn), Arrays.toString(fileManager.getAllFiles(root)));
  }

  @Test
  public void testFileManagerGetFilesWithDirectoryWithOneFile() {
    File root = new File(TEST_FOLDER_LOCATION, "foo/bar");
    File[] expectedReturn = {new File(TEST_FOLDER_LOCATION, "foo/bar/5.jpg")};
    assertEquals(Arrays.toString(expectedReturn), Arrays.toString(fileManager.getAllFiles(root)));
  }

  @Test
  public void testFileManagerGetFilesWithDirectoryWithOneUnixHiddenFile() {
    File root = new File(TEST_FOLDER_LOCATION, "foo/baz");
    File[] expectedReturn = {new File(TEST_FOLDER_LOCATION, "foo/baz/.hidden.txt")};
    assertEquals(Arrays.toString(expectedReturn), Arrays.toString(fileManager.getAllFiles(root)));
  }

  @Test
  public void testFileManagerGetFilesWithDirectoryWithSubFoldersWithImagesAndAImage() {
    File root = new File(TEST_FOLDER_LOCATION, "foo");
    File[] expectedReturn = {
      new File(TEST_FOLDER_LOCATION, "foo/4.jpg"),
      new File(TEST_FOLDER_LOCATION, "foo/baz/.hidden.txt"),
      new File(TEST_FOLDER_LOCATION, "foo/bar/5.jpg"),
    };
    assertEquals(Arrays.toString(expectedReturn), Arrays.toString(fileManager.getAllFiles(root)));
  }
}

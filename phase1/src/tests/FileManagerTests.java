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
  public void testFileManagerAllGetFilesWithOneFile() {
    File root = new File(TEST_FOLDER_LOCATION, "1.jpg");
    File[] expectedReturn = {root};
    assertEquals(Arrays.toString(expectedReturn), Arrays.toString(fileManager.getAllFiles(root)));
  }

  @Test
  public void testFileManagerGetAllFilesWithEmptyDirectory() {
    File root = new File(TEST_FOLDER_LOCATION, "empty");
    File[] expectedReturn = {};
    assertEquals(Arrays.toString(expectedReturn), Arrays.toString(fileManager.getAllFiles(root)));
  }

  @Test
  public void testFileManagerGetAllFilesWithDirectoryWithOneFile() {
    File root = new File(TEST_FOLDER_LOCATION, "foo/bar");
    File[] expectedReturn = {new File(TEST_FOLDER_LOCATION, "foo/bar/5.jpg")};
    assertEquals(Arrays.toString(expectedReturn), Arrays.toString(fileManager.getAllFiles(root)));
  }

  @Test
  public void testFileManagerGetAllFilesWithDirectoryWithOneUnixHiddenFile() {
    File root = new File(TEST_FOLDER_LOCATION, "foo/baz");
    File[] expectedReturn = {new File(TEST_FOLDER_LOCATION, "foo/baz/.hidden.txt")};
    assertEquals(Arrays.toString(expectedReturn), Arrays.toString(fileManager.getAllFiles(root)));
  }

  @Test
  public void testFileManagerGetAllFilesWithDirectoryWithSubFoldersWithImagesAndAImage() {
    File root = new File(TEST_FOLDER_LOCATION, "foo");
    File[] expectedReturn = {
      new File(TEST_FOLDER_LOCATION, "foo/4.jpg"),
      new File(TEST_FOLDER_LOCATION, "foo/baz/.hidden.txt"),
      new File(TEST_FOLDER_LOCATION, "foo/bar/5.jpg"),
    };
    assertEquals(Arrays.toString(expectedReturn), Arrays.toString(fileManager.getAllFiles(root)));
  }

  @Test
  public void testFileManagerGetFilesWithOneFileAndRegExJpegSuffix() {
    File root = new File(TEST_FOLDER_LOCATION, "1.jpg");
    String regEx = ".*[.]jpg";
    File[] expectedReturn = {new File(TEST_FOLDER_LOCATION, "1.jpg")};
    assertEquals(
        Arrays.toString(expectedReturn), Arrays.toString(fileManager.getFiles(root, regEx)));
  }

    @Test
    public void testFileManagerGetFilesWithOneFileAndEmptyRegEx() {
        File root = new File(TEST_FOLDER_LOCATION, "1.jpg");
        String regEx = "";
        File[] expectedReturn = {};
        assertEquals(
                Arrays.toString(expectedReturn), Arrays.toString(fileManager.getFiles(root, regEx)));
    }

  @Test
  public void testFileManagerGetFilesWithDirectoryWithOneFileAndRegExJpegSuffix() {
    File root = new File(TEST_FOLDER_LOCATION, "foo/bar");
    String regEx = ".*[.]jpg";
    File[] expectedReturn = {new File(TEST_FOLDER_LOCATION, "foo/bar/5.jpg")};
    assertEquals(
        Arrays.toString(expectedReturn), Arrays.toString(fileManager.getFiles(root, regEx)));
  }

    @Test
    public void testFileManagerGetFilesWithDirectoryWithOneFileAndEmptyRegEx() {
        File root = new File(TEST_FOLDER_LOCATION, "foo/bar");
        String regEx = ".*[.]jpg";
        File[] expectedReturn = {new File(TEST_FOLDER_LOCATION, "foo/bar/5.jpg")};
        assertEquals(
                Arrays.toString(expectedReturn), Arrays.toString(fileManager.getFiles(root, regEx)));
    }

    @Test
    public void testFileManagerGetFilesWithDirectoryWithATextFileAndRegExJpegSuffix(){
      File root = new File(TEST_FOLDER_LOCATION, "bar");
      String regEx = ".*[.]jpg";
      File[] expectedReturn = {
              new File(TEST_FOLDER_LOCATION, "bar/2.jpg"),
              new File(TEST_FOLDER_LOCATION, "bar/.hidden3.jpg")
      };
      assertEquals(Arrays.toString(expectedReturn), Arrays.toString(fileManager.getFiles(root, regEx)));

    }
}

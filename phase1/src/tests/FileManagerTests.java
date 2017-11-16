package tests;

import model.file_explorer.FileManager;
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
    fileManager = new FileManager("foo");
  }

  @Test
  public void testFileManagerAllGetFilesWithOneFile() {
    File root = new File(TEST_FOLDER_LOCATION, "1.jpg");
    FileManager fileManager = new FileManager(root.getPath());
    File[] expectedReturn = {root};
    assertEquals(Arrays.toString(expectedReturn), Arrays.toString(fileManager.getAllFiles()));
  }

  @Test
  public void testFileManagerGetAllFilesWithEmptyDirectory() {
    File root = new File(TEST_FOLDER_LOCATION, "empty");
    FileManager fileManager = new FileManager(root.getPath());
    File[] expectedReturn = {};
    assertEquals(Arrays.toString(expectedReturn), Arrays.toString(fileManager.getAllFiles()));
  }

  @Test
  public void testFileManagerGetAllFilesWithDirectoryWithOneFile() {
    File root = new File(TEST_FOLDER_LOCATION, "foo/bar");
    FileManager fileManager = new FileManager(root.getPath());
    File[] expectedReturn = {new File(TEST_FOLDER_LOCATION, "foo/bar/5.jpg")};
    assertEquals(Arrays.toString(expectedReturn), Arrays.toString(fileManager.getAllFiles()));
  }

  @Test
  public void testFileManagerGetAllFilesWithDirectoryWithOneUnixHiddenFile() {
    File root = new File(TEST_FOLDER_LOCATION, "foo/baz");
    FileManager fileManager = new FileManager(root.getPath());
    File[] expectedReturn = {new File(TEST_FOLDER_LOCATION, "foo/baz/.hidden.txt")};
    assertEquals(Arrays.toString(expectedReturn), Arrays.toString(fileManager.getAllFiles()));
  }

  @Test
  public void testFileManagerGetAllFilesWithDirectoryWithSubFoldersWithImagesAndAImage() {
    File root = new File(TEST_FOLDER_LOCATION, "foo");
    FileManager fileManager = new FileManager(root.getPath());
    File[] expectedReturn = {
      new File(TEST_FOLDER_LOCATION, "foo/4.jpg"),
      new File(TEST_FOLDER_LOCATION, "foo/bar/5.jpg"),
      new File(TEST_FOLDER_LOCATION, "foo/baz/.hidden.txt"),

    };
    assertEquals(Arrays.toString(expectedReturn), Arrays.toString(fileManager.getAllFiles()));
  }

  @Test
  public void testFileManagerGetFilesWithOneFileAndRegExJpegSuffix() {
    File root = new File(TEST_FOLDER_LOCATION, "1.jpg");
    FileManager fileManager = new FileManager(root.getPath());
    String regEx = ".*[.]jpg";
    File[] expectedReturn = {new File(TEST_FOLDER_LOCATION, "1.jpg")};
    assertEquals(
        Arrays.toString(expectedReturn), Arrays.toString(fileManager.getAllFiles(root.getPath(), regEx)));
  }

    @Test
    public void testFileManagerGetFilesWithOneFileAndEmptyRegEx() {
      File root = new File(TEST_FOLDER_LOCATION, "1.jpg");
      FileManager fileManager = new FileManager(root.getPath());
      String regEx = "";
      File[] expectedReturn = {};
      assertEquals(
               Arrays.toString(expectedReturn), Arrays.toString(fileManager.getAllFiles(root.getPath(), regEx)));
    }

  @Test
  public void testFileManagerGetFilesWithDirectoryWithOneFileAndRegExJpegSuffix() {
    File root = new File(TEST_FOLDER_LOCATION, "foo/bar");
    FileManager fileManager = new FileManager(root.getPath());
    String regEx = ".*[.]jpg";
    File[] expectedReturn = {new File(TEST_FOLDER_LOCATION, "foo/bar/5.jpg")};
    assertEquals(
        Arrays.toString(expectedReturn), Arrays.toString(fileManager.getAllFiles(root.getPath(), regEx)));
  }

    @Test
    public void testFileManagerGetFilesWithDirectoryWithOneFileAndEmptyRegEx() {
        File root = new File(TEST_FOLDER_LOCATION, "foo/bar");
      FileManager fileManager = new FileManager(root.getPath());
        String regEx = ".*[.]jpg";
        File[] expectedReturn = {new File(TEST_FOLDER_LOCATION, "foo/bar/5.jpg")};
        assertEquals(
                Arrays.toString(expectedReturn), Arrays.toString(fileManager.getAllFiles(root.getPath(), regEx)));
    }

    @Test
    public void testFileManagerGetFilesWithDirectoryWithATextFileAndRegExJpegSuffix(){
      File root = new File(TEST_FOLDER_LOCATION, "bar");
      FileManager fileManager = new FileManager(root.getPath());
      String regEx = ".*[.]jpg";
      File[] expectedReturn = {
              new File(TEST_FOLDER_LOCATION, "bar/.hidden3.jpg"),
              new File(TEST_FOLDER_LOCATION, "bar/2.jpg"),
      };
      assertEquals(Arrays.toString(expectedReturn), Arrays.toString(fileManager.getAllFiles(root.getPath(), regEx)));

    }
}

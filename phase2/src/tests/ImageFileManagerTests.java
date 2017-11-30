package tests;
// Note: I'm using Junit4 because of the Temporary Folder Rule that it has (and was removed in
// Junit5) to save on headaches
import model.ImageFile;
import model.ImageFileManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ImageFileManagerTests {
  @Rule public TemporaryFolder folder = new TemporaryFolder();

  private ImageFileManager imageFileManager;
  private File imageFile;
  private File nonTaggableFile;
  private File subFolder;
  private File subFolderedImageFile;
  private File subFolderedNonTaggableFile;

  @Before
  public void Before() throws IOException {
    imageFile = folder.newFile("ImageFile @Tag1.jpg");
    nonTaggableFile = folder.newFile("NonTaggableFile.txt");
    subFolder = folder.newFolder("SubFolder");
    subFolderedImageFile = new File(subFolder, "SubFolderedImageFile @Tag2.png");
    subFolderedImageFile.createNewFile();
    subFolderedNonTaggableFile = new File(subFolder, "SubFolderedNonTaggableFile.doc");
    subFolderedNonTaggableFile.createNewFile();
    imageFileManager = new ImageFileManager(folder.getRoot());
  }

  @Test
  public void testGetLocalImageFiles() {
    ImageFile[] expectedResults = new ImageFile[] {new ImageFile(imageFile)};
    Assert.assertArrayEquals(expectedResults, imageFileManager.getLocalImageFiles(""));
  }

  @Test
  public void testGetAllImageFiles() {
    ImageFile[] expectedResults =
        new ImageFile[] {new ImageFile(imageFile), new ImageFile(subFolderedImageFile)};
    Assert.assertArrayEquals(expectedResults, imageFileManager.getAllImageFiles(""));
  }

  //  @Test
  //  public void testGetAllTags() {
  //    String[] expectedResults = new String[] {"Tag1", "Tag2"};
  //    Assert.assertArrayEquals(expectedResults, imageFileManager.getAllCurrentTags());
  //  }

  @Test
  public void testGetRoot() {
    File expectedResults = folder.getRoot();
    assertEquals(expectedResults, imageFileManager.getRoot());
  }

  @Test
  public void testChangeDirectory() {
    imageFileManager.changeDirectory(subFolder);
    File expectedResults = subFolder;
    assertEquals(expectedResults, imageFileManager.getRoot());
    imageFileManager.changeDirectory(folder.getRoot().getPath());
    expectedResults = folder.getRoot();
    assertEquals(expectedResults, imageFileManager.getRoot());
  }
}

package image_tag_model;

import file_explorer.FileManager;
import image_tag_explorer.Image;
import image_tag_explorer.ImageManager;
import image_tag_explorer.TagManager;

import java.io.File;

public class ModelManager {
  private static final String[] FILE_MATCH_STRINGS = {
    ".*[.]jpg", ".*[.]png", ".*[.]gif", ".*[.]bmp"
  };
  private ImageManager imageManager;
  private TagManager tagManager;
  private FileManager fileManager;

  public ModelManager() {
    imageManager = new ImageManager();
    tagManager = new TagManager();
    fileManager = new FileManager();
  }

  public ModelManager(File root) {
    this();
    // TODO: add a way to load a serializable file if it exists.
    fileManager.getFiles(root, "*.jpg");
    for (String regEx : FILE_MATCH_STRINGS) {
      for (File file : fileManager.getFiles(root, regEx)) {
        try {
          imageManager.addImage(new Image(file));
        } catch (Exception e) {
          // TODO: we should catch when Image has tags that are not in tagManager
        }
      }
    }
  }
}

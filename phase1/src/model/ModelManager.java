package model;

import model.file_explorer.FileManager;
import model.image_tag_explorer.Image;
import model.image_tag_explorer.ImageManager;
import model.image_tag_explorer.TagManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelManager {
  /** String to match all tagable files */
  private static final String FILE_MATCH_STRING = ".*[.](jpg|png|gif|bmp)";

  private String path;
  private ImageManager imageManager;
  private TagManager tagManager;
  private FileManager fileManager;

  public ModelManager() {
    imageManager = new ImageManager();
    tagManager = new TagManager();
    fileManager = new FileManager();
  }

  public ModelManager(String path) {
    this();
    // TODO: add a way to load a serializable file if it exists.
    fileManager.getFiles(path, "*.jpg");

    for (File file : fileManager.getFiles(path, FILE_MATCH_STRING)) {
      try {
        imageManager.addImage(new Image(file));
      } catch (Exception e) {
        // TODO: we should catch when Image has tags that are not in tagManager
      }
    }
  }
}

package model;

import model.file_explorer.FileManager;
import model.image_tag_explorer.Image;
import model.image_tag_explorer.ImageManager;
import model.image_tag_explorer.TagManager;

import java.io.File;

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
    fileManager = new FileManager("");
  }

  public ModelManager(String path) {
    this();
    // TODO: add a way to load a serializable file if it exists.
    for (File file : fileManager.getAllFiles(FILE_MATCH_STRING)) {
    }
  }
}

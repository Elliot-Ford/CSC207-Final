package model;

import javafx.scene.image.Image;

import java.io.File;

public class GeneralFile extends AbsTaggableFile {

  /**
   * Constructs a new GeneralFile object from a string
   * @param path
   */
  public GeneralFile(String path) {
    this(new File(path));
  }

  /**
   * Constructs a new GeneralFile object from the given file
   * @param file
   */
  public GeneralFile(File file) {
    super(file);
  }

  /**
   * returns the associated image.
   * @return Image
   */
  @Override
  public Image getImage() {
    return null;
  }
}

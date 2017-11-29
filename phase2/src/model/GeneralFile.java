package model;

import javafx.scene.image.Image;

import java.io.File;

public class GeneralFile extends AbsTaggableFile {

  public GeneralFile(String path) {
    this(new File(path));
  }

  public GeneralFile(File file) {
    super(file);
  }

  @Override
  public Image getImage() {
    return null;
  }
}

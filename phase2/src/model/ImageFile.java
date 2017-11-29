package model;

import javafx.scene.image.Image;

import java.io.*;
//import java.nio.file.FileSystems;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Represents a physical image file in a filesystem. */
@SuppressWarnings("WeakerAccess")
public class ImageFile extends AbsTaggableFile implements Observer {

  /**
   * Construct a new ImageFile object with a given path.
   *
   * @param path the directory this ImageFile object is under
   */
  public ImageFile(String path) {
    this(new File(path));
  }

  /**
   * Construct a new ImageFile object representation of a physical file.
   *
   * @param file the physical file for this ImageFile
   */
  public ImageFile(File file) {
    super(file);
  }

  /**
   * Returns the physical Image File.
   *
   * @return the physical Image File.
   */
  public Image getImage() {
    return new Image("file:" + super.getFile().getAbsolutePath());
  }

}

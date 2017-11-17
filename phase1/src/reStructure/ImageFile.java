package reStructure;

import java.io.File;

/** Represents a physical image file in a filesystem. */
public class ImageFile {

  private static final String LOG_FILE_SUFFIX = ".log";

  /** the image file in the system */
  private File file;

  private File log;

  public ImageFile(String path) {
    file = new File(path);
    log =
        new File(
            path, file.getName().substring(0, file.getName().lastIndexOf('.')) + LOG_FILE_SUFFIX);
  }
}

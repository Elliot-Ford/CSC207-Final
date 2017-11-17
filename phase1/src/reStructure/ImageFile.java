package reStructure;

import java.io.File;

/** Represents a physical image file in a filesystem. */
public class ImageFile {

  private static final String LOG_FILE_SUFFIX = ".log";

  /** the image file in the system */
  private File file;

  private File log;

    /**
     * Construct a new ImageFile object with a given path.
     *
     * @param path the directory this ImageFile object is under
     */
  public ImageFile(String path) {
    file = new File(path);
    log =
        new File(
            path, file.getName().substring(0, file.getName().lastIndexOf('.')) + LOG_FILE_SUFFIX);
  }

    /**
     * Construct a new ImageFile object representation of a physical file.
     *
     * @param file the physical file for this ImageFile
     */
  public ImageFile(File file) {
        this.file = file;
        log = new File( file.getAbsolutePath(),
                file.getName().substring(0, file.getName().lastIndexOf('.')) + LOG_FILE_SUFFIX);
    }

}

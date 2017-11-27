package model;

import javafx.scene.image.Image;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/** Represents a physical image file in a filesystem. */
@SuppressWarnings("WeakerAccess")
public class ImageFile extends Observable implements Observer {

  private static final String LOG_FILE_SUFFIX = ".log";

  private static final String TAG_MARKER = "@";

  private static final String LOG_FILE_SEPARATOR = " / ";

  private static final String LOG_FILE_PREFIX = ".";

  /** the image file in the system */
  private File file;

  /** the previous names for the file */
  private Log log;

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
    this.file = file;
    log = new Log(file);
  }

  /**
   * Move the ImageFile to a given directory.
   *
   * @param newPath the string representation of the given directory
   * @return Whether this moving of the ImageFile was successful
   */
  public boolean moveFile(String newPath) {
    File newFile = new File(newPath, getName() + getSuffix());
    boolean ret1 = file.renameTo(newFile);
    boolean ret2 = log.moveFile(newPath, file, ret1);
    boolean ret = ret1 && ret2;
    if (ret) {
      if (newFile.exists()) {
        file = newFile;
      }
    }
    return ret;
  }

  /**
   * gets all the tags associated to the image file.
   *
   * @return a String[] of associated tags.
   */
  public String[] getTags() {
    return extractTags(getName());
  }

  /** Extracts the Tags in a given string */
  private String[] extractTags(String stringWithTags) {
    List<String> tags = new ArrayList<>();
    String[] slicedString = stringWithTags.split(" ");
    for (String word : slicedString) {
      // Check each word to see if its format indicates it's a tag.
      if (word.length() >= TAG_MARKER.length()
          && word.substring(0, TAG_MARKER.length()).equals(TAG_MARKER)) {
        tags.add(word.substring(1));
      }
    }
    return tags.toArray(new String[tags.size()]);
  }

  /**
   * gets all the tags that were previously associated to the image file.
   *
   * @return a String[] of previously associated tags.
   */
  @SuppressWarnings("ResultOfMethodCallIgnored")
  public String[] getPreviousTags() {
    Set<String> tags = new HashSet<>();
    String[] currentTags = getTags();
    String[] logs = log.getLog();
    for (String log : logs) {
      String[] potentialTags = extractTags(log.split(LOG_FILE_SEPARATOR)[0]);
      for (String potentialTag : potentialTags) {
        if (!Arrays.asList(currentTags).contains(potentialTag)) {
          tags.add(potentialTag);
        }
        //          if(currentTags.length > 0) {
        //              for (String currentTag : currentTags) {
        //                  if (currentTag.equals(potentialTag)) {
        //                      tags.add(potentialTag);
        //                  }
        //              }
        //          } else {
        //              tags.add(potentialTag);
        //          }
      }
    }
    return tags.toArray(new String[tags.size()]);
  }

  /**
   * Tries to add a given tag to the image.
   *
   * @param newTag the tag to try and add.
   * @return true if successful, false if it isn't.
   */
  public boolean addTag(String newTag) {
    boolean success = false;
    if (newTag != null && newTag.length() > 0 && !Arrays.asList(getTags()).contains(newTag)) {
      success = rename(String.format("%s %s%s", getName(), TAG_MARKER, newTag));
      setChanged();
      notifyObservers();
    }
    return success;
  }

  /**
   * Tries to remove the given tag from the image.
   *
   * @param thisTag the tag to try and remove.
   * @return true if removal is successful, false if it isn't.
   */
  public boolean removeTag(String thisTag) {
    boolean ret = false;
    if (thisTag != null && file.getName().contains(thisTag) && thisTag.length() > 0) {
      ret = rename(getName().replace(String.format(" %s%s", TAG_MARKER, thisTag), ""));
    }
    return ret;
  }

  /**
   * Tries to rename the image with the newName
   *
   * @param newName a String to try and rename the image with.
   * @return true if renaming is successful, false if it isn't.
   */
  public boolean rename(String newName) {
    String lastName = getName();
    File newFile = new File(file.getParent(), newName + getSuffix());
    boolean ret = false;
    if (!newFile.exists()) {
      ret = file.renameTo(newFile);
    }
    if (ret) {
      if (newFile.exists()) {
        file = newFile;
      }
      if (newFile.exists()) {
        Log newLog = new Log(newFile);
        ret = log.rename(lastName, newName, newLog.getFile());
      }
    }
    return ret;
  }

  /**
   * Returns the name of an image with no suffix.
   *
   * @return the name of an image.
   */
  public String getName() {
    String ret = "";
    if (file.getName().lastIndexOf(".") != -1) {
      ret = file.getName().substring(0, file.getName().lastIndexOf("."));
    }
    return ret;
  }

  /**
   * Returns the suffix of an image.
   *
   * @return the suffix of an image
   */
  public String getSuffix() {
    String ret = "";
    if (file.getName().lastIndexOf(".") != -1) {
      ret = file.getName().substring(file.getName().lastIndexOf("."));
    }
    return ret;
  }

  @Override
  public String toString() {
    return file.getName();
  }

  /**
   * Returns the physical Image File.
   *
   * @return the physical Image File.
   */
  public Image getImage() {
    return new Image("file:" + file.getAbsolutePath());
  }

  /**
   * Return the name of the image file without any String representations of the Tags.
   *
   * @return the actual name of the image file
   */
  public String getActualName() {
    String ret = "";
    if (getName().contains(TAG_MARKER)) {
      ret = getName().substring(0, getName().indexOf(TAG_MARKER));
    }
    return ret;
  }

  public File getFile() {
    return file;
  }

  /**
   * Return the full name of the image file, suffix and all.
   *
   * @return the full name of the image file
   */
  public String getFullName() {
    return file.getName();
  }

  @Override
  public boolean equals(Object o) {
    return o != null && o.getClass() == this.getClass() && ((ImageFile) o).getFile().equals(this.getFile());
  }

  /**
   * Update the ImageFile when a tag is deleted from the TagManager.
   *
   * @param o the observable object that updates the observer ImageFile when a Tag is deleted
   * @param arg pass in argument
   */
  @Override
  public void update(Observable o, Object arg) {
    for (String tag : getTags()) {
      if (!Arrays.asList(((TagManager) o).getTags()).contains(tag)) {
        removeTag(tag);
      }
    }
  }

  /**
   *
   *
   * @return
   */
  public String[] getLog() {
    return log.getLog();
  }
}

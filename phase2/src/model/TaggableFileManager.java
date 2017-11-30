package model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Manages all the imageFiles under a root folder */
public class TaggableFileManager {
  /** String to match all image files */
  private static final String IMAGE_FILE = "^.*[.](jpg|jpeg|png|gif|bmp|JPG|JPEG|PNG|GIF|BMP)$";

  /** the root of the directory */
  private File root;

  /** the tagManager */
  private TagManager tagManager;

  /** a set of AbsTaggableFiles */
  private Set<AbsTaggableFile> absTaggableFiles;

  /**
   * Construct a new TaggableFileManager object.
   *
   * @param path the root directory for this TaggableFileManager object
   */
  public TaggableFileManager(String path) {
    this(new File(path));
  }

  public TaggableFileManager(File file) {
    root = new File("");
    tagManager = new TagManager();
    absTaggableFiles = new HashSet<>();
    changeDirectory(file);
  }

  /**
   * Returns all the image files anywhere under the root directory.
   *
   * @param regex the regular expression to an acceptable file with.
   * @return a ImageFile[] of all image files anywhere under the root directory.
   */
  public AbsTaggableFile[] getAllImageFiles(String regex) {
    List<File> matchingFiles = new ArrayList<>();
    if (root.isDirectory() || (root.isFile() && root.getName().matches(IMAGE_FILE))) {
      matchingFiles.add(root);

      int i = 0;
      while (i < matchingFiles.size()) {
        // Check if element at i in ret is a Directory, a File, or it exists.
        if (matchingFiles.get(i).isDirectory()) {
          // Element at i is a directory, so determine if it has children and remove the Element at
          // i.
          // Check if the Element at i has children.
          if (matchingFiles.get(i).list() != null) {
            // Element at i has children, so add children to the ArrayList if they match the regEx.
            for (File file : matchingFiles.get(i).listFiles()) {
              if (file.getName().matches(IMAGE_FILE) || file.isDirectory()) {
                matchingFiles.add(file);
              }
            }
          }
          matchingFiles.remove(i);
        } else if (matchingFiles.get(i).isFile()) {
          // Element at i is a File, so increment i by 1.
          i += 1;
        } else {
          // Element doesn't exist in filesystem, so remove from ret.
          matchingFiles.remove(i);
        }
      }
    }
    return generateAbsTaggableFiles(matchingFiles);
  }

  /**
   * Returns all the image files directly under the root directory.
   *
   * @param regex the regular expression to an acceptable file with.
   * @return a ImageFile[] of all images files directly under the root directory.
   */
  public AbsTaggableFile[] getLocalImageFiles(String regex) {
    List<File> matchingFiles = new ArrayList<>();
    if (root.list() != null) {
      for (File file : root.listFiles()) {
        if (file.isFile() && file.getName().matches(IMAGE_FILE)) {
          matchingFiles.add(file);
        }
      }
    }
    return generateAbsTaggableFiles(matchingFiles);
  }

  private AbsTaggableFile[] generateAbsTaggableFiles(List<File> files) {
    List<AbsTaggableFile> possibleAbsTaggableFiles = new ArrayList<>(files.size());
    for (File file : files) {
      if (file.getName().matches(IMAGE_FILE)) {
        possibleAbsTaggableFiles.add(new ImageFile(file));
      } else {
        possibleAbsTaggableFiles.add(new GeneralFile(file));
      }
    }
    Set<AbsTaggableFile> newAbsTaggableFiles =
        new HashSet<>(possibleAbsTaggableFiles.size() + absTaggableFiles.size());
    newAbsTaggableFiles.addAll(absTaggableFiles);
    newAbsTaggableFiles.addAll(possibleAbsTaggableFiles);
    newAbsTaggableFiles.retainAll(possibleAbsTaggableFiles);
    tagManager.deleteObservers();
    for (AbsTaggableFile absTaggableFile : newAbsTaggableFiles) {
      try {
        tagManager.addTag(absTaggableFile.getTags());
      } catch (Exception e) {
        e.printStackTrace();
      }
      absTaggableFile.addObserver(tagManager);
      tagManager.addObserver(absTaggableFile);
    }
    absTaggableFiles = newAbsTaggableFiles;
    return absTaggableFiles.toArray(new AbsTaggableFile[absTaggableFiles.size()]);
  }

  /**
   * returns all the current existing tags, associated or unassociated to images
   *
   * @return a String[] of all the tags.
   */
  public String[] getAllCurrentTags() {
    return tagManager.getTags();
  }

  /**
   * Deletes Tag from all files and tagManager
   *
   * @param tags the Array of String representations of tags to delete
   * @return true if it succeeds, false if it doesn't.
   */
  public boolean deleteTag(String[] tags) {
    try {
      return tagManager.removeTag(tags);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean addTag(String[] tags) {
    try {
      return tagManager.addTag(tags);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Changes the working directory of TaggableFileManager if new directory exists. All tags that aren't
   * associated with an Image will when be unavailable when switch happens unless restored.
   *
   * @param path the String path of the root folder to try to switch to
   * @return true if changingDirectory succeeds.
   */
  public boolean changeDirectory(String path) {
    return changeDirectory(new File(path));
  }

  /**
   * Changes the working directory of TaggableFileManager if new directory exists. All tags that aren't
   * associated with an Image will when be unavailable when switch happens unless restored.
   *
   * @param root the root folder to try to switch to
   * @return true if changingDirectory succeeds.
   */
  public boolean changeDirectory(File root) {
    boolean ret = false;
    if (root.exists()) {
      ret = true;
      this.root = root;
    }
    return ret;
  }

  public File getRoot() {
    return root;
  }
}

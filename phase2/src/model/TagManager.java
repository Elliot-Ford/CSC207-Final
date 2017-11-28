package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/** manages a collection of tags. */
public class TagManager extends Observable implements Observer, Taggable {
  /** A set of tags. */
  private Set<String> tags;

  private Log log;

  private static final String LOG_FILE_SEPARATOR = " / ";

  /** Construct a new TagManager with no existing tag. */
  public TagManager() {
    tags = new HashSet<>();
    try {
      FileReader fileReader = new FileReader(".TagManager.log");
      BufferedReader reader = new BufferedReader(fileReader);
      String line = reader.readLine();
      while (line != null) {
        String curr = line;
        line = reader.readLine();
        if (line == null) {
          String[] lineList = curr.split(LOG_FILE_SEPARATOR);
          String set = lineList[1];
          String[] tagSet = set.split(",");
          for (String s : tagSet) {
            s = s.replaceFirst("\\[", "");
            s = s.replaceFirst("]", "");
            s = s.trim();
            tags.add(s);
          }
        }
      }
      reader.close();
      fileReader.close();
    } catch (IOException e1) {
      log = new Log();
    }
  }

  /**
   * Construct a new TagManager with a list of existing tags.
   *
   * @param tags a list of string representations for existing tags.
   */
  public TagManager(String[] tags) {
    this.tags = new HashSet<>(Arrays.asList(tags));
  }

  /**
   * Add a new tag to the TagManager.
   *
   * @param newTag the string representation for this new tag
   * @return a boolean indicating whether the adding of this tag succeeded
   */
  public boolean addTag(String newTag) {
      Set<String> oldSet = new HashSet<>(tags);
    boolean success =  tags.add(newTag);
    if(success) {
      setChanged();
      notifyObservers();
      log.rename(oldSet.toString(), tags.toString(), log.getFile().getName());
    }
    return success;
  }

  /**
   * Remove a tag from the TagManager.
   *
   * @param thisTag the string representation for this new tag
   * @return a boolean indicating whether the removal of this tag succeeded
   */
  public boolean removeTag(String thisTag) {
    Set<String> oldSet = new HashSet<>(tags);
    boolean success =  tags.remove(thisTag);
    if(success) {
      setChanged();
      notifyObservers();
      log.rename(oldSet.toString(), tags.toString());
    }
    return success;
  }

  /**
   * Return existing tags.
   *
   * @return a String[] of the existing tags
   */
  public String[] getTags() {
    return tags.toArray(new String[tags.size()]);
  }

  /**
   * Update the tagManager with new tags.
   *
   * @param o the observable object that updates the observer TagManager when new Tag is added
   * @param arg pass in argument
   */
  @Override
  public void update(Observable o, Object arg) {
    for (String tag : ((ImageFile) o).getTags()) {
      addTag(tag);
    }
  }

  public Set<String> getPreviousGlobalTags() {
    return log.getPreviousGlobalTags();
  }
}

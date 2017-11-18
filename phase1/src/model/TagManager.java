package model;

import java.util.*;

public class TagManager {
  /** A list of string representations for tags. */
  public Set<String> tags;

  /** Construct a new TagManager with no existing tag. */
  public TagManager() {
    tags = new HashSet<>();
  }

  /**
   * Construct a new TagManager with a list of existing tags.
   *
   * @param tags a list of string representations for existing tags
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
    return tags.add(newTag);
  }

  /**
   * Remove a tag from the TagManager.
   *
   * @param thisTag the string representation for this new tag
   * @return a boolean indicating whether the removal of this tag succeeded
   */
  public boolean removeTag(String thisTag) {
    return tags.remove(thisTag);
  }

  /**
   * Get a list of the string representation of the existing tags.
   *
   * @return a list of the string representation of the existing tags
   */
  public String[] getTags() {
    return tags.toArray(new String[tags.size()]);
  }

  public boolean update(String[] newTags) {
    return tags.addAll(Arrays.asList(newTags));
  }
}

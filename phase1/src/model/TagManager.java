package model;

import java.util.*;

/** manages a collection of tags. */
public class TagManager {
  /** A set of tags. */
  private Set<String> tags;

  /** Construct a new TagManager with no existing tag. */
  public TagManager() {
    tags = new HashSet<>();
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
   * Return existing tags.
   *
   * @return a String[] of the existing tags
   */
  public String[] getTags() {
    return tags.toArray(new String[tags.size()]);
  }

  /**
   * Update the tagManager with new tags.
   * @param newTags tags to add to the tagManager.
   * @return if updating the tagManager was a success.
   */
  public boolean update(String[] newTags) {
    return tags.addAll(Arrays.asList(newTags));
  }
}
package model;

/** Represents all classes that can have tags added and removed from it */
public interface Taggable {

  /**
   * Add tags to the file
   * @param tags
   * @return boolean
   * @throws Exception
   */
  boolean addTag(String[] tags) throws Exception;

  /**
   * removes tag from a given file
   * @param tags
   * @return
   * @throws Exception
   */
  boolean removeTag(String[] tags) throws Exception;

  String[] getTags();
}

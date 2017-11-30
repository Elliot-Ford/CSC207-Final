package model;

/** Represents all classes that can have tags added and removed from it */
public interface Taggable {
  boolean addTag(String[] tags) throws Exception;

  boolean removeTag(String[] tags) throws Exception;

  String[] getTags();
}

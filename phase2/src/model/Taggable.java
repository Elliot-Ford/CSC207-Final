package model;

/** Represents all classes that can have tags added and removed from it */
public interface Taggable {
    boolean addTag(String tag);
    boolean removeTag(String tag);

}

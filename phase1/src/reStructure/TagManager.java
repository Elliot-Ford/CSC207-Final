package reStructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagManager {
    /** A list of string representations for tags. */
    public List<String> tags;

    /**
     * Construct a new TagManager with no existing tag.
     */
    public TagManager() {}

    /**
     * Construct a new TagManager with a list of existing tags.
     *
     * @param tags a list of string representations for existing tags
     */
    public TagManager(String[] tags) {
        this.tags = new ArrayList<>(Arrays.asList(tags));
    }

    /**
     * Add a new tag to the TagManager.
     *
     * @param newTag the string representation for this new tag
     * @return a boolean indicating whether the adding of this tag succeeded
     */
    public boolean addTag(String newTag) {
        boolean here = false;
        // Checking if this tag already exists
        for (String tag : tags) {
            if (tag.equals(newTag)) {
                here = true;
            }
        }

        // Add the tag to the list if it does not exist
        if (!here) {
            tags.add(newTag);
        }

        return !here;
    }

    /**
     * Remove a tag from the TagManager.
     *
     * @param thisTag the string representation for this new tag
     * @return a boolean indicating whether the removal of this tag succeeded
     */
    public boolean removeTag(String thisTag) {
        boolean here = false;
        // Checking if this tag already exists
        for (String tag : tags) {
            if (tag.equals(thisTag)) {
                here = true;
            }
        }

        // Remove the tag to the list if it does not exist
        if (here) {
            tags.remove(thisTag);
        }

        return here;
    }

    /**
     * Get a list of the string representation of the existing tags.
     *
     * @return a list of the string representation of the existing tags
     */
    public String[] getTags() {
        return tags.toArray(new String[tags.size()]);
    }
}

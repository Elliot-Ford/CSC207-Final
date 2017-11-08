package image_tag_explorer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Image {

    private File imageFile;
    private List<Tag> tags;
    private Stack<String> previousNames;

    /**
     * Constructs an abstract image with a referring file with no tags.
     * @param imageFile the abstract file representation of the image file
     */
    public Image(File imageFile) {
        this(imageFile, new Tag[0]);
    }

    public Image(File imageFile, Tag[] tags) {
        this.imageFile = imageFile;
        this.tags = new ArrayList<>(Arrays.asList(tags));
        previousNames = new Stack<>();

        // Adding Tags into the Image name.
        for (Tag tag : tags) {
            previousNames.add("@" + tag.getName());
        }
        //TODO: if there are "@" tags on the image name that aren't in the given Tag[] array throw an custom error.
        // why do we need to check this if we are constructing a new Image object?
    }

    /**
     * adds a given tag to the Image, modifies the image filename
     * to match the new change
     *
     * @param newTag the tag to add to the image
     */
    public void addTag(Tag newTag) {
        boolean notHere = true;
        // Checking if this Tag is new to this Image
        for (String tagName : previousNames) {
            if (tagName.equals(newTag.getName())) {
                notHere = false;
            }
        }

        if (notHere) {
            previousNames.add("@" + newTag.getName());
            tags.add(newTag);
            newTag.addImage(this);
        }
    }

    /**
     * tries to remove a given tag from the Image, modifies the image filename
     * to match the new change
     *
     * @param tag the tag to try and remove from the image
     * @return true if tag removal was successful, false if not.
     */
    public boolean removeTag(Tag tag) {
        boolean notHere = true;
        // Checking if this Tag is in this Image
        for (String tagName : previousNames) {
            if (tagName.equals(tag.getName())) {
                notHere = false;
            }
        }

        // If this Tag is in this Image, then remove it.
        if (!notHere) {
            tags.remove(tag);
            previousNames.remove("@" + tag.getName());
            tag.removeImage(this);
        }

        // If !notHere is true, then the removal was successful.
        return !notHere;
    }

    public void rename(String newName) {
        previousNames.push(imageFile.getName());
        imageFile.renameTo(new File(imageFile.getParentFile(), newName));
        //TODO: write a tester that makes sure this method does what's expected.
    }

    /**
     * returns the name of an Image.
     *
     * @return the name of the Image.
     */
    public String getName() {
        return imageFile.getName();
    }
}

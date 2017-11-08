package image_tag_explorer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tag {

    private List<Image> images;
    private String name;

    public Tag(String name) {
        this(name, new Image[0]);
    }

    public Tag(String name, Image[] images) {
        this.name = name;
        this.images = new ArrayList<>(Arrays.asList(images));
    }

    public void addImage(Image newImage) {
        boolean notHere = true;
        // Checking if this Image is new to this Tag
        for (Image image : images) {
            if (image.getName().equals(newImage.getName())) {
                notHere = false;
            }
        }

        if (notHere) {
            images.add(newImage);
            newImage.addTag(this);
        }
    }

    public boolean removeImage(Image image) {
        boolean notHere = true;
        // Checking if this Image is in this Tag
        for (Image existImage : images) {
            if (existImage.getName().equals(image.getName())) {
                notHere = false;
            }
        }

        // If this Image is in this Tag, then remove it.
        if (!notHere) {
            images.remove(image);
            image.removeTag(this);
            //TODO: this return value of image.removeTag(this) is not used in this call
        }

        // If !notHere is true, then the removal was successful.
        return !notHere;
    }

    public String getName() {
        return this.name;
    }
}

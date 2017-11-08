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
        //TODO: Write a working version of this.
    }

    public boolean removeImage(Image image) {
        //TODO: Write a working version of this
        return false;
    }

    public String getName() {
        return this.name;
    }
}

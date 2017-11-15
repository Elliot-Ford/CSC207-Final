package image_tag_explorer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagManager {
    public List<Tag> tags;

    public TagManager() {}

    public TagManager(Tag[] tag) {
        this.tags = new ArrayList<>(Arrays.asList(tag));
    }

}

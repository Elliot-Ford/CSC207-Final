package reStructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagManager {
    public List<String> tags;

    public TagManager() {}

    public TagManager(String[] tags) {
        this.tags = new ArrayList<>(Arrays.asList(tags));
    }
}

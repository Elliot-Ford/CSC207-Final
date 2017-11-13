package image_tag_explorer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagManager {
	public List<Tag> tags;

	public TagManager() {
		this(new Tag[0]);
	}

	public TagManager(Tag[] tags) {
		this.tags = new ArrayList<>(Arrays.asList(tags));

	}

	public void addTag(Tag newTag) {
		tags.add(newTag);
	}

	public void removeTag(Tag tag) {
		tags.remove(tag);
	}

	public Tag[] getTags() {
		return (Tag[]) tags.toArray();
	}
}

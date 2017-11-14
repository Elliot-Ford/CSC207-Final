package model.image_tag_explorer;

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

	public boolean addTag(Tag newTag) {
		return tags.add(newTag);
	}

	public boolean removeTag(Tag tag) {
		return tags.remove(tag);
	}

	public Tag[] getTags() {
		return (Tag[]) tags.toArray();
	}
}

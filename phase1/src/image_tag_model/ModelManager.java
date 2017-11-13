package image_tag_model;


import file_explorer.FileManager;
import image_tag_explorer.ImageManager;
import image_tag_explorer.TagManager;

import java.io.File;

public class ModelManager {
	ImageManager imageManager;
	TagManager tagManager;
	FileManager fileManager;

	public ModelManager() {
		imageManager = new ImageManager();
		tagManager = new TagManager();
		fileManager = new FileManager();
	}

	public ModelManager(File root) {
		this();
		fileManager.getFiles(root,"*.jpg");
	}

}

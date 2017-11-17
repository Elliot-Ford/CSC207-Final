package model;

import model.file_explorer.FileManager;
import model.image_tag_explorer.Image;
import model.image_tag_explorer.ImageManager;
import model.image_tag_explorer.TagManager;

import java.io.File;

public class ModelManager {
    /**
     * String to match all tagable files
     */
    private static final String FILE_MATCH_STRING = ".*[.](jpg|png|gif|bmp)";

    private String path;
    private ImageManager imageManager;
    private TagManager tagManager;
    private FileManager fileManager;

    public ModelManager() {
        imageManager = new ImageManager();
        tagManager = new TagManager();
        fileManager = new FileManager("");
    }

    public ModelManager(String path) throws ClassNotFoundException {
        imageManager = new ImageManager();
        tagManager = new TagManager();
        fileManager = new FileManager(path);

//        if (fileManager.ImagesFileExists()) {
//            Image[] ret = fileManager.readImagesFromFile();
//            imageManager = new ImageManager(ret);
//            if (fileManager.TagsFileExists()) {
//                Tag[] t = fileManager.readTagsFromFile();
//                tagManager = new TagManager(t);
//            }
//        } else {
            for (File file : fileManager.getLocalFiles(FILE_MATCH_STRING)) {
                try {
                    imageManager.addImage(new Image(file));
                } catch (Exception e) {
                    // TODO: we should catch when Image has tags that are not in tagManager
                }
            }
//        }
    }
}


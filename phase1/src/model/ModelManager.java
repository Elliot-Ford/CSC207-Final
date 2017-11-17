package model;

import model.file_explorer.FileManager;
import model.image_tag_explorer.Image;
import model.image_tag_explorer.ImageManager;
import model.image_tag_explorer.Tag;
import model.image_tag_explorer.TagManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        fileManager = new FileManager();
    }

    public ModelManager(String path) throws ClassNotFoundException {
        this();

        if (fileManager.ImagesFileExists(path)) {
            Image[] ret = fileManager.readImagesFromFile(path);
            imageManager = new ImageManager(ret);
            if (fileManager.TagsFileExists(path)) {
                Tag[] t = fileManager.readTagsFromFile(path);
                tagManager = new TagManager(t);
            }
        } else {
            fileManager.getFiles(path, "*.jpg");
            for (File file : fileManager.getFiles(path, FILE_MATCH_STRING)) {
                try {
                    imageManager.addImage(new Image(file));
                } catch (Exception e) {
                    // TODO: we should catch when Image has tags that are not in tagManager
                }
            }
        }
    }
}


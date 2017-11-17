package reStructure;

import reStructure.ImageFile;
import reStructure.TagManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ImageFileManager {
    //    private static final String SERIALIZED_IMAGES_FILENAME = ".images.ser";
    /** the name of a serialized file of tags */
    private static final String SERIALIZED_TAGS_FILENAME = ".tags.ser";
    /** String to match all tagable files */
    private static final String FILE_MATCH_STRING = ".*[.](jpg|png|gif|bmp)";

    /** the root of the directory */
    private File root;

    public ImageFileManager(String path) {
        
    }

}

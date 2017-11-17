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
        root = new File(path);
    }

    /**
     * Returns all files anywhere under the root directory.
     *
     * @return a File[] of all files anywhere under the root directory.
     */
    public File[] getAllFiles() {
        List<File> ret = new ArrayList<>();
        ret.add(root);
        int i = 0;
        while (i < ret.size()) {
            // Check if element at i in ret is a Directory, a File, or it exists.
            if (ret.get(i).isDirectory()) {
                // Element at i is a directory, so determine if it has children and remove the Element at i.
                // Check if the Element at i has children.
                if (ret.get(i).list() != null) {
                    // Element at i has children, so add children to the ArrayList.
                    ret.addAll(Arrays.asList(ret.get(i).listFiles()));
                }
                ret.remove(i);
            } else if (ret.get(i).isFile()) {
                // Element at i is a File, so increment i by 1.
                i += 1;
            } else {
                // Element doesn't exist in filesystem, so remove from ret.
                ret.remove(i);
            }
        }
        return ret.toArray(new File[ret.size()]);
    }

    /**
     * Return all files anywhere under the root directory that match the regular expression.
     *
     * @param regEx the regular expression to match.
     * @return a File[] of all files anywhere under the root directory that match the given regular
     *     expression.
     */
    public File[] getAllFiles(String regEx) {
        List<File> ret = new ArrayList<>();
        for (File file : getAllFiles()) {
            if (file.getName().matches(regEx)) {
                ret.add(file);
            }
        }
        return ret.toArray(new File[ret.size()]);
    }

    /**
     * Returns all the image files anywhere under the root directory.
     *
     * @return a ImageFile[] of all image files anywhere under the root directory.
     */
    public ImageFile[] getAllImageFiles() {
        List<ImageFile> ret = new ArrayList<>();
        for (File file : getAllFiles(FILE_MATCH_STRING)) {
            ret.add(new ImageFile(file));
        }
        return ret.toArray(new ImageFileManager[ret.size()]);
    }

    /**
     * Returns all the files directly under the root directory.
     *
     * @return a File[] of all files directly under the root directory.
     */
    public File[] getLocalFiles() {
        List<File> ret = new ArrayList<>();
        if(root.list() != null) {
            for (File file : root.listFiles()) {
                if (file.isFile()) {
                    ret.add(file);
                }
            }
        }
        return ret.toArray(new File[ret.size()]);
    }

    /**
     * Returns all the files directly under the root directory that match the given pattern of the
     * input string.
     *
     * @param regEx the given pattern to match.
     * @return an File[] of all files directly under the root directory that contain a match to the
     *     given pattern.
     */
    public File[] getLocalFiles(String regEx) {
        List<File> ret = new ArrayList<>();
        for (File file : getLocalFiles()) {
            if (file.getName().matches(regEx)) {
                ret.add(file);
            }
        }
        return ret.toArray(new File[ret.size()]);
    }

    /**
     * Returns all the image files directly under the root directory.
     *
     * @return a ImageFile[] of all images files directly under the root directory.
     */
    public ImageFile[] getLocalImageFiles() {
        List<ImageFile> ret = new ArrayList<>();
        for (File file : getLocalFiles(FILE_MATCH_STRING)) {
            ret.add(new ImageFile(file));
        }

        return ret.toArray(new ImageFile[ret.size()]);
    }

    // TODO: Create a method that moves a file from it's current directory to another directory
    // TODO: Create a method that returns whether a config file exists.



}

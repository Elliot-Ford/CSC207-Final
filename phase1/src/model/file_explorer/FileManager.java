package model.file_explorer;

import model.image_tag_explorer.Tag;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileManager {
//    private static final String SERIALIZED_IMAGES_FILENAME = ".images.ser";
    /** the name of a serialized file of tags */
    private static final String SERIALIZED_TAGS_FILENAME = ".tags.ser";

    /** the root of the directory */
    private File root;

    public FileManager(String path){
        root = new File(path);
    }

    /**
     * Returns a list representation of all the files of the given root.
     *
     * @return all the files that are found at/under the given root.
     */
    public File[] getAllFiles() {
        List<File> ret = new ArrayList<>();
        ret.add(root);
        int i = 0;
        while(i < ret.size()) {
            // Check if element at i in ret is a Directory, a File, or it exists.
            if(ret.get(i).isDirectory()) {
                // Element at i is a directory, so determine if it has children and remove the Element at i.
                // Check if the Element at i has children.
                if(ret.get(i).list() != null) {
                    // Element at i has children, so add children to the ArrayList.
                    ret.addAll(Arrays.asList(ret.get(i).listFiles()));
                }
                ret.remove(i);
            } else if(ret.get(i).isFile()) {
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
     * Returns all the files under the given root that contain a matching the given pattern of the input string
     * @param regEx the given pattern to match
     * @return an array of all files that contain a match to the given pattern
     */
    public File[] getAllFiles(String regEx) {
        List<File> ret = new ArrayList<>();
        File[] filesToMatch = getAllFiles();
        for(File file: filesToMatch) {
            if(file.getName().matches(regEx)) {
                ret.add(file);
            }
        }
        return ret.toArray(new File[ret.size()]);

    }

    /**
     * Returns all the files directly under the FileManger's root.
     * @return an array of all files directly under the FileManager's root.
     */
    public File[] getFiles() {
        List<File> ret = new ArrayList<>(root.list().length);
        for(File file: root.listFiles()) {
            if(file.isFile()) {
                ret.add(file);
            }
        }
        return ret.toArray(new File[ret.size()]);
    }

    /**
     * Returns all the files directly under the FileManager's root that match the given pattern of the input string.
     * @param regEx the given pattern to match.
     * @return an array of all files directly under the FileManager that contain a match to the given pattern.
     */
    public File[] getFiles(String regEx) {
        List<File> ret = new ArrayList<>();
        File[] filesToMatch = getFiles();
        for(File file: filesToMatch) {
            if(file.getName().matches(regEx)) {
                ret.add(file);
            }
        }
        return ret.toArray(new File[ret.size()]);
    }



    //TODO: Create a method that moves a file from it's current directory to another directory
    //TODO: Create a method that returns whether a config file exists.

//    /**
//     * Checks if the serializable file of Image exists
//     * @param path the path to the root folder
//     * @return True if the serialized file of Image exists, False if otherwise.
//     */
//    public boolean ImagesFileExists(String path) {
//        return new File(path, SERIALIZED_IMAGES_FILENAME).exists();
//    }
//
//    /**
//     * Reads the serialized Image[] file in the given root and returns the Image[]
//     * @param path the path to the root folder
//     * @return  Image[] store in serialized file.
//     * @throws ClassNotFoundException throws ClassNotFoundException if File doesn't exist.
//     */
//    public Image[] readImagesFromFile(String path) throws ClassNotFoundException {
//        Image[] ret = new Image[0];
//        try {
//            InputStream file = new FileInputStream(new File(path, SERIALIZED_IMAGES_FILENAME));
//            InputStream buffer = new BufferedInputStream(file);
//            ObjectInput input = new ObjectInputStream(buffer);
//
//            // deserialize the Array
//            ret = (Image[]) input.readObject();
//            input.close();
//
//
//        } catch (IOException ex) {
//            //TODO: log
//        }
//        return ret;
//    }
//
//    /**
//     * Writes a given Image[] to the serializable file under the given path. Throws IOException if can't write the file.
//     * @param path the path to the root folder.
//     * @param images the image[] array to store in serialized file.
//     * @throws IOException throw this exception if unable to write the file
//     */
//    public void saveImagesToFile(String path, Image[] images) throws IOException {
//        OutputStream file = new FileOutputStream(new File(path, SERIALIZED_IMAGES_FILENAME));
//        OutputStream buffer = new BufferedOutputStream(file);
//        ObjectOutput output = new ObjectOutputStream(buffer);
//
//        // serialize the Array's
//        output.writeObject(images);
//        output.close();
//    }

    /**
     * Checks if the serializable file of Tags exists
     * @param path the path to the root folder
     * @return True if the serialized file of Tags exists, False if otherwise.
     */
    public boolean TagsFileExists(String path) {
        return new File(path, SERIALIZED_TAGS_FILENAME).exists();
    }


    /**
     * Reads the serialized Tag[] file in the given root and returns the Image[]
     * @param path the path to the root folder
     * @return  Tag[] store in serialized file.
     * @throws ClassNotFoundException throws ClassNotFoundException if File doesn't exist.
     */
    public Tag[] readTagsFromFile(String path) throws ClassNotFoundException {
        Tag[] ret = new Tag[0];
        try {
            InputStream file = new FileInputStream(new File(path, SERIALIZED_TAGS_FILENAME));
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the Array
            ret = (Tag[]) input.readObject();
            input.close();


        } catch (IOException ex) {
            //TODO: log
        }
        return ret;
    }

    /**
     * Writes a given Tag[] to the serializable file under the given path. Throws IOException if can't write the file.
     * @param path the path to the root folder.
     * @param tags the tag[] array to store in serialized file.
     * @throws IOException throw this exception if unable to write the file.
     */
    public void saveTagsToFile(String path, Tag[] tags) throws IOException {

        OutputStream file = new FileOutputStream(path + SERIALIZED_TAGS_FILENAME);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Array's
        output.writeObject(tags);
        output.close();
    }
  /*public static void main(String[] args) {

      FileManager fileManager = new FileManager();

  }*/
}

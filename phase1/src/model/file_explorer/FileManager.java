package model.file_explorer;

import model.image_tag_explorer.Image;
import model.image_tag_explorer.Tag;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileManager {
  //    private static final String SERIALIZED_IMAGES_FILENAME = ".images.ser";
  /** the name of a serialized file of tags */
  private static final String SERIALIZED_TAGS_FILENAME = ".tags.ser";
  /** String to match all tagable files */
  private static final String FILE_MATCH_STRING = ".*[.](jpg|png|gif|bmp)";

  /** the root of the directory */
  private File root;

  public FileManager(String path) {
    root = new File(path);
//    //Probably code that would be better to be higher up.
//    if (!TagsFileExists()) {
//		try {
//			saveTagsToFile(new Tag[0]);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	} else {
//
//	}
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
   * @return a Image[] of all image files anywhere under the root directory.
   */
  public Image[] getAllImages() {
    List<Image> ret = new ArrayList<>();
    for (File file : getAllFiles(FILE_MATCH_STRING)) {
      ret.add(new Image(file));
    }
    return ret.toArray(new Image[ret.size()]);
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
   * @return a Image[] of all images files directly under the root directory.
   */
  public Image[] getImages() {
    List<Image> ret = new ArrayList<>();
    for (File file : getLocalFiles(FILE_MATCH_STRING)) {
      ret.add(new Image(file));
    }

    return ret.toArray(new Image[ret.size()]);
  }

  // TODO: Create a method that moves a file from it's current directory to another directory
  // TODO: Create a method that returns whether a config file exists.

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
  //     * Writes a given Image[] to the serializable file under the given path. Throws IOException
  // if can't write the file.
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
   *
   * @return True if the serialized file of Tags exists, False if otherwise.
   */
  public boolean TagsFileExists() {
    return new File(root, SERIALIZED_TAGS_FILENAME).exists();
  }

  /**
   * Reads the serialized Tag[] file in the given root and returns the Image[]
   *
   * @return Tag[] store in serialized file.
   * @throws ClassNotFoundException throws ClassNotFoundException if File doesn't exist.
   */
  public Tag[] readTagsFromFile() throws ClassNotFoundException {
    Tag[] ret = new Tag[0];
    try {
      InputStream file = new FileInputStream(new File(root, SERIALIZED_TAGS_FILENAME));
      InputStream buffer = new BufferedInputStream(file);
      ObjectInput input = new ObjectInputStream(buffer);

      // deserialize the Array
      ret = (Tag[]) input.readObject();
      input.close();

    } catch (IOException ex) {
      // TODO: log
    }
    return ret;
  }

  /**
   * Writes a given Tag[] to the serializable file under the given path. Throws IOException if can't
   * write the file.
   *
   * @param tags the tag[] array to store in serialized file.
   * @throws IOException throw this exception if unable to write the file.
   */
  public void saveTagsToFile(Tag[] tags) throws IOException {

    OutputStream file = new FileOutputStream(new File(root, SERIALIZED_TAGS_FILENAME));
    OutputStream buffer = new BufferedOutputStream(file);
    ObjectOutput output = new ObjectOutputStream(buffer);

    // serialize the Array's
    output.writeObject(tags);
    output.close();
  }
  public static void main(String[] args) {
      FileManager fileManager = new FileManager("");

    Scanner scanner = new Scanner(System.in);
    String input = "";
    while(!input.equals("exit")) {
      System.out.println("Enter a command (type \"exit\" to exit):\n" +
              "(1) to change directory\n" +
              "(2) to get Files\n" +
              "(3) to get all Files\n" +
              "(4) to get Images\n" +
              "(5) to get all images");
      input = scanner.nextLine();
      String output = "";
      switch (input) {
        case "1":
          System.out.println("Where to?");
          input = scanner.nextLine();
          fileManager = new FileManager(input);
          output = "Changed directory to" + input;
          break;

        case "2":
          output = Arrays.toString(fileManager.getLocalFiles());
          break;

        case "3":
          output = Arrays.toString(fileManager.getAllFiles());
          break;

        case "4":
          output = Arrays.toString(fileManager.getImages());
          break;

        case "5":
          output = Arrays.toString(fileManager.getAllImages());
      }
      if(output.equals("") && !input.equals("exit")) {
        output = "This is not a valid input.";
      }
      System.out.println(output);
    }

  }
}

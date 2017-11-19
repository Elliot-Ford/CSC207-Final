package model;

import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/** Represents a physical image file in a filesystem. */
@SuppressWarnings("WeakerAccess")
public class ImageFile extends Observable{

  private static final String LOG_FILE_SUFFIX = ".log";

  private static final String TAG_MARKER = "@";

  private static final String LOG_FILE_SEPARATOR = "|";

  /** the image file in the system */
  private File file;

  /** the previous names for the file */
  private File log;

  /**
   * Construct a new ImageFile object with a given path.
   *
   * @param path the directory this ImageFile object is under
   */
  public ImageFile(String path) {
    this(new File(path));
  }

  /**
   * Construct a new ImageFile object representation of a physical file.
   *
   * @param file the physical file for this ImageFile
   */
  public ImageFile(File file) {
    this.file = file;
    log = new File(file.getParent(), getName() + LOG_FILE_SUFFIX);
    if (!log.exists() && file.exists()) {
      try {
        log.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Move the ImageFile to a given directory.
   *
   * @param newPath the string representation of the given directory
   * @return Whether this moving of the ImageFile was successful
   * @throws IOException Throws an exception if we cannot write the file
   */
  public boolean moveFile(String newPath) throws IOException {
    File newFile = new File(newPath, getName() + getSuffix());
    File newLog = new File(newPath, getName() + LOG_FILE_SUFFIX);
    boolean ret = file.renameTo(newFile) && log.renameTo(newLog);
    if (ret) {
      if (newFile.exists()) {
        file = newFile;
      }
      if (newLog.exists()) {
        log = newLog;
      }
    }
    return ret;
  }

  /**
   * gets all the tags associated to the image file.
   * @return a String[] of associated tags.
   */
  public String[] getTags() {
    return extractTags(getName());
  }

  /** Extracts the Tags in a given string */
  private String[] extractTags(String stringWithTags) {
    List<String> tags = new ArrayList<>();
    String[] slicedString = stringWithTags.split(" ");
    for (String word : slicedString) {
      // Check each word to see if its format indicates it's a tag.
      if (word.length() >= TAG_MARKER.length()
          && word.substring(0, TAG_MARKER.length()).equals(TAG_MARKER)) {
        tags.add(word.substring(1));
      }
    }
    return tags.toArray(new String[tags.size()]);
  }

  /**
   * gets all the tags that were previously associated to the image file.
   * @return a String[] of previously associated tags.
   */
  @SuppressWarnings("ResultOfMethodCallIgnored")
  public String[] getPreviousTags() throws IOException {
    Set<String> tags = new HashSet<>();
    String[] currentTags = getTags();
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(log.getPath()));
    } catch (FileNotFoundException e) {
      //log file must not exist
      log.createNewFile();
    }
    String line = reader.readLine();
    while (line != null) {
      String[] potentialTags = extractTags(line.split(LOG_FILE_SEPARATOR)[0]);
      for (String potentialTag : potentialTags) {
        if (currentTags.length > 0) {
          for (String currentTag : currentTags) {
            if (!currentTag.equals(potentialTag)) {
              tags.add(potentialTag);
            }
          }
        } else {
          tags.add(potentialTag);
        }
      }
      line = reader.readLine();
    }
    reader.close();

    return tags.toArray(new String[tags.size()]);
  }

  /**
   * Tries to add a given tag to the image.
   * @param newTag the tag to try and add.
   * @return true if successful, false if it isn't.
   */
  public boolean addTag(String newTag) {
    boolean success = rename(String.format("%s %s%s", getName(), TAG_MARKER, newTag));
    setChanged();
    notifyObservers();
    return success;
  }

  /**
   * Tries to remove the given tag from the image.
   * @param thisTag the tag to try and remove.
   * @return true if removal is successful, false if it isn't.
   */
  public boolean removeTag(String thisTag) {
    boolean ret = false;
    if (file.getName().contains(thisTag)) {
        ret = rename(getName().replace(String.format(" %s%s", TAG_MARKER, thisTag), ""));
    }
    return ret;
  }

  /**
   * Tries to rename the image with the newName
   * @param newName a String to try and rename the image with.
   * @return true if renaming is successful, false if it isn't.
   */
  public boolean rename(String newName) {
    String lastName = getName();
    File newFile = new File(file.getParent(), newName + getSuffix());
    File newLog = new File(log.getParent(), newName + LOG_FILE_SUFFIX);
    boolean ret = false;
    // get the time
    Date time = new Date();
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    if(!newFile.exists() && !newLog.exists()) {
      ret = file.renameTo(newFile) && log.renameTo(newLog);
    }
    if (ret) {
      if (newFile.exists()) {
        file = newFile;
      }
      if (newLog.exists()) {
        log = newLog;
      }
      try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(log, true));
        writer.append(String.format("%s %s %s %s %s", lastName, LOG_FILE_SEPARATOR, newName, LOG_FILE_SEPARATOR,
                dateFormat.format(time))).append(String.valueOf('\n'));
        writer.close();
      } catch (IOException ex) {
        ex.printStackTrace();
        ret = false;
      }
    }
    return ret;
  }

  /**
   * Returns the name of an image with no suffix.
   *
   * @return the name of an image.
   */
  public String getName() {
    String ret = "";
    if (file.getName().lastIndexOf(".") != -1) {
      ret = file.getName().substring(0, file.getName().lastIndexOf("."));
    }
    return ret;
  }

  /**
   * Returns the suffix of an image.
   *
   * @return the suffix of an image
   */
  public String getSuffix() {
    String ret = "";
    if (file.getName().lastIndexOf(".") != -1) {
      ret = file.getName().substring(file.getName().lastIndexOf("."));
    }
    return ret;
  }

  @Override
  public String toString() {
    return file.getName();
  }

  /**
   * Returns the physical Image File.
   *
   * @return the physical Image File.
   */
  public File getImage() {
    return file;
  }

  public static void main(String[] args) {
    ImageFile imageFile = new ImageFile("/Users/Jeremy/Desktop/test/Sakshi.jpg");

    Scanner scanner = new Scanner(System.in);
    String input = "";
    while (!input.equals("exit")) {
      System.out.println(
          "Enter a number to execute a command (type \"exit\" to exit):\n"
              + "(1) change to a new file\n"
              + "(2) to add a Tag\n"
              + "(3) to remove a Tag\n"
              + "(4) to get Tags\n"
              + "(5) to get previous Tags\n"
              + "(6) to move file\n");
      input = scanner.nextLine();
      String output = "";
      switch (input) {
        case "1":
          System.out.println("Where to?");
          input = scanner.nextLine();
          imageFile = new ImageFile(input);
          output = "Changed to " + input;
          break;

        case "2":
          System.out.println("What tag should I add?");
          input = scanner.nextLine();
            if (imageFile.addTag(input)) {
              output = "Added " + input;
            } else {
              output = "Adding tag failed";
            }
          break;

        case "3":
          System.out.println("What tag should I remove?");
          input = scanner.nextLine();
          if (imageFile.removeTag(input)) {
            output = "Removed " + input;
          } else {
            output = "Removing tag failed";
          }

          break;

        case "4":
          output = Arrays.toString(imageFile.getTags());
          break;

        case "5":
          try {
            output = Arrays.toString(imageFile.getPreviousTags());
          } catch (IOException e) {
            output = "Getting previous tags threw error";
          }
          break;

        case "6":
          System.out.println("Where should I move the image?");
          input = scanner.nextLine();
          try {
            if (imageFile.moveFile(input)) {
              output = "Moved image" + input;
            } else {
              output = "Moving image failed";
            }
          } catch (IOException e) {
            output = "Moving Image threw error";
          }
      }
      if (output.equals("") && !input.equals("exit")) {
        output = "This is not a valid input.";
      }
      System.out.println(output);
    }
  }
}

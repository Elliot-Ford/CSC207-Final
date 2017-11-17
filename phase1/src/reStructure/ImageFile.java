package reStructure;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/** Represents a physical image file in a filesystem. */
public class ImageFile {

  private static final String LOG_FILE_SUFFIX = ".log";

  private static final String TAG_MARKER = "@";

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
    file = new File(path);
    log =
        new File(
            file.getParent(),
            file.getName().substring(0, file.getName().lastIndexOf('.')) + LOG_FILE_SUFFIX);
    if (!log.exists() && file.exists()) {
      try {
        log.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Construct a new ImageFile object representation of a physical file.
   *
   * @param file the physical file for this ImageFile
   */
  public ImageFile(File file) {
    this.file = file;
    log =
        new File(
            file.getAbsolutePath(),
            file.getName().substring(0, file.getName().lastIndexOf('.')) + LOG_FILE_SUFFIX);
  }

  // TODO: change the directory of this file

  /**
   * Move the ImageFile to a given directory.
   *
   * @param newPath the string representation of the given directory
   * @return Whether this moving of the ImageFile was successful
   * @throws IOException Throws an exception if we cannot write the file
   */
  public boolean moveFile(String newPath) throws IOException {
    boolean success = file.renameTo(new File(newPath));
    if (success) {
      log.renameTo(
          new File(
              newPath,
              file.getName().substring(0, file.getName().lastIndexOf('.')) + LOG_FILE_SUFFIX));
    }
    return success;
  }

  // TODO: get tags
  public String[] getTags() {
    return extractTags(file.getName().substring(0, file.getName().lastIndexOf(".")));
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

  // TODO: get previous tags
  public String[] getPreviousTags() throws IOException {
    List<String> tags = new ArrayList<>();
    String[] currentTags = getTags();
    BufferedReader reader = new BufferedReader(new FileReader(log.getPath()));
    String line = reader.readLine();
    while (line != null) {
      String[] potentialTags = extractTags(line);
      for(String potentialTag : potentialTags) {
          boolean found = false;
          for(String currentTag: currentTags) {
              if (currentTag.equals(potentialTag)) {
                  found = true;
              }
          }
          if(!found) {
              tags.add(potentialTag);
          }
      }
      line = reader.readLine();
    }
    reader.close();

    return tags.toArray(new String[tags.size()]);
  }

  // TODO: add tag
  public boolean addTag(String newTag) throws IOException {
    String lastName = file.getName().substring(0, file.getName().lastIndexOf('.'));
    File newFile =
        new File(
            file.getParent(),
            String.format(
                "%s %s%s%s",
                file.getName().substring(0, file.getName().lastIndexOf('.')),
                TAG_MARKER,
                newTag,
                file.getName().substring(file.getName().lastIndexOf("."))));
    File newLog =
        new File(
            log.getParent(),
            String.format(
                "%s%s",
                newFile.getName().substring(0, newFile.getName().lastIndexOf(".")),
                LOG_FILE_SUFFIX));
    boolean imageRenameSuccess = file.renameTo(newFile);
    boolean logRenameSuccess = log.renameTo(newLog);
    // Add the last name to the log file.
    if (imageRenameSuccess && logRenameSuccess) {
      file = newFile;
      log = newLog;
      BufferedWriter writer = new BufferedWriter(new FileWriter(log, true));
      writer.append(lastName);
      writer.append('\n');
      writer.close();
    }

    return imageRenameSuccess && logRenameSuccess;
  }

  // TODO: remove tag
  public boolean removeTag(String thisTag) throws IOException {
    boolean success = false;
    if (file.getName().contains(thisTag)) {
      String lastName = file.getName().substring(0, file.getName().lastIndexOf('.'));
      // Rename the current file.
      String newName = file.getName().replace((" " + TAG_MARKER + thisTag), "");
      success = file.renameTo(new File(file.getParent(), newName));
      // Add the last name to the log file.
      if (success) {
        BufferedWriter writer = new BufferedWriter(new FileWriter(log));
        writer.append('\n');
        writer.append(lastName);
        writer.close();
      }
    }

    return success;
  }

    public static void main(String[] args) {
        ImageFile imageFile = new ImageFile("");

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
                    try {
                        if (imageFile.addTag(input)) {
                            output = "Added " + input;
                        } else {
                            output = "Adding tag failed";
                        }
                    } catch (IOException ex) {
                        output = "Adding tag threw error";
                    }
                    break;

                case "3":
                    System.out.println("What tag should I remove?");
                    input = scanner.nextLine();
                    try {
                        if (imageFile.removeTag(input)) {
                            output = "Removed " + input;
                        } else {
                            output = "Removing tag failed";
                        }
                    } catch (IOException ex) {
                        output = "Removing tag threw error";
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
            }
            if (output.equals("") && !input.equals("exit")) {
                output = "This is not a valid input.";
            }
            System.out.println(output);
        }
    }
}

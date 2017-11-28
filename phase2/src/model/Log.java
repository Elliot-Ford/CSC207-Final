package model;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/** Manages the log file for the target class (ImageFile and TagManager in our model). */
public class Log {

  private static final String LOG_FILE_SUFFIX = ".log";
  private static final String LOG_FILE_SEPARATOR = " / ";
  private static final String LOG_FILE_PREFIX = ".";
  /** the log file for the target class. */
  private File log;

  //    private static final String TAG_MARKER = "@";

  /**
   * Creates a log file for a file
   *
   * @param file the physical file this log file is created for.
   */
  public Log(File file) {
    log = new File(file.getParent(), LOG_FILE_PREFIX + file.getName() + LOG_FILE_SUFFIX);
    if (!log.exists()) {
      try {
        log.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    if (System.getProperty("os.name").contains("Windows")) {
      Path logPath =
          FileSystems.getDefault().getPath(log.getParentFile().getAbsolutePath(), log.getName());
      try {
        Files.setAttribute(logPath, "dos:hidden", true);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /** @param fileName */
  public Log(String root, String fileName) {
    this(new File(root, fileName));
  }

  /**
   * When the file is moved, move this log file as well.
   *
   * @param newPath the String representation of the given directory
   * @param file the target file of this log file
   * @return Whether this moving the files was successful
   */
  boolean moveFile(String newPath, File file, boolean ret1) {
    File newLog = new File(newPath, LOG_FILE_PREFIX + file.getName() + LOG_FILE_SUFFIX);
    boolean ret = ret1 && log.renameTo(newLog);
    if (ret) {
      if (newLog.exists()) {
        log = newLog;
      }
    }
    return ret;
  }

  /**
   * Record the renaming of the target file in the log file.
   *
   * @param lastName the last name of the target file
   * @param newName the new name of the target file
   * @param newLogName the new log file
   * @return the log file after recording the renaming
   */
  public boolean rename(String lastName, String newName, String newLogName) {
    File newLog =
        new File(
            log.getParentFile(),
            String.format("%s%s%s", LOG_FILE_PREFIX, newLogName, LOG_FILE_SUFFIX));
    //        if (!newLog.exists()) {
    boolean ret = log.renameTo(newLog);
    if (ret) {
      log = newLog;
    }
    // Add the new line into the now log file.
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(log, true));
      writer.append(
          String.format(
              "%s%s%s%s%tD %tT\n",
              lastName,
              LOG_FILE_SEPARATOR,
              newName,
              LOG_FILE_SEPARATOR,
              Calendar.getInstance(),
              Calendar.getInstance()));
      writer.close();
    } catch (IOException ex) {
      ex.printStackTrace();
      ret = false;
    }
    return ret;
  }

  void rename(String lastName, String newName) {
    rename(lastName, newName, log.getName().substring(1, log.getName().lastIndexOf(".")));
  }

  /**
   * Return a list of String representations of the recording of the tag changes in the log file. a
   * String in the String[] has the following format:
   *
   * <p>"firstData / secondData / MM/DD/YY HH:MM:SS"
   *
   * @return the list of String representations
   */
  public String[] getLog() {
    List<String> logs = new ArrayList<>();
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(log.getPath()));
      String line = reader.readLine();
      while (line != null) {
        logs.add(line);
        line = reader.readLine();
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return logs.toArray(new String[logs.size()]);
  }

  /**
   * Returns the column of entries at the given number between 0-2.
   *
   * @column: the column of data to get, should be a value between 0-2 inclusive.
   */
  String[] getColumn(int column) {
    List<List<String>> tabledLog = new ArrayList<>();
    List<String> ret = new ArrayList<>();
    for (String logEntry : getLog()) {
      List<String> inner = new ArrayList<>();
      inner.addAll(Arrays.asList(logEntry.split(LOG_FILE_SEPARATOR)));
      tabledLog.add(inner);
    }
    for (List<String> logRow : tabledLog) {
      String[] tagSet = (logRow.get(column).split(","));
      for (String s : tagSet) {
        s = s.replaceFirst("\\[", "");
        s = s.replaceFirst("]", "");
        s = s.trim();
        ret.add(s);
      }
    }
    return ret.toArray(new String[ret.size()]);
  }

  //    /**
  //     * Return if this log file exits.
  //     *
  //     * @return return true if this log file exist, false if not
  //     */
  //    boolean exists() {
  //        return log.exists();
  //    }

//  /**
//   * Return the physical log file of this Log Object.
//   *
//   * @return the physical log file
//   */
//  public File getFile() {
//    return log;
//  }

//  Set<String> getPreviousGlobalTags() {
//    Set<String> tags = new HashSet<>();
//    try {
//      BufferedReader reader = new BufferedReader(new FileReader(log.getPath()));
//      String line = reader.readLine();
//      while (line != null) {
//        String curr = line;
//        line = reader.readLine();
//        if (line == null) {
//          String[] lineList = curr.split(LOG_FILE_SEPARATOR);
//          String set = lineList[1];
//          String[] tagSet = set.split(",");
//          for (String s : tagSet) {
//            s = s.replaceFirst("\\[", "");
//            s = s.replaceFirst("]", "");
//            s = s.trim();
//            tags.add(s);
//          }
//        }
//        line = reader.readLine();
//      }
//      reader.close();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    return tags;
//  }
}

package file_explorer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileManager {
    List<File> DirectoryLeafs;

    public FileManager(String root) {
        DirectoryLeafs = getAllLeafs(new File(root));
    }

    public FileManager(File root) {
        DirectoryLeafs = getAllLeafs(root);

    }

    /** returns a list representation of all the non-directory files of the given root */
    private List<File> getAllLeafs(File root) {
        List<File> ret = new ArrayList<>();
        if(root.isDirectory() && root.listFiles() != null) {
            for(File subFile: root.listFiles()) {
                ret.addAll(getAllLeafs(subFile));
            }
        } else {
            ret.add(root);
        }
        return ret;
    }

    /**
     * Returns all the Files that contain a matching pattern to the input string
     * @param regex the given pattern to match
     * @return an array of all files that contain a match to the given pattern
     */
    public File[] getFiles(String regex) {
        List<File> ret = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        for(File file: DirectoryLeafs) {
            if(pattern.matcher(file.getName()).find()) {
                ret.add(file);
            }
        }
        return ret.toArray(new File[ret.size()]);

    }

    public List<File> getDirectoryLeafs() {
        return DirectoryLeafs;
    }

  public static void main(String[] args) {

      FileManager fileManager = new FileManager("put the complete file path here");
      for(File file:fileManager.getDirectoryLeafs()) {
          System.out.println(file.getName());
      }
  }
}

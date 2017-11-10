package file_explorer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileManager {

    public FileManager(){
    }

    /**
     * Returns a list representation of all the files of the given root.
     *
     * @param root the root of the directory.
     * @return all the files that are found at/under the given root.
     */
    public List<File> getAllFiles(File root) {
        List<File> ret = new ArrayList<>();
        if(root.isDirectory() && root.listFiles() != null) {
            for(File subFile: root.listFiles()) {
                ret.addAll(getAllFiles(subFile));
            }
        } else {
            ret.add(root);
        }
        return ret;
    }

    /**
     * Returns all the files under the given root that contain a matching pattern to the input string
     * @param regex the given pattern to match
     * @return an array of all files that contain a match to the given pattern
     */
    public List<File> getFiles(File root, String regex) {
        List<File> ret = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        List<File> filesToMatch = getAllFiles(root);
        for(File file: filesToMatch) {
            if(pattern.matcher(file.getName()).find()) {
                ret.add(file);
            }
        }
        return ret;

    }

  /*public static void main(String[] args) {

      FileManager fileManager = new FileManager();

  }*/
}

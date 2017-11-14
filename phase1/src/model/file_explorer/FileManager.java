package model.file_explorer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileManager {

    public FileManager(){
    }

    /**
     * Returns a list representation of all the files of the given root.
     *
     * @param root the root of the directory.
     * @return all the files that are found at/under the given root.
     */
    public File[] getAllFiles(File root) {
        List<File> ret = new ArrayList<>();
        if(root.isDirectory() && root.listFiles() != null) {
            for(File subFile: root.listFiles()) {
                ret.addAll(Arrays.asList(getAllFiles(subFile)));
            }
        } else if(root.isFile()) {
            ret.add(root);
        }
        return ret.toArray(new File[ret.size()]);
    }

    /**
     * Returns all the files under the given root that contain a matching pattern to the input string
     * @param regEx the given pattern to match
     * @return an array of all files that contain a match to the given pattern
     */
    public File[] getFiles(File root, String regEx) {
        List<File> ret = new ArrayList<>();
        File[] filesToMatch = getAllFiles(root);
        for(File file: filesToMatch) {
            if(file.getName().matches(regEx)) {
                ret.add(file);
            }
        }
        return ret.toArray(new File[ret.size()]);

    }

  /*public static void main(String[] args) {

      FileManager fileManager = new FileManager();

  }*/
}

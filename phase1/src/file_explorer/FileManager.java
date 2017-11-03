package file_explorer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
}

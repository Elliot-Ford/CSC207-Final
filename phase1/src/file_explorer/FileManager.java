package file_explorer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    List<File> nonDirectoryFileList;

    public FileManager(String root) {
        nonDirectoryFileList = getAllLeafs(new File(root));
    }

    public FileManager(File root) {
        nonDirectoryFileList = getAllLeafs(root);
    }

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

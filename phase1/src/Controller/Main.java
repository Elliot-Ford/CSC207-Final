package Controller;

import model.ImageFile;
import model.ImageFileManager;

import java.io.IOException;

public class Main {

    //TODO: get path-directory and start the program
    public static void Main (String args[]) {
        String path = "";
        //get the path from the user interface
        ImageFileManager IFManager1 = new ImageFileManager(path);
    }

    //TODO: get images
    public ImageFile[] getImageFiles(ImageFileManager IFManager) {
        return IFManager.getAllImageFiles();
    }
    //TODO: get tags
    public String[] getTags(ImageFileManager IFManager) {
        return IFManager.getAllCurrentTags();
    }

    //TODO: add tags
    public boolean addTag(ImageFile IFile, String tag) {
        return IFile.addTag(tag);
    }

    //TODO: remove tags
    public boolean removeTag(ImageFile IFile, String tag) {
        return IFile.removeTag(tag);
    }

    //TODO: move File
    public boolean moveFile(ImageFile IFile, String newPath) throws IOException {
        return IFile.moveFile(newPath);
    }

    //TODO: get previous tags for one file
    public String[] getLog(ImageFile IFile) throws IOException {
        return IFile.getPreviousTags();
    }

    //TODO: come up with more?

}

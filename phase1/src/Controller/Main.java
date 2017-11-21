package Controller;

import model.ImageFile;
import model.ImageFileManager;

import java.io.IOException;

public class Main {
    /**
     * Construct a new Main object for the Controller.
     */
    public Main() {
    }

    /**
     * Return a new ImageFileManager when the GUI requested.
     *
     * @param path the path for the directory
     * @return the new ImageFileManager
     */
    public ImageFileManager startProgram(String path) {
        return new ImageFileManager(path);
    }

    /**
     * Return all the ImageFiles under one ImageFileManager.
     *
     * @param IFManager the ImageFileManager
     * @return a list of ImageFile
     */
    public ImageFile[] getImageFiles(ImageFileManager IFManager) {
        return IFManager.getAllImageFiles();
    }

    /**
     * Return the String representation of all Tags under one ImageFileManager.
     *
     * @param IFManager the ImageFileManager
     * @return a list of String representations of Tags
     */
    public String[] getTags(ImageFileManager IFManager) {
        return IFManager.getAllCurrentTags();
    }

    /**
     * Add a Tag to a given ImageFile.
     *
     * @param IFile the ImageFile
     * @param tag   the new Tag we want to add to the ImageFile
     * @return whether this adding of a tag was successful
     */
    public boolean addTag(ImageFile IFile, String tag) {
        return IFile.addTag(tag);
    }

    /**
     * Remove a Tag from a given ImageFile.
     *
     * @param IFile the ImageFile
     * @param tag   the Tag we want to remove from the ImageFile
     * @return whether this removal was successful
     */
    public boolean removeTag(ImageFile IFile, String tag) {
        return IFile.removeTag(tag);
    }

    /**
     * Move an ImageFile to a different directory.
     *
     * @param IFile   the ImageFile
     * @param newPath the Path for the new directory
     * @return whether this moving of the ImageFile was successful
     * @throws IOException throw an Exception if the File cannot be moved for some reason
     */
    public boolean moveFile(ImageFile IFile, String newPath) throws IOException {
        return IFile.moveFile(newPath);
    }

    /**
     * Get the String representation of all the previous Tags for a given ImageFile.
     *
     * @param IFile the ImageFile
     * @return a list of String representation of all the previous Tags for a given ImageFile
     * @throws IOException throw an Exception if we cannot get the previous Tags for this ImageFile for some reason
     */
    public String[] getLog(ImageFile IFile) throws IOException {
        return IFile.getPreviousTags();
    }

    /**
     * Rename a given ImageFile.
     *
     * @param IFile   the ImageFIle
     * @param newName the new name we want to rename the ImageFile with
     * @return whether this renaming was successful
     */
    public boolean renameFile(ImageFile IFile, String newName) {
        return IFile.rename(newName);
    }

}

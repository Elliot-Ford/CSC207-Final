package reStructure;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a physical image file in a filesystem.
 */
public class ImageFile {

    private static final String LOG_FILE_SUFFIX = ".log";

    private static final String TAG_MARKER = "@";

    /**
     * the image file in the system
     */
    private File file;

    /**
     * the previous names for the file
     */
    private File log;

    /**
     * Construct a new ImageFile object with a given path.
     *
     * @param path the directory this ImageFile object is under
     */
    public ImageFile(String path) {
        file = new File(path);
        log = new File(path,
                file.getName().substring(0, file.getName().lastIndexOf('.')) + LOG_FILE_SUFFIX);
    }

    /**
     * Construct a new ImageFile object representation of a physical file.
     *
     * @param file the physical file for this ImageFile
     */
    public ImageFile(File file) {
        this.file = file;
        log = new File(file.getAbsolutePath(),
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
            log.renameTo(new File(newPath,
                    file.getName().substring(0, file.getName().lastIndexOf('.')) + LOG_FILE_SUFFIX));
        }
        return success;
    }

    // TODO: get tags
    public String[] getTags() {
        List<String> tags = new ArrayList<>();
        String[] fileName = file.getName().split(" ");
        for (String s : fileName) {
            if (s.length() >= TAG_MARKER.length()
                    && s.substring(0, TAG_MARKER.length()).equals(TAG_MARKER)) {
                tags.add(s);
            }
        }
        return tags.toArray(new String[tags.size()]);
    }

    // TODO: get previous tags
    public String[] getPreviousTags() throws IOException {
        List<String> tags = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(log.getPath()));
        String line = reader.readLine();
        while (line != null) {
            String[] fileName = file.getName().split(" ");
            // Check each string to see if its format indicates it's a tag.
            for (String s : fileName) {
                if (s.length() >= TAG_MARKER.length()
                        && s.substring(0, TAG_MARKER.length()).equals(TAG_MARKER)
                        && !tags.contains(s.substring(0, TAG_MARKER.length()))) {
                    // Add the tag into the tag list.
                    tags.add(s);
                }
            }
        }
        reader.close();

        return tags.toArray(new String[tags.size()]);
    }

    // TODO: add tag
    public boolean addTag(String newTag) throws IOException {
        String lastName = file.getName().substring(0, file.getName().lastIndexOf('.'));
        boolean success = file.renameTo(new File(file.getParent(),
                (file.getName().substring(0, file.getName().lastIndexOf('.'))
                        + " " + TAG_MARKER + newTag + LOG_FILE_SUFFIX)));
        // Add the last name to the log file.
        if (success) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(log));
            writer.append('\n');
            writer.append(lastName);
            writer.close();
        }

        return success;
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
            if(success) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(log));
                writer.append('\n');
                writer.append(lastName);
                writer.close();
            }
        }

        return success;
    }

}

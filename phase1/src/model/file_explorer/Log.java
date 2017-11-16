package model.file_explorer;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    public Logger logger;
    FileHandler fileHandler;

    public Log(String file_name) throws SecurityException, IOException {

        File f = new File(file_name);
        if(!f.exists())
        {
            f.createNewFile();
        }

        fileHandler = new FileHandler();

        logger = Logger.getLogger("logs of all renaming ever done");
        logger.setLevel(Level.INFO);
        logger.addHandler(fileHandler);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
    }
}

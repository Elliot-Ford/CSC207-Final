package viewer;

import javafx.application.Application;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Console extends Application {

  Stage window;

  public Console() {}

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    DirectoryChooser dc = new DirectoryChooser();
    OpenFile op = new OpenFile();

    dc.setInitialDirectory(new File(System.getProperty("user.home")));
    File file = dc.showDialog(window);
    try {
      op.openFile(file, primaryStage);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
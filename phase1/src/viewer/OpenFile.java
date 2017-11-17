package viewer;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public final class OpenFile{
    Stage window;

    public void openFile(Stage stage) {
        window = stage;
        window.show();
        GridPane newGrid = new GridPane();

        Scene scene2 = new Scene(newGrid, 1020, 720);
        window.setScene(scene2);
        window.setTitle("Lets do Tagging");



    }
}
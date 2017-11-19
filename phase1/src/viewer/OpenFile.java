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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;


public final class OpenFile{

    Stage window;

    public void openFile(File file,Stage stage) {
        window = stage;
        window.setTitle("Lets do Tagging");

        GridPane newGrid = new GridPane();
        newGrid.setAlignment(Pos.CENTER);
        newGrid.setPadding(new Insets(10, 10, 10, 10));
        newGrid.setVgap(10);
        newGrid.setHgap(10);

        TreeView<String> a = new TreeView<>();
        GridPane.setConstraints(a,0,0);
        a.setRoot(getNodesForDirectory(file));

        newGrid.getChildren().addAll(a);
        Scene scene = new Scene(newGrid, 1020, 720);
        window.setScene(scene);
        window.show();
    }

    public TreeItem<String> getNodesForDirectory(File directory) {
        TreeItem<String> root = new TreeItem<>(directory.getName());
        for(File f : directory.listFiles()) {
//            System.out.println("Loading " + f.getName());
            if(f.isDirectory()) { //Then we call the function recursively
                root.getChildren().add(getNodesForDirectory(f));
            } else {
                root.getChildren().add(new TreeItem<>(f.getName()));
            }
        }
        return root;
    }

}
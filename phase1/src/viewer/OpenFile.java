package viewer;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import Controller.Main;
import model.ImageFile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.ImageFileManager;


public final class OpenFile{

    Stage window;

    private static void open(Stage stage) {
        Application.launch(Console.class);
    }



    public void openFile(File file,Stage stage) throws Exception {
        final int[] toggleViewer = {-1};

        window = stage;
        window.setTitle("Lets do Tagging");

        GridPane newGrid = new GridPane();
//        newGrid.setAlignment(Pos.TOP_LEFT);
        newGrid.setPadding(new Insets(10, 10, 10, 10));
        newGrid.setVgap(10);
        newGrid.setHgap(10);

        ImageFileManager imageFileManager = new ImageFileManager(file);
        ImageFile[] imageList = imageFileManager.getAllImageFiles();
        Map<String, ImageFile> fileMap = new HashMap<>();
        TreeView<String> a = new TreeView<>();
        for(ImageFile imageFile: imageList) {
            fileMap.put(imageFile.toString(), imageFile);
        }
//        newGrid.setAlignment(Pos.CENTER_LEFT);
        GridPane.setConstraints(a,0,0);
        TreeItem<String> tree = getNodesForDirectory(file, Arrays.toString(imageList));
        TreeItem<String> holderTree = new TreeItem<>(file.getName() + " (Tree View)");
        holderTree.getChildren().addAll(tree.getChildren());
        tree = holderTree;



        a.setRoot(tree);
        a.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {
                    if (newValue != null) {
                        if (newValue.getValue() != null){
                            if (fileMap.containsKey(newValue.getValue())){
                                 ImageFile imageFile = fileMap.get(newValue.getValue());

                            }
                        }
                    }
                });
        // button for browsing between directories

        Button browseFiles = new Button("Change Directory");
        browseFiles.setOnAction( e -> {
            OpenFile.open(window);
            //link it to the console
        });

        GridPane.setConstraints(browseFiles,1,5);

        // button that shows all the image under the the selected directory

        Button toggle = new Button("Toggle View");
//        newGrid.setAlignment(Pos.TOP_LEFT);
        GridPane.setConstraints(toggle,2,5);
        toggle.setOnAction(e -> {
            if(toggleViewer[0] == -1) {
                toggleViewer[0] += 1;
                TreeItem<String> newTree = new TreeItem<>(file.getName() +" (Recursive View)");

//                TreeItem<String> tempTree = new TreeItem<>(file.getName() + " (All View)");
//                tempTree.getChildren().addAll(newTree.getChildren());
//                newTree = tempTree;
                for(ImageFile imageFile: imageFileManager.getAllImageFiles()) {
                    newTree.getChildren().add(new TreeItem<>(imageFile.getName()));
                }
                a.setRoot(newTree);
            } else if(toggleViewer[0] == 0) {
                toggleViewer[0] += 1;
                TreeItem<String> newTree = new TreeItem<>(file.getName() +" (Local View)");

                for(ImageFile imageFile: imageFileManager.getLocalImageFiles()) {
                    newTree.getChildren().add(new TreeItem<>(imageFile.getName()));
                }
                a.setRoot(newTree);
            } else if (toggleViewer[0] == 1) {
                toggleViewer[0] = -1;
                TreeItem<String> newTree = getNodesForDirectory(file, Arrays.toString(imageList));
                TreeItem<String> tempTree = new TreeItem<>(file.getName() + " (Tree View)");
                tempTree.getChildren().addAll(newTree.getChildren());
                newTree = tempTree;
                a.setRoot(newTree);

            }
        });

        //logger to display all the changes ever done to any image
        Button log = new Button("Log");
        log.setOnAction(e -> {
            // TODO: 19-11-2017 create a logger
        });

//        newGrid.setAlignment(Pos.TOP_LEFT);
        newGrid.getChildren().addAll(a, toggle, browseFiles);
        Scene scene = new Scene(newGrid, 1020, 720);
        window.setScene(scene);
        window.show();
    }


    // for making the tree
    private TreeItem<String> getNodesForDirectory(File directory, String StringThatContains) {
        TreeItem<String> root = new TreeItem<>(directory.getName());
        if(directory.listFiles() != null) {
            for (File f : directory.listFiles()) {
                if (f.isDirectory()) {
                    TreeItem<String> children = getNodesForDirectory(f, StringThatContains);
//                System.out.println(children.getChildren().size());
                    if (children.getChildren().size() != 0) {
                        root.getChildren().add(getNodesForDirectory(f, StringThatContains));
                    }
                } else {
                    if (StringThatContains.contains(f.getName())) {
                        root.getChildren().add(new TreeItem<>(f.getName()));
                    }
                }
            }
        }
        return root;
    }

}
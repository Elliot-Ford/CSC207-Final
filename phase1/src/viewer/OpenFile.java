package viewer;

import java.io.File;
import java.util.*;

import javafx.application.Application;
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
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import model.ImageFile;
import model.ImageFileManager;


public final class OpenFile{

    Stage window;

    private static void open(Stage stage) {
        Application.launch(Console.class);
    }


    public void openFile(File file,Stage stage) throws Exception {
        int[] toggleViewer = {-1};
        Console console = new Console();
        Map<String, ImageFile> fileMap = new HashMap<>();

        window = stage;
        window.setTitle("Lets do Tagging");

        GridPane newGrid = new GridPane();
        newGrid.setAlignment(Pos.TOP_LEFT);
        newGrid.setPadding(new Insets(10, 10, 10, 10));
        newGrid.setVgap(10);
        newGrid.setHgap(10);

        VBox vBox = new VBox();
        HBox buttonsBox = new HBox();
        buttonsBox.setSpacing(10);
        buttonsBox.setPadding(new Insets(10, 10, 10, 10));

        ImageFileManager imageFileManager = new ImageFileManager(file);

        TreeView<String> fileViewer = new TreeView<>();
        for(ImageFile imageFile: imageFileManager.getAllImageFiles()) {
            fileMap.put(imageFile.toString(), imageFile);
        }
        vBox.setAlignment(Pos.TOP_LEFT);
        vBox.getChildren().add(fileViewer);
        //GridPane.setConstraints(a,0,0);
        final TreeItem<String>[] tree = new TreeItem[]{getNodesForDirectory(file, Arrays.toString(imageFileManager.getAllImageFiles()))};
        final TreeItem<String>[] holderTree = new TreeItem[]{new TreeItem<>(file.getName() + " (Tree View)")};
        holderTree[0].getChildren().addAll(tree[0].getChildren());
        tree[0] = holderTree[0];



        fileViewer.setRoot(tree[0]);
        fileViewer.getSelectionModel().selectedItemProperty()
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
        browseFiles.setOnAction((final ActionEvent e) -> {
                console.start(stage);
        });

        //GridPane.setConstraints(browseFiles,1,5);

        // button that shows all the image under the the selected directory

        Button toggle = new Button("Toggle View");
        //GridPane.setConstraints(toggle,2,5);
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
                fileViewer.setRoot(newTree);
            } else if(toggleViewer[0] == 0) {
                toggleViewer[0] += 1;
                TreeItem<String> newTree = new TreeItem<>(file.getName() +" (Local View)");

                for(ImageFile imageFile: imageFileManager.getLocalImageFiles()) {
                    newTree.getChildren().add(new TreeItem<>(imageFile.getName()));
                }
                fileViewer.setRoot(newTree);
            } else if (toggleViewer[0] == 1) {
                toggleViewer[0] = -1;
                TreeItem<String> newTree = getNodesForDirectory(file, Arrays.toString(imageFileManager.getAllImageFiles()));
                TreeItem<String> tempTree = new TreeItem<>(file.getName() + " (Tree View)");
                tempTree.getChildren().addAll(newTree.getChildren());
                newTree = tempTree;
                fileViewer.setRoot(newTree);

            }
        });

//        //logger to display all the changes ever done to any image
//        Button log = new Button("Log");
//        log.setOnAction(e -> {
//            // TODO: 19-11-2017 create a logger
//        });

        buttonsBox.getChildren().addAll(toggle,browseFiles);
        newGrid.getChildren().addAll(vBox);
        vBox.getChildren().add(buttonsBox);
        Scene scene = new Scene(newGrid, 1020, 720);
        window.setScene(scene);
        window.sizeToScene();
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
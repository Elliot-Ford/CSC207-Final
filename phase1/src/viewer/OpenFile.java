package viewer;

import java.io.File;
import java.util.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import model.ImageFile;
import model.ImageFileManager;


public final class OpenFile{

    Stage window;

    private static void open(Stage stage) {
        Application.launch(Console.class);
    }



    public void openFile(File file,Stage stage) throws Exception {
        final int[] toggleViewer = {-1};
        final Console console = new Console();
        final Map<String, ImageFile>[] fileMap = new Map[]{new HashMap<>()};

        window = stage;
        window.setTitle("Lets do Tagging");

        GridPane newGrid = new GridPane();
        newGrid.setAlignment(Pos.CENTER);
        newGrid.setPadding(new Insets(10, 10, 10, 10));
        newGrid.setVgap(10);
        newGrid.setHgap(10);

        ImageFileManager imageFileManager = new ImageFileManager(file);
        ImageFile[] imageList = imageFileManager.getAllImageFiles();
        TreeView<String> a = new TreeView<>();
        for(ImageFile imageFile: imageFileManager.getAllImageFiles()) {
            fileMap[0].put(imageFile.toString(), imageFile);
        }
        GridPane.setConstraints(a,0,0);
        final TreeItem<String>[] tree = new TreeItem[]{getNodesForDirectory(file, Arrays.toString(imageFileManager.getAllImageFiles()))};
        final TreeItem<String>[] holderTree = new TreeItem[]{new TreeItem<>(file.getName() + " (Tree View)")};
        holderTree[0].getChildren().addAll(tree[0].getChildren());
        tree[0] = holderTree[0];

        editImage ei = new editImage();

        a.setRoot(tree[0]);
        a.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {
                    if (newValue != null) {
                        ei.editPic(newValue);
                    }
                });
        // button for browsing between directories
        Button browseFiles = new Button("Change Directory");
        browseFiles.setOnAction((final ActionEvent e) -> {
                console.start(stage);
        });

        GridPane.setConstraints(browseFiles,1,5);

        // button that shows all the image under the the selected directory

        Button toggle = new Button("Toggle View");
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


        newGrid.getChildren().addAll(a, toggle, browseFiles);
        Scene scene = new Scene(newGrid, 1020, 720);
        window.setScene(scene);
        window.show();
    }


    // for making the
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

    private void showImage(ImageFile[] list, String path){
        /**
        TreeItem<String> nodeItem = new TreeItem<>();
        for (ImageFile image : list){
            nodeItem.getChildren().addAll((Collection<? extends TreeItem<String>>) image);
         }
         */
        TreeItem<String> rootItem = new TreeItem<>("Root Folder");
        rootItem.setExpanded(true);


        window = new Stage();
        window.setTitle("All Image");
        GridPane newGrid = new GridPane();
        newGrid.setPadding(new Insets(10, 10, 10, 10));
        newGrid.setVgap(10);
        newGrid.setHgap(10);

        for (ImageFile IFile : list) {
            //Node nodeIcon = new ImageView(new Image(getClass().getResourceAsStream(IFile.getName())));
            TreeItem<String> nodeItem = new TreeItem<>(IFile.getName());
            rootItem.getChildren().add(nodeItem);
        }

        TreeView<String> tree = new TreeView<>(rootItem);
        StackPane root = new StackPane();
        root.getChildren().add(tree);

        newGrid.getChildren().addAll(root.getChildren());

        Scene scene = new Scene(newGrid, 1020, 720);
        window.setScene(scene);
        window.showAndWait();
    }


}
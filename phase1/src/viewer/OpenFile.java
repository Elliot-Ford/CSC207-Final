package viewer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import Controller.Main;
import model.ImageFile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public final class OpenFile{

    Stage window;



    public void openFile(File file,Stage stage) {
        window = stage;
        window.setTitle("Lets do Tagging");

        GridPane newGrid = new GridPane();
        newGrid.setAlignment(Pos.CENTER_LEFT);
        newGrid.setPadding(new Insets(10, 10, 10, 10));
        newGrid.setVgap(10);
        newGrid.setHgap(10);

        TreeView<String> a = new TreeView<>();
        GridPane.setConstraints(a,0,0);
        a.setRoot(getNodesForDirectory(file));
        a.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {
                    if (newValue != null)
                        System.out.println(newValue.getValue());
                });

        // button that shows all the image under the parent directory

//        newGrid.setAlignment(Pos.BASELINE_RIGHT);
        Button showAll = new Button("Show all Images");
        GridPane.setConstraints(showAll,5,5);

//        TableView<ImageFile> table = new TableView<>();
        System.out.println(file.getPath());

        Main controller = new Main();
        ImageFile[] imageList = controller.getImageFiles(controller.startProgram(file.getPath()));
        showAll.setOnAction( e -> {
            showImage(imageList, file.getPath());
        });

        newGrid.getChildren().addAll(a, showAll);
        Scene scene = new Scene(newGrid, 1020, 720);
        window.setScene(scene);
        window.show();
    }

    // for making the
    private TreeItem<String> getNodesForDirectory(File directory) {
        TreeItem<String> root = new TreeItem<>(directory.getName());
        for(File f : directory.listFiles()) {
            if(f.isDirectory()) {
                root.getChildren().add(getNodesForDirectory(f));
            } else {
                root.getChildren().add(new TreeItem<>(f.getName()));
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
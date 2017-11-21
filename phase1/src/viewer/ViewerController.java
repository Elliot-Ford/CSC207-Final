package viewer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.ImageFile;
import model.ImageFileManager;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ViewerController {

    public ListView<String> currentTags;
    public Button createTag;
    public ListView previousTags;
    public TextField tagToAdd;
    public ListView log;
    public Button changeDir;
    public Button move;
    public Button removeTag;
    public Button addTag;
    public Button deleteTag;
    public ImageView imageView;
    public GridPane gp;
    public TreeView<String> viewer;
    public GridPane gp2;
    public ListView allTags;
    public TextField tagToCreate;

    private ImageFileManager imageFileManager;
    private ImageFile selectedImageFile;
    private Map<String, ImageFile> imageFileMap;
    private boolean toggle;
    private boolean clicked;

    public ViewerController() {
        toggle = false;
        imageFileManager = new ImageFileManager("");
        imageFileMap = new HashMap<>();
        imageView = new ImageView();
    }

    @FXML
    private void initialize() {

    }

    @FXML
    void setup(Stage stage) {
        changeDirectory(stage);
        updateAllTags(clicked);
    }

    @FXML
    public void handleToggleViewerAction(ActionEvent event) {
        toggle = !toggle;
        updateTreeView();
    }

    @FXML
    public void handleChangeDir(ActionEvent actionEvent) {
        changeDirectory(gp.getScene().getWindow());
    }

    private void changeDirectory(Window window) {
        DirectoryChooser dc = new DirectoryChooser();
        File initialDirectory = new File(System.getProperty("user.home"));
        File newDirectory = null;

        if (imageFileManager.getRoot().exists()) {
            initialDirectory = imageFileManager.getRoot();
        }

        dc.setInitialDirectory(initialDirectory);

        do {
            newDirectory = dc.showDialog(window);
            if (newDirectory != null && newDirectory.exists()) {
                imageFileManager.changeDirectory(newDirectory);
                selectedImageFile = null;
                updateAllTags(clicked);
                updateImageView();
            }
        } while (newDirectory != null && !newDirectory.exists());
    }

    @FXML
    private void moveFile(Window window) {
        DirectoryChooser dc = new DirectoryChooser();
        File initialDirectory = new File(System.getProperty("user.home"));
        File newDirectory = null;

        if (imageFileManager.getRoot().exists()) {
            initialDirectory = imageFileManager.getRoot();
        }

        dc.setInitialDirectory(initialDirectory);

        do {
            newDirectory = dc.showDialog(window);
            if (newDirectory != null && newDirectory.exists()) {
                selectedImageFile.moveFile(newDirectory.getPath());
                selectedImageFile = null;
                updateAllTags(clicked);
                updateImageView();
            }
        } while (newDirectory != null && !newDirectory.exists());
    }

    // the Update methods block.

    @FXML
    private void updateAllTags(boolean clicked) {
        if (!clicked) {
          updateTreeView();
        }
        updateCurrentTagsView();
        updatePreviousTagsView();
        updateAllTagsView();
        updateLogView();
    }

    @FXML
    private void updateTreeView() {
        if (imageFileMap.size() > 0) {
            imageFileMap = new HashMap<>();
        }
        if (toggle) {
            TreeItem<String> root =
                    new TreeItem<>(imageFileManager.getRoot().getName() + " (Recursive View)");
            root.setExpanded(true);
            viewer.setRoot(root);
            for (ImageFile imageFile : imageFileManager.getAllImageFiles()) {
                viewer.getRoot().getChildren().add(new TreeItem<>(imageFile.getName()));
                imageFileMap.put(imageFile.getName(), imageFile);
            }
        } else {
            TreeItem<String> root =
                    new TreeItem<>(imageFileManager.getRoot().getName() + " (Local View)");
            root.setExpanded(true);
            viewer.setRoot(root);
            for (ImageFile imageFile : imageFileManager.getLocalImageFiles()) {
                viewer.getRoot().getChildren().add(new TreeItem<>(imageFile.getName()));
                imageFileMap.put(imageFile.getName(), imageFile);
            }
        }
    }


    @FXML
    private void updateCurrentTagsView() {
      ObservableList availableTagsList = FXCollections.observableArrayList();
        if (selectedImageFile != null) {
            availableTagsList.addAll(selectedImageFile.getTags());
        }
      currentTags.setItems(availableTagsList);
    }

    @FXML
    private void updatePreviousTagsView() {
      ObservableList previousTagsList = FXCollections.observableArrayList();
        if (selectedImageFile != null) {
            previousTagsList.addAll(selectedImageFile.getPreviousTags());
        }
      previousTags.setItems(previousTagsList);
    }

    @FXML
    private void updateAllTagsView() {
        ObservableList allTagsList = FXCollections.observableArrayList();
        for (ImageFile iFile : imageFileManager.getAllImageFiles()) {
            allTagsList.addAll(iFile.getTags());
        }

        allTags.setItems(allTagsList);
    }

    @FXML
    private void updateLogView() {
      ObservableList logList = FXCollections.observableArrayList();
        if (selectedImageFile != null) {
            logList.addAll(selectedImageFile.getLog());
        }
      log.setItems(logList);
    }

    @FXML
    private void updateImageView() {
        if (selectedImageFile != null) {
            imageView.setImage(selectedImageFile.getImage());
        }
    }

    @FXML
    private void updateUnassociatedTagsView() {

    }

    //  The handle methods block.

    @FXML
    public void handleViewerClick(MouseEvent mouseEvent) {
        ImageFile imageFile = imageFileMap.get(viewer.getSelectionModel().getSelectedItem().getValue());
        if (imageFile != null) {
            selectedImageFile = imageFile;

            clicked = true;
            updateAllTags(clicked);
            clicked = false;

            updateImageView();
        }
    }

    @FXML
    public void handleAddTag(ActionEvent actionEvent) {
        if (selectedImageFile != null &&
                !Arrays.asList(imageFileManager.getAllCurrentTags()).contains(tagToCreate.getText())) {
            selectedImageFile.addTag(tagToCreate.getText());
            tagToCreate.clear();
            updateAllTags(clicked);
        }
    }

    @FXML
    public void handleCreateTag(ActionEvent actionEvent) {
        if (selectedImageFile != null) {
            selectedImageFile.addTag(tagToCreate.getText());
            tagToCreate.clear();
            updateAllTags(clicked);
        }
    }

    @FXML
    public void handleMoveFile(ActionEvent actionEvent) {
        moveFile(gp.getScene().getWindow());
    }

    @FXML
    public void handleRemoveTag(ActionEvent actionEvent) {
        if (selectedImageFile != null) {
            selectedImageFile.removeTag(tagToCreate.getText());
            tagToCreate.clear();
            updateAllTags(clicked);
        }
    }

    @FXML
    public void handleDeleteTag(ActionEvent actionEvent) {
        if (imageFileManager != null) {
            imageFileManager.deleteTag(tagToCreate.getText());
            tagToCreate.clear();
            updateAllTags(clicked);
        }
    }
}
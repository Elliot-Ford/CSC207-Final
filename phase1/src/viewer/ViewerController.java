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

  public ViewerController() {
    toggle = false;
    imageFileManager = new ImageFileManager("");
    imageFileMap = new HashMap<>();
    imageView = new ImageView();
  }

  @FXML
  private void initialize() {

  }

  void setup(Stage stage) throws IOException {
    changeDirectory(stage);
    updateAllTags();
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
        updateTreeView();
      }
    } while (newDirectory != null && !newDirectory.exists());
  }

  private void updateAllTags() throws IOException {
    updateTreeView();
    updateCurrentTagsView();
    updatePreviousTagsView();
    updateAllTagsView();
    updateLogView();
  }

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

  private void updateCurrentTagsView() {
    if (selectedImageFile != null) {
      ObservableList availableTagsList = FXCollections.observableArrayList();
      availableTagsList.addAll(selectedImageFile.getTags());
      currentTags.setItems(availableTagsList);
    }
  }

  private void updatePreviousTagsView() throws IOException {
    if (selectedImageFile != null) {
      ObservableList previousTagsList = FXCollections.observableArrayList();
      previousTagsList.addAll(selectedImageFile.getPreviousTags());
      previousTags.setItems(previousTagsList);
    }
  }

  private void updateAllTagsView() {
    ObservableList allTagsList = FXCollections.observableArrayList();
    for (ImageFile iFile : imageFileManager.getAllImageFiles()) {
      allTagsList.addAll(iFile.getTags());
    }

    allTags.setItems(allTagsList);
  }

  private void updateLogView() throws IOException {
    if (selectedImageFile != null) {
      ObservableList logList = FXCollections.observableArrayList();
      logList.addAll(selectedImageFile.getLog());
      log.setItems(logList);
    }
  }

  private void updateImageView() {
    if (selectedImageFile != null) {
      imageView.setImage(selectedImageFile.getImage());
    }
  }

  private void updateUnassociatedTagsView() {

  }

  public void handleViewerClick(MouseEvent mouseEvent) {
    ImageFile imageFile = imageFileMap.get(viewer.getSelectionModel().getSelectedItem().getValue());
    if (imageFile != null) {
      imageView.setImage(imageFile.getImage());
      selectedImageFile = imageFile;

      updateCurrentTagsView();

      ObservableList allTagsList = FXCollections.observableArrayList();
      allTagsList.addAll(imageFileManager.getAllCurrentTags());

      allTags.setItems(allTagsList);
    }
  }

  public void handleAddTag(ActionEvent actionEvent) {
    if(selectedImageFile != null) {
      selectedImageFile.addTag(allTags.getSelectionModel().getSelectedItem().toString());
      updateTreeView();
    }
  }

  public void handleCreateTag(ActionEvent actionEvent) {
    if(selectedImageFile != null) {
      selectedImageFile.addTag(tagToCreate.getText());
      tagToCreate.clear();
      updateTreeView();
    }
  }
}

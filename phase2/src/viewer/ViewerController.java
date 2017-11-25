package viewer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.ImageFile;
import model.ImageFileManager;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ViewerController {

  /** Displays all the current Tags */
  public ListView<String> currentTags;

  /** Displays all the previous Tags */
  public ListView<String> previousTags;

  /** Displays all Tags */
  public ListView<String> allTags;

  /** Displays all the lines in log */
  public ListView<String> log;

  /** the Button that moves the selected image */
  public Button move;

  /** the Button that removes the selected Tag */
  public Button removeTag;

  /** the Button that adds the selected Tag */
  public Button addTag;

  /** the Button that deletes the selected Tag */
  public Button deleteTag;

  /** the main(root) GridPane */
  public GridPane gp;

  /** the image GridPane */
  public GridPane gp2;

  /** the image viewer */
  public ImageView imageView;

  /** Displays the String representations of all images */
  public ListView<ImageFile> viewer;
  /** Gets the String representation for a new Tag */
  public TextField tagToCreate;

  /** The name of the displayed image */
  public Label imageName;

  /** The imaged displayed as default when there's no image available. */
  public Image defaultImage;

  /** the Boolean that indicates which mode it is for the TreeView */
  private boolean toggle;

  /** the imageFileManager for this GUI */
  private ImageFileManager imageFileManager;

  /** the imageFile for this GUI */
  private ImageFile selectedImageFile;

  /** the observable list of currentTags */
  private ObservableList<String> currentTagsList;

  /** the observable list of allTags */
  private ObservableList<String> allTagsList;

  /** the observable list of viewer */
  private ObservableList<String> previousTagsList;

  /** the observable list of viewer */
  private ObservableList<ImageFile> viewerList;

  /** Construct a ViewerController. */
  public ViewerController() {
    toggle = false;
    imageFileManager = new ImageFileManager("");
    currentTagsList = FXCollections.observableArrayList();
    allTagsList = FXCollections.observableArrayList();
    previousTagsList = FXCollections.observableArrayList();
    viewerList = FXCollections.observableArrayList();
  }

  /**
   * Sets up the GUI before the directory is selected.
   *
   * @param stage the initial stage of the GUI
   */
  @FXML
  void setup(Stage stage) {
    changeDirectory(stage);
    currentTags.setItems(currentTagsList);
    allTags.setItems(allTagsList);
    previousTags.setItems(previousTagsList);
    viewer.setItems(viewerList);
    updateAll();
  }

  /**
   * Ask the viewer for a new directory to change directory.
   *
   * @param window this window
   */
  private void changeDirectory(Window window) {
    DirectoryChooser dc = new DirectoryChooser();
    File initialDirectory = new File(System.getProperty("user.home"));
    File newDirectory;

    if (imageFileManager.getRoot().exists()) {
      initialDirectory = imageFileManager.getRoot();
    }

    dc.setInitialDirectory(initialDirectory);

    do {
      newDirectory = dc.showDialog(window);
      if (newDirectory != null && newDirectory.exists()) {
        imageFileManager.changeDirectory(newDirectory);
        selectedImageFile = null;
        updateAll();
      }
    } while (newDirectory != null && !newDirectory.exists());
  }

  /**
   * Ask the viewer for a new directory to move file.
   *
   * @param window this window
   */
  @FXML
  private void moveFile(Window window) {
    DirectoryChooser dc = new DirectoryChooser();
    File initialDirectory = new File(System.getProperty("user.home"));
    File newDirectory;

    if (imageFileManager.getRoot().exists()) {
      initialDirectory = imageFileManager.getRoot();
    }

    dc.setInitialDirectory(initialDirectory);

    do {
      newDirectory = dc.showDialog(window);
      if (newDirectory != null && newDirectory.exists()) {
        selectedImageFile.moveFile(newDirectory.getPath());
        selectedImageFile = null;
        updateAll();
      }
    } while (newDirectory != null && !newDirectory.exists());
  }

  // the Update methods block.

  /** Update everything. */
  @FXML
  private void updateAll() {
    updateViewer();
    updateCurrentTagsView();
    updatePreviousTagsView();
    updateAllTagsView();
    updateLogView();
    updateImageView();
    updateImageName();
  }

  /** Update everything that's image related. */
  @FXML
  private void updateAllImageRelated() {
    updateCurrentTagsView();
    updatePreviousTagsView();
    updateAllTagsView();
    updateLogView();
    updateImageView();
    updateImageName();
  }

  /** Update the tree view. */
  @FXML
  private void updateViewer() {
    if (imageFileManager != null) {
      viewerList.clear();
      ImageFile[] imageFiles;
      if (toggle) {
        imageFiles = imageFileManager.getAllImageFiles();

      } else {
        imageFiles = imageFileManager.getLocalImageFiles();
      }
      viewerList.addAll(imageFiles);
    }

    //    if (imageFileMap.size() > 0) {
    //      imageFileMap = new HashMap<>();
    //    }
    //    if (toggle) {
    //      TreeItem<ImageFile> root =
    //          new TreeItem<>(imageFileManager.getRoot().getName() + " (Recursive View)");
    //      root.setExpanded(true);
    //      viewer.setRoot(root);
    //      for (ImageFile imageFile : imageFileManager.getAllImageFiles()) {
    //        viewer.getRoot().getChildren().add(new TreeItem<>(imageFile.getFullName()));
    //        imageFileMap.put(imageFile.getFullName(), imageFile);
    //      }
    //    } else {
    //      TreeItem<String> root =
    //          new TreeItem<>(imageFileManager.getRoot().getName() + " (Local View)");
    //      root.setExpanded(true);
    //      viewer.setRoot(root);
    //      for (ImageFile imageFile : imageFileManager.getLocalImageFiles()) {
    //        viewer.getRoot().getChildren().add(new TreeItem<>(imageFile.getFullName()));
    //        imageFileMap.put(imageFile.getFullName(), imageFile);
    //      }
    //    }
  }

  /** Update the current tags view. */
  @FXML
  private void updateCurrentTagsView() {
    currentTagsList.clear();
    if (selectedImageFile != null) {
      currentTagsList.addAll(selectedImageFile.getTags());
    }
  }

  /** Update the previous tags view. */
  @FXML
  private void updatePreviousTagsView() {
    previousTagsList.clear();
    if (selectedImageFile != null) {
      previousTagsList.addAll(selectedImageFile.getPreviousTags());
    }
  }

  /** Update the all tags view. */
  @FXML
  private void updateAllTagsView() {
    allTagsList.clear();
    allTagsList.addAll(imageFileManager.getAllCurrentTags());
  }

  /** Update the log view. */
  @FXML
  private void updateLogView() {
    ObservableList<String> logList = FXCollections.observableArrayList();
    if (selectedImageFile != null) {
      logList.addAll(selectedImageFile.getLog());
    }
    log.setItems(logList);
    log.scrollTo(logList.size() - 1);
  }

  /** Update the image view. */
  @FXML
  private void updateImageView() {
    if (selectedImageFile != null) {
      imageView.setImage(selectedImageFile.getImage());
      imageView.autosize();

    } else {
      imageView.setImage(defaultImage);
      imageView.autosize();
    }
  }

  /** Update the image name. */
  @FXML
  private void updateImageName() {
    if (selectedImageFile != null) {
      imageName.setText(selectedImageFile.getName());
    } else {
      imageName.setText("No Image");
    }
  }

  //  The handle methods block.

  /** Handles the viewer click action. */
  @FXML
  public void handleViewerClick() {
    ImageFile imageFile = viewer.getSelectionModel().getSelectedItem();
    if (imageFile != null) {
      selectedImageFile = imageFile;

      updateAllImageRelated();
    }
  }

  /** Handles the toggle view action. */
  @FXML
  public void handleToggleViewerAction() {
    toggle = !toggle;
    updateViewer();
  }

  /** Handles the change directory action. */
  @FXML
  public void handleChangeDir() {
    changeDirectory(gp.getScene().getWindow());
  }

  /** Handles the add Tag action. */
  @FXML
  public void handleAddTag() {
    if (selectedImageFile != null) {
      selectedImageFile.addTag(allTags.getSelectionModel().getSelectedItem());
      updateAll();
    }
  }

  /** Handles the restore Tag action. */
  @FXML
  public void handleRestoreTag() {
    // TODO doesn't work rn.
    if (selectedImageFile != null) {
      selectedImageFile.addTag(previousTags.getSelectionModel().getSelectedItem());
      updateAll();
    }
  }

  /** Handles the create Tag action. */
  @FXML
  public void handleCreateTag() {
    if (selectedImageFile != null) {
      selectedImageFile.addTag(tagToCreate.getText());
      tagToCreate.clear();
      updateAll();
    }
  }

  /** Handles the move file action. */
  @FXML
  public void handleMoveFile() {
    moveFile(gp.getScene().getWindow());
  }

  /** Handles the remove Tag action. */
  @FXML
  public void handleRemoveTag() {
    if (selectedImageFile != null) {
      selectedImageFile.removeTag(currentTags.getSelectionModel().getSelectedItem());
      tagToCreate.clear();
      updateAll();
    }
  }

  /** Handles the delete Tag action. */
  @FXML
  public void handleDeleteTag() {
    if (imageFileManager != null) {
      imageFileManager.deleteTag(allTags.getSelectionModel().getSelectedItem());
      updateAll();
    }
  }
}

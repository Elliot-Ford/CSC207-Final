package viewer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.ImageFile;
import model.ImageFileManager;

import java.io.File;

public class ViewerController {
  /** text to display when there's no image */
  private static final String DEFAULT_IMAGE_NAME = "No Image";

  /** Displays all the current Tags */
  public ListView<String> currentTags;

  /** Displays all the previous Tags */
  public ListView<String> previousTags;

  /** Displays all Tags */
  public ListView<String> directoryTags;

  /** Displays all the lines in log */
  public ListView<String> log;

  /** the main(root) GridPane */
  public BorderPane gp;

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

  /** the observable list of directoryTags */
  private ObservableList<String> directoryTagsList;

  /** the observable list of viewer */
  private ObservableList<String> previousTagsList;

  /** the observable list of viewer */
  private ObservableList<ImageFile> viewerList;

  /** the observable list of log */
  private ObservableList<String> logList;

  /** Construct a ViewerController. */
  public ViewerController() {
    toggle = false;
    imageFileManager = new ImageFileManager("");
    currentTagsList = FXCollections.observableArrayList();
    directoryTagsList = FXCollections.observableArrayList();
    previousTagsList = FXCollections.observableArrayList();
    viewerList = FXCollections.observableArrayList();
    logList = FXCollections.observableArrayList();
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
    directoryTags.setItems(directoryTagsList);
    previousTags.setItems(previousTagsList);
    viewer.setItems(viewerList);
    log.setItems(logList);
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

  // Start of all the Update methods.

  /** Update everything. */
  @FXML
  private void updateAll() {
    updateImageFileManagerViews();
    updateImageFileViews();
  }

  /** Updates all the ImageFileManager related views. */
  @FXML
  private void updateImageFileManagerViews() {
    viewerList.clear();
    directoryTagsList.clear();
    if (imageFileManager != null) {
      ImageFile[] imageFiles;
      if (toggle) {
        imageFiles = imageFileManager.getAllImageFiles();

      } else {
        imageFiles = imageFileManager.getLocalImageFiles();
      }
      viewerList.addAll(imageFiles);
      directoryTagsList.addAll(imageFileManager.getAllCurrentTags());
    }
  }

  /** Updates all the ImageFile related views */
  @FXML
  private void updateImageFileViews() {
    // Clear everything that is to be updated
    currentTagsList.clear();
    previousTagsList.clear();
    logList.clear();
    imageName.setText(DEFAULT_IMAGE_NAME);
    imageView.setImage(defaultImage);

    // If there's a selected ImageFile then update the views with the new ImageFile related
    // material.
    if (selectedImageFile != null) {
      // Update the list of tags currently assigned to the Image
      currentTagsList.addAll(selectedImageFile.getTags());
      // Update the list of all the previous tags that were assigned to the Image
      previousTagsList.addAll(selectedImageFile.getPreviousTags());
      // Update the log of all the changes to the Image
      logList.addAll(selectedImageFile.getLog());
      // Update the name of the Image.
      imageName.setText(selectedImageFile.getName());
      // Update the
      imageView.setImage(selectedImageFile.getImage());
    }
    // keep the log up to date
    log.scrollTo(logList.size() - 1);
    // Resize the imageView to match the given space.
    imageView.autosize();
  }

  // End of all the Update methods.
  // Start of all the handle methods.

  /** Handles the viewer click action. */
  @FXML
  public void handleViewerClick() {
    ImageFile imageFile = viewer.getSelectionModel().getSelectedItem();
    if (imageFile != null) {
      selectedImageFile = imageFile;

      updateAll();
    }
  }

  /** Handles the toggle view action. */
  @FXML
  public void handleToggleViewerAction() {
    toggle = !toggle;
    updateImageFileManagerViews();
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
      if (selectedImageFile.addTag(directoryTags.getSelectionModel().getSelectedItem())) {
        updateAll();
      }
    }
  }

  /** Handles the restore Tag action. */
  @FXML
  public void handleRestoreTag() {
    // TODO doesn't work rn. Backend problem
    if (selectedImageFile != null) {
      if (selectedImageFile.addTag(previousTags.getSelectionModel().getSelectedItem())) {
        updateAll();
      }
    }
  }

  /** Handles the create Tag action. */
  @FXML
  public void handleCreateTag() {
    if (selectedImageFile != null) {
      if (imageFileManager.addTag(tagToCreate.getText())) {
        tagToCreate.clear();
        updateAll();
      }
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
      if (selectedImageFile.removeTag(currentTags.getSelectionModel().getSelectedItem())) {
        updateAll();
      }
    }
  }

  /** Handles the delete Tag action. */
  @FXML
  public void handleDeleteTag() {
    if (imageFileManager != null) {
      if (imageFileManager.deleteTag(directoryTags.getSelectionModel().getSelectedItem())) {
      }
      updateAll();
    }
  }

  public void handleKeyPressed(KeyEvent keyEvent) {
    if(keyEvent.getCode() == KeyCode.ENTER) {
      handleCreateTag();
    }
  }


  // End of all the handle methods.
}

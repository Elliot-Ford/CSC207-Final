package viewer;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.ImageFile;
import model.ImageFileManager;

import java.io.File;

public class ViewerController {

  public ListView availableTags;
  public Button createTag;
  public ListView previousTags;
  public ListView currentTags;
  public TextField tagToAdd;
  public ListView log;
  public Button changeDir;
  public Button move;
  public Button removeTag;
  public Button addTag;
  public Button deleteTag;
  public ImageView image;
  public GridPane gp;
  public TreeView<String> viewer;

  private ImageFileManager imageFileManager;
  private boolean toggle;

  public ViewerController() {
    toggle = false;
  }

  @FXML
  private void initialize() {}

  void setup(Stage stage) {

    File newDirectory = changeDirectory(stage);
    if (newDirectory.exists()) {
      imageFileManager = new ImageFileManager(newDirectory);
      update();
    }
  }

  @FXML
  public void handleToggleViewerAction(ActionEvent event) {
    toggle = !toggle;
    update();
  }

  @FXML
  public void handleChangeDir(ActionEvent actionEvent) {
    File newDirectory = changeDirectory(gp.getScene().getWindow());
    if (newDirectory.exists()) {
      imageFileManager = new ImageFileManager(newDirectory);
      update();
    }
  }

  private File changeDirectory(Window window) {
    DirectoryChooser dc = new DirectoryChooser();
    File initialDirectory = new File(System.getProperty("user.home"));

    if (imageFileManager != null) {
      initialDirectory = imageFileManager.getRoot();
    }

    dc.setInitialDirectory(initialDirectory);

    File newDirectory = dc.showDialog(window);
    return newDirectory;
  }

  private void update() {
    if (toggle) {
      TreeItem<String> root = new TreeItem<>(imageFileManager.getRoot() + " (Recursive View)");
      root.setExpanded(true);
      viewer.setRoot(root);
      for (ImageFile imageFile : imageFileManager.getAllImageFiles()) {
        viewer.getRoot().getChildren().add(new TreeItem<>(imageFile.getName()));
      }
    } else {
      TreeItem<String> root = new TreeItem<>(imageFileManager.getRoot() + " (Local View)");
      root.setExpanded(true);
      viewer.setRoot(root);
      for (ImageFile imageFile : imageFileManager.getLocalImageFiles()) {
        viewer.getRoot().getChildren().add(new TreeItem<>(imageFile.getName()));
      }
    }
  }
}

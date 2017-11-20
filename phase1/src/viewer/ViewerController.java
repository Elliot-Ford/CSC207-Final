package viewer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.awt.event.ActionEvent;

public class ViewerController {

    @FXML
    public ListView avaliableTags;
    public Button createTag;
    public ImageView Image;
    public ListView previousTags;
    public ListView currentTags;
    public TextField tagToAdd;
    public ListView log;
    //public Button toggleViewer;
    public Button changeDir;
    public Button move;
    public Button removeTag;
    public Button addTag;
    public Button deleteTag;
    public ImageView image;


    @FXML public void handleToggleViewerAction(ActionEvent event) {
    System.out.println("dog" );
    }

}

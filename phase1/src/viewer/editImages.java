package viewer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.ImageFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class editImages {

    Stage window;
    ListView<String> listView;

    public void editPic(Stage stage, ImageFile iFile) throws IOException {
        window = stage;
        ImageView iv = new ImageView(String.valueOf(iFile));

        GridPane newGrid = new GridPane();
        newGrid.setAlignment(Pos.BOTTOM_RIGHT);
        newGrid.setPadding(new Insets(10, 10, 10, 10));
        newGrid.setVgap(10);
        newGrid.setHgap(10);


        //button for add tag
        Button add = new Button("Add Tag");
        TextField text1 = new TextField();
        text1.setPromptText("Enter tag");
        add.setOnAction(e->{
            iFile.addTag(text1.getText());
        });

        //button for remove tag
        Button remove = new Button("Remove Tag");
        TextField text2 = new TextField();
        text2.setPromptText("Enter Tag");
        remove.setOnAction(e->{
            iFile.removeTag(text2.getText());
        });



        //button for moving a file
        Button move = new Button("Move File");
        move.setOnAction(e->{
            DirectoryChooser dc = new DirectoryChooser();
            File file = dc.showDialog(window);
                iFile.moveFile(file.getPath());
        });

        //button get all tags
        Button getAllTags = new Button("Get all Tags");
        getAllTags.setOnAction(e->{
                List<String> temp1 = Arrays.asList(iFile.getPreviousTags());
                temp1.addAll(Arrays.asList(iFile.getTags()));
                String[] temp2 = temp1.toArray(new String[temp1.size()]);
                // temp 2 is array display on screen.
                ObservableList<String> options =
                        FXCollections.observableArrayList(temp2);
                listView = new ListView<>();
                listView.getItems().addAll(options);
                //pass this to open file
        });

        //button for local log
        Button getLog = new Button("Get Log");
        getLog.setOnAction(e->{
                String[] temp = iFile.getLog();
                // temp 1 is array display on screen.
                ObservableList<String> options =
                        FXCollections.observableArrayList(temp);
                listView = new ListView<>();
                listView.getItems().addAll(options);
                // pass this to openFile
        });
        

    }
}

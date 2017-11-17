package viewer;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main extends Application {

    Stage window;

    public Main() throws IOException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.show();

        //GridPane with 10pixel padding around edge
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        Label nameLabel = new Label(" Enter Directory");
        GridPane.setConstraints(nameLabel, 0, 0);
        //Name Input
        TextField nameInput = new TextField();
        nameInput.setPromptText("Directory");
        GridPane.setConstraints(nameInput, 0, 1);

//        final FileChooser fileChooser = new FileChooser();
        DirectoryChooser directoryChooser = new DirectoryChooser();

        final Button openButton = new Button("Browse");
        GridPane.setConstraints(openButton, 2, 1);

        openButton.setOnAction(
                e -> {
                    File file = directoryChooser.showDialog(primaryStage);
                    if (file != null) {
                        nameInput.setText(file.getAbsolutePath());
                    }
                });
        OpenFile op = new OpenFile();

        nameInput.setOnKeyPressed(ke -> {
            if (isDirectory(nameInput.getText())){
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    op.openFile(primaryStage);
            }
            }
        });
        //Enter
        Button enterButton = new Button("Enter");
        GridPane.setConstraints(enterButton, 1, 1);
        enterButton.setOnAction(event -> {
            if (isDirectory(nameInput.getText())) {
                op.openFile(primaryStage);
            }
        });

        //Add everything to grid
        grid.getChildren().addAll(nameLabel, nameInput, enterButton, openButton);

        Scene scene = new Scene(grid, 1020, 720);
        window.setScene(scene);
        window.show();
    }

    //check if the entered directory exists or not
    public boolean isDirectory(String userInput){
        File file = new File(userInput);
        if (file.exists())
            return true;
        else{
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Input not valid");
        errorAlert.setContentText("Entered directory dose not exists.");
        errorAlert.showAndWait();
        return false;
        }
    }

}



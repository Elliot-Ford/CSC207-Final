package viewer;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Image Tagger");

        //GridPane with 10pixel padding around edge
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        Label nameLabel = new Label(" Enter Directory");
        GridPane.setConstraints(nameLabel, 14, 10);
        //Name Input
        TextField nameInput = new TextField();
        nameInput.setPromptText("Directory");
        GridPane.setConstraints(nameInput, 15, 10);

        //Enter
        Button enterButton = new Button("Enter");
        GridPane.setConstraints(enterButton, 15, 11);

        //Add everything to grid
        grid.getChildren().addAll(nameLabel, nameInput, enterButton);

        Scene scene = new Scene(grid, 1020, 720);
        window.setScene(scene);
        window.show();
    }


}

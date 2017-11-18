package viewer;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public final class OpenFile{
    Stage window;

    public void openFile(Stage stage) {
        window = stage;
        window.setTitle("Lets do Tagging");

        GridPane newGrid = new GridPane();
        newGrid.setPadding(new Insets(10, 10, 10, 10));
        newGrid.setVgap(10);
        newGrid.setHgap(10);

        newGrid.setAlignment(Pos.TOP_LEFT);

        Button back = new Button("<");
        GridPane.setConstraints(back,0,0);

        Scene scene2 = new Scene(newGrid, 1020, 720);

        newGrid.getChildren().addAll(back);
        window.setScene(scene2);
        window.show();






    }
}
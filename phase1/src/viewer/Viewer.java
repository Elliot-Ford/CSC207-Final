package viewer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Viewer extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("viewer.fxml"));
        Parent root = (Parent) loader.load();
        ViewerController controller= (ViewerController)loader.getController();
        controller.setup(stage);

        Scene scene = new Scene(root, 900, 600);

        stage.setTitle("Tagger");
        stage.setScene(scene);
        stage.show();
    }
}

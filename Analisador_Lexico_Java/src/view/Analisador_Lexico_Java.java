package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author steli
 */
public class Analisador_Lexico_Java extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);

        controller controller = loader.getController();
        controller.setStage(stage);

        stage.setResizable(false);
        stage.setMaximized(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

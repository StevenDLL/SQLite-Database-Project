package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage mainStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/loginTheme.fxml"));
        Scene scene = new Scene(root);

        mainStage.setTitle("SQLite Database Project By Steven Acosta");
        mainStage.initStyle(StageStyle.UTILITY);
        mainStage.setResizable(false);
        mainStage.setScene(scene);
        mainStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}

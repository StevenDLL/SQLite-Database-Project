package app;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.SceneManager;

/*Project Description:
Write a Java program that allows the storage and management of Student objects using a sqlite database.
A student will have a unique ID, first name, last name, a unique username, password, and address.
The address has a street number, street name, city, state and zip code.

The program allows a user to insert and update students and addresses.
It also allows a user to search and remove students by ID, by username, and by last name.
If you are ambitious, try to make your program to be able to search students by address as well.
Note that multiple students could potentially have the same address and last name.
Try to use two tables: one for students, and the other one for addresses.
Design JavaFX gui components using Scene Builder.
 */

public class Main extends Application {

    public static String currentUser = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage mainStage) throws Exception {
        mainStage.initStyle(StageStyle.UTILITY);
        mainStage.setResizable(false);

        SceneManager sceneManager = new SceneManager(mainStage);
        sceneManager.changeScene("Login");

    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}

package utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class SceneManager {

    private final Stage mainStage;
    private static SceneManager mainManager = null;
    private final HashMap<String, Scene> sceneHashMap = new HashMap<>(6);

    public SceneManager(Stage mStage) {
        this.mainStage = mStage;
        generateScenes();
    }

    public static SceneManager getInstance(Stage mStage) {
        if (mainManager == null) {
            mainManager = new SceneManager(mStage);
        }
        return mainManager;
    }

    private void generateScenes() {
        try {

            //Login Scene
            Parent loginParent = FXMLLoader.load(getClass().getResource("/view/loginTheme.fxml"));
            Scene loginScene = new Scene(loginParent);

            //MainTheme Scene
            Parent mainParent = FXMLLoader.load(getClass().getResource("/view/mainTheme.fxml"));
            Scene mainScene = new Scene(mainParent);

//            //AddStudentTheme Scene
//            Parent addParent = FXMLLoader.load(getClass().getResource("/view/addStudentTheme.fxml"));
//            Scene addScene = new Scene(addParent);
//
//            //EditStudentTheme Scene
//            Parent editParent = FXMLLoader.load(getClass().getResource("/view/editStudentTheme.fxml"));
//            Scene editScene = new Scene(editParent);

            this.sceneHashMap.put("Login", loginScene);
            this.sceneHashMap.put("MainTheme", mainScene);
//            this.sceneHashMap.put("AddTheme", addScene);
//            this.sceneHashMap.put("EditTheme", editScene);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void changeScene(String sceneName) {

        switch (sceneName) {
            case "Login" -> {
                this.mainStage.hide();
                this.mainStage.setTitle("SQLite Database Project - Login - By Steven Acosta");
                this.mainStage.setScene(sceneHashMap.get(sceneName));
                this.mainStage.show();
            }

            case "MainTheme" -> {
                this.mainStage.hide();
                this.mainStage.setTitle("SQLite Database Project By Steven Acosta");
                this.mainStage.setScene(sceneHashMap.get(sceneName));
                this.mainStage.show();
            }
//
//            case "AddTheme" -> {
//                this.mainStage.hide();
//                this.mainStage.setTitle("Add NEW Student");
//                this.mainStage.setScene(sceneHashMap.get(sceneName));
//                this.mainStage.show();
//            }
//
//            case "EditTheme" -> {
//                this.mainStage.hide();
//                this.mainStage.setTitle("Edit SELECTED Student");
//                this.mainStage.setScene(sceneHashMap.get(sceneName));
//                this.mainStage.show();
//            }
            default -> DisplayAlert.displayErrorAlert("Unable to switch scene!");
        }
    }

}

package controller;

import app.Main;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.LoginHandler;
import utilities.DisplayAlert;
import utilities.SceneManager;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public ImageView loginImage;
    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public Button loginButton;
    public Button signUpButton;
    public Button exitButton;
    public Label dbStatusLabel;

    public Label dbStatus;
    public LoginHandler loginHandler = new LoginHandler();


    //ONLINE COLOR:     #57cc59
    //OFFLINE COLOR:    #b51d1d

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (loginHandler.checkConnection()) {
            dbStatus.setText("ONLINE");
            dbStatus.setTextFill(Color.web("#57cc59"));
        } else {
            dbStatus.setText("OFFLINE");
            dbStatus.setTextFill(Color.web("#b51d1d"));
        }
    }

    public void onLoginClicked(MouseEvent mouseEvent) {
        if (loginHandler.checkLogin(usernameTextField.getText().strip(), passwordTextField.getText().strip())) {
            System.out.println("logged in");
            Main.currentUser = usernameTextField.getText().strip();
            Stage mainStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            SceneManager.getInstance(mainStage).changeScene("MainTheme");
        } else {
            DisplayAlert.displayErrorAlert("Check Username & Password. This user might not exist.");
        }
    }

    public void onSignUpClicked(MouseEvent mouseEvent) {
    }

    public void onExitClicked(MouseEvent mouseEvent) {
        System.exit(0);
    }

}

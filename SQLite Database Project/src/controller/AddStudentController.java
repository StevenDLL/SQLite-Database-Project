package controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddStudentController {
    public TextField firstNameTextField;
    public TextField lastNameTextField;
    public TextField userNameTextField;
    public TextField passwordTextField;
    public TextField streetTextField;
    public TextField cityTextField;
    public TextField stateTextField;
    public TextField zipCodeTextField;
    public TextField countryTextField;
    public Button cancelAddButton;

    public void addButtonClicked(MouseEvent mouseEvent) {
        if (!firstNameTextField.getText().isEmpty() && !lastNameTextField.getText().isEmpty() && !userNameTextField.getText().isEmpty()
                && !passwordTextField.getText().isEmpty() && !streetTextField.getText().isEmpty() && !cityTextField.getText().isEmpty() && !stateTextField.getText().isEmpty()
                && !zipCodeTextField.getText().isEmpty() && !countryTextField.getText().isEmpty()) {

            if (utilities.dbInteractionUtils.addStudentSQL(this.firstNameTextField.getText(), this.lastNameTextField.getText(), this.userNameTextField.getText(),
                    this.passwordTextField.getText(), this.streetTextField.getText(), this.cityTextField.getText(), this.stateTextField.getText(),
                    this.zipCodeTextField.getText(), this.countryTextField.getText())) {
                utilities.DisplayAlert.displayInformationAlert("NEW Student Added.");
                Stage currentStage = (Stage) cancelAddButton.getScene().getWindow();
                currentStage.close();
            } else {
                utilities.DisplayAlert.displayErrorAlert("Unable to add NEW Student. Username is mostlikely already taken.");
            }

        } else {
            utilities.DisplayAlert.displayErrorAlert("CHECK ALL TEXT-FIELDS & MAKE SURE THAT THEY ARE FILLED");
        }

    }

    public void cancelButtonClicked(MouseEvent mouseEvent) {
        Stage currentStage = (Stage) cancelAddButton.getScene().getWindow();
        currentStage.close();
    }
}

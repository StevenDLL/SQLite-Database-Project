package controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Student;

public class EditStudentController {
    public TextField firstNameTextField;
    public TextField lastNameTextField;
    public TextField userNameTextField;
    public TextField streetTextField;
    public TextField cityTextField;
    public TextField stateTextField;
    public TextField zipCodeTextField;
    public TextField countryTextField;
    public TextField passwordTextField;
    public TextField idTextField;
    public Button cancelEditButton;
    public Button updateAndCloseButton;

    public Student currentStudent = null;

    public void updateButtonClicked(MouseEvent mouseEvent) {

        currentStudent.setID(currentStudent.getID());
        currentStudent.setFirstName(this.firstNameTextField.getText());
        currentStudent.setLastName(this.lastNameTextField.getText());
        currentStudent.setUserName(this.userNameTextField.getText());
        currentStudent.setPassword(this.passwordTextField.getText());
        currentStudent.setAddress(formAddress(this.streetTextField.getText(), this.cityTextField.getText(), this.stateTextField.getText(), this.zipCodeTextField.getText(), this.countryTextField.getText()));
        currentStudent.setAddressID(currentStudent.getAddressID());

        utilities.dbInteractionUtils.doSQLUpdate(currentStudent);
        Stage currentStage = (Stage) cancelEditButton.getScene().getWindow();
        currentStage.close();
    }

    public void cancelButtonClicked(MouseEvent mouseEvent) {
        Stage currentStage = (Stage) cancelEditButton.getScene().getWindow();
        currentStage.close();
    }

    private String formAddress(String street, String city, String state, String zipcode, String country) {
        return street.replaceAll("\\s", "") + " " + city.replaceAll("\\s", "") + " " + state.replaceAll("\\s", "") + " " + zipcode.replaceAll("\\s", "") + " " + country.replaceAll("\\s", "");
    }

    public void setTextFields(String id, String fName, String lName, String uName, String pWord, String street, String city, String state, String zip, String country) {
        this.idTextField.setText(id);
        this.firstNameTextField.setText(fName);
        this.lastNameTextField.setText(lName);
        this.userNameTextField.setText(uName);
        this.passwordTextField.setText(pWord);
        this.streetTextField.setText(street);
        this.cityTextField.setText(city);
        this.stateTextField.setText(state);
        this.zipCodeTextField.setText(zip);
        this.countryTextField.setText(country);
    }

    public void setCurrentStudent(Student student) {
        currentStudent = student;
    }
}

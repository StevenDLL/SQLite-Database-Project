package controller;

import app.Main;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Student;
import utilities.SceneManager;
import utilities.dbInteractionUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public Label currentUserLabel;
    public TabPane mainTabPane;
    public Tab dataViewTab;
    public TextField idTextField;
    public Label idLabel;
    public Label firstNameLabel;
    public TextField firstNameTextField;
    public Label lastNameLabel;
    public TextField lastNameTextField;
    public Label userNameLabel;
    public TextField userNameTextField;
    public Button searchButton;
    public Label streetLabel;
    public TextField streetTextField;
    public Label cityLabel;
    public TextField cityTextField;
    public Label stateLabel;
    public TextField stateTextField;
    public Label zipCodeLabel;
    public TextField zipCodeTextField;
    public Label countryLabel;
    public TextField countryTextField;
    public TableView<Student> dataTableView;
    public TableColumn<Student, Integer> idColumn;
    public TableColumn<Student, String> firstNameColumn;
    public TableColumn<Student, String> lastNameColumn;
    public TableColumn<Student, String> userNameColumn;
    public TableColumn<Student, String> passwordColumn;
    public TableColumn<Student, Integer> addressIDColumn;
    public TableColumn<Student, String> addressColumn;
    public Tab settingsTab;
    public ImageView folderImageView;

// TODO: 2/18/2022 MOVE ALL SQL RELATED METHODS TO THEIR OWN UTILS CLASS

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUserLabel.setText(Main.currentUser);
        doTableViewSetup();
        dataTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void doSearch(MouseEvent mouseEvent) {
        if (!idTextField.getText().isEmpty() && !firstNameTextField.getText().isEmpty() && !lastNameTextField.getText().isEmpty() && !userNameTextField.getText().isEmpty()) {
            dataTableView.setItems(doStudentSearch(Integer.parseInt(idTextField.getText()), firstNameTextField.getText(), lastNameTextField.getText(), userNameTextField.getText()));
        } else if (!idTextField.getText().isEmpty() && firstNameTextField.getText().isEmpty() && lastNameTextField.getText().isEmpty() && userNameTextField.getText().isEmpty()) {
            dataTableView.setItems(utilities.dbInteractionUtils.doStudentIDSQLSearch(Integer.parseInt(idTextField.getText())));
        }
    }

    private void doTableViewSetup() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        addressIDColumn.setCellValueFactory(new PropertyValueFactory<>("addressID"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private static ObservableList<Student> doStudentSearch(int ID, String firstName, String lastName, String userName) {
        return dbInteractionUtils.doStudentSQLSearch(ID, firstName, lastName, userName);
    }

    public void addStudent(MouseEvent mouseEvent) {
        Stage mainStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        doAddSceneSetup(mainStage); //Not saving controller since it's not needed
    }

    private AddStudentController doAddSceneSetup(Stage mainStage) {
        try {
            Stage editStage = new Stage();
            editStage.setResizable(false);
            editStage.initStyle(StageStyle.UTILITY);
            editStage.initModality(Modality.APPLICATION_MODAL);
            editStage.initOwner(mainStage);
            editStage.setTitle("Add NEW Student");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addStudentTheme.fxml"));
            Parent addParent = loader.load();
            Scene addScene = new Scene(addParent);
            editStage.setScene(addScene);
            editStage.show();

            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void doShowAll(MouseEvent mouseEvent) {
        dataTableView.setItems(dbInteractionUtils.doShowAllSQLQuery());
    }

    public void editSelected(MouseEvent mouseEvent) {
        int selected = dataTableView.getSelectionModel().getSelectedIndex();
        System.out.println(selected);
        if (selected != -1) {
            Stage mainStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            EditStudentController tempController = doEditSceneSetup(mainStage);
            Student student = dataTableView.getItems().get(selected);
            String[] splitAddress = student.getSplitAddress();

            if (tempController != null) {
                tempController.setCurrentStudent(student);
                tempController.setTextFields(String.valueOf(student.getID()), student.getFirstName(), student.getLastName(), student.getUserName(), student.getPassword(), splitAddress[0], splitAddress[1], splitAddress[2], splitAddress[3], splitAddress[4]);
            }
        }
    }

    private EditStudentController doEditSceneSetup(Stage mainStage) {
        try {
            Stage editStage = new Stage();
            editStage.setResizable(false);
            editStage.initStyle(StageStyle.UTILITY);
            editStage.initModality(Modality.APPLICATION_MODAL);
            editStage.initOwner(mainStage);
            editStage.setTitle("Edit SELECTED Student");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editStudentTheme.fxml"));
            Parent editParent = loader.load();
            Scene editScene = new Scene(editParent);
            editStage.setScene(editScene);
            editStage.show();

            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteSelected(MouseEvent mouseEvent) {
        int selected = dataTableView.getSelectionModel().getSelectedIndex();
        if (selected != -1) {
            dbInteractionUtils.doSQLDrop(dataTableView.getSelectionModel().getSelectedItem().getID());
            dataTableView.getItems().remove(selected);
        }
    }

}

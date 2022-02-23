package utilities;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.awt.event.ActionEvent;

public class DisplayAlert {

    public static void displayErrorAlert(String errorText) {
        Alert alertToShow = new Alert(Alert.AlertType.ERROR);
        alertToShow.setResizable(false);
        alertToShow.setTitle("Error Alert");
        alertToShow.setHeaderText(errorText);
        alertToShow.showAndWait();
    }

    public static void displayInformationAlert(String infoText) {
        Alert alertToShow = new Alert(Alert.AlertType.INFORMATION);
        alertToShow.setResizable(false);
        alertToShow.setTitle("Information Alert");
        alertToShow.setHeaderText(infoText);
        alertToShow.showAndWait();
    }

}

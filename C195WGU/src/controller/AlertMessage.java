package controller;

import javafx.scene.control.TextField;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;

/**
 * Alert Message class
 * @author isabelle Matthews
 */

/**
 *  This class is for alert message.
 */
public class AlertMessage {

    /**
     * This will save all errors alerts.
     * @param code code
     * @param field field for error
     */
    public static void error(int code, TextField field) {
        fieldError(field);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("ERROR ");
        switch (code) {
            case 1: {
                alert.setContentText("Postal code is required");
                break;
            }
            case 2: {
                alert.setContentText("Phone number is required");
                break;
            }
            case 3: {
                alert.setContentText("Address is required");
                break;
            }
            case 4: {
                alert.setContentText("Customer Name is required");
                break;
            }
            case 5: {
                alert.setContentText("Title is required.");
                break;
            }
            case 6: {
                alert.setContentText("Description is required.");
                break;
            }
            case 7: {
                alert.setContentText("Type is required.");
                break;
            }
            case 8: {
                alert.setContentText("Location is required");
                break;
            }
            case 9: {
                alert.setContentText("Start date/time is required");
                break;
            }
            case 10: {
                alert.setContentText("End date/time is required");
                break;
            }
            default: {
                alert.setContentText("UnknownError.");
                break;
            }
        }
        alert.showAndWait();
    }


     /**
      *  color of border
      */
     private static void fieldError(TextField field) {
        if (field != null) {
            field.setStyle("-fx-border-color: black");
        }
    }
}
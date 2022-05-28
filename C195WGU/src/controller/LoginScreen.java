package controller;

/**
 * Login Screen Controller
 * @author Isabelle Matthews
 */

import Model.Appointment;
import database.AppointmentData;
import database.JDBC;
import database.UserData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
interface loginActivity {
    public String login();
}


/**
 * This class is for log in screen
 */
public class LoginScreen implements Initializable {
    public TextField userText;
    public PasswordField passwordText;
    public Button logInBt;
    public Label login;
    public Label timeZone;
    public Label userID;
    public Label Password;
    ResourceBundle resourceBundle;
    boolean validLogin = false;

    /**
     * Lambda expression line 54-55: Log in activity returns its own text file.
     * That will help with code instead to repeat the txt or its code when I need it anywhere else for log in.
     */
    loginActivity loginActivity = ()-> {
        return "login_activity.txt";
    };

    @Override
    /**
     * Login screen initialize
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResourceBundle myBundle = ResourceBundle.getBundle("language/language", Locale.getDefault());

        userID.setText(myBundle.getString("username"));
        Password.setText(myBundle.getString("password"));
        logInBt.setText(myBundle.getString("login"));
        login.setText(myBundle.getString("login"));

        ZoneId.systemDefault().toString();
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        System.out.println("timezone update" + localZoneId);
        timeZone.setText(ZoneId.systemDefault().toString());
    }


    /**
     * THis method is used to log in button and go to second screen after success log in.
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void loginButton(ActionEvent actionEvent) throws IOException, SQLException {
        String username = userText.getText();
        String password = passwordText.getText();
        boolean valid = UserData.login(userText.getText(), passwordText.getText());
        if (valid) {

            upcomingAppt();

            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            Scene scene = new Scene(root, 400, 400);
            stage.setScene(scene);
            stage.show();
            validLogin = true;
        } else {
            ResourceBundle myBundle = ResourceBundle.getBundle("language/language", Locale.getDefault());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(myBundle.getString("invalid"));
            alert.showAndWait();
            validLogin = false;
        }
        loginActivity();
    }

     /**
      * TIME ZONE
     * @param actionEvent
     */
    public void timeZone(ActionEvent actionEvent) {
        ZoneId.systemDefault().toString();
    }

    /**
     * This method is keep records of every log in attempt, failure or success
     * Lambda experession from line 54-55, It puts in printerwriter, so it will create new file if needed and print every success or failure log ins.
     * @throws IOException
     */
    public void loginActivity() throws IOException {
        LocalDate dateTime = LocalDateTime.now().toLocalDate();
        Timestamp timeStamp = Timestamp.valueOf(LocalDateTime.now());
        PrintWriter pw = new PrintWriter(new FileOutputStream(loginActivity.login(), true));
        pw.print("Success log in ");
        pw.print("Date: " + dateTime + " ");
        pw.print("Time: " + timeStamp + " ");
        if (validLogin) {
            pw.print("success \n");
        } else {
            pw.print("not success \n");
        }
        pw.close();
    }

    /**
     * This method check upcoming appointments within 15 minutes and alert at main screen after log in.
     */
    public void upcomingAppt() {
        LocalDateTime LDT = LocalDateTime.now();
        LocalDateTime LDT15 = LocalDateTime.now().plusMinutes(15);
        boolean found = false;
        Appointment appt = null;

        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        try {
           ObservableList<Appointment> appointments = AppointmentData.getAllAppointment();
            if (appointments != null) {
                for (Appointment appointment : appointments) {
                    if (appointment.getStart().isAfter(LDT) && appointment.getStart().isBefore(LDT15)) {
                        found = true;

                        appt = appointment;
                        break;
                    }
                    appointmentList.add(appointment);
                    }
                }
            if (found) {
                ResourceBundle myBundle = ResourceBundle.getBundle("language/language", Locale.getDefault());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(myBundle.getString("appointmentalert"));
                alert.setHeaderText(myBundle.getString("appointmentalert"));
                alert.setContentText(myBundle.getString("15mins") + ("\n") + (myBundle.getString("appointmentID") + appt.getAppointmentID() + ("\n") + (myBundle.getString("appointmentDate") + appt.getStart().toLocalDate() + ("\n") + (myBundle.getString("appointmentTime")) + appt.getStart().toLocalTime())));
                alert.showAndWait();
            } else {
                   ResourceBundle myBundle = ResourceBundle.getBundle("language/language", Locale.getDefault());
                   Alert alert = new Alert(Alert.AlertType.INFORMATION);
                   alert.setTitle(myBundle.getString("no"));
                   alert.setHeaderText(myBundle.getString("no"));
                   alert.setContentText(myBundle.getString("15min"));
                   alert.showAndWait();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        }
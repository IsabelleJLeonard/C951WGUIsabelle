package controller;

/**
 * Main Screen Controller- after log in
 * @author Isabelle Matthews
 */

import Model.Appointment;
import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


/**
 * This class is for main screen after log in.
 */
public class MainScreen implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * This method is for Log out button to go back to Log in Screen
     * @param actionEvent
     * @throws IOException
     */
    public void logOutButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 400);
        stage.setTitle("Login Screen");
        stage.setScene(scene);
    }

    /**
     * This method is for Customer button to go to Customer Table
     * @param actionEvent
     * @throws IOException
     */
    public void customerListButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/SecondScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 500);
        stage.setTitle("Customer List");
        stage.setScene(scene);
    }

    /**
     * This method is for Appointment button to go to Appointment Table
     * @param actionEvent
     * @throws IOException
     */
    public void appointmentListButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Scheduling.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 500);
        stage.setTitle("Appointment");
        stage.setScene(scene);

    }

    /**
     * This method is for Report button to go to Report Table
     * @param actionEvent
     * @throws IOException
     */
    public void reportListButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Records.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 400);
        stage.setTitle("Report");
        stage.setScene(scene);
    }
}

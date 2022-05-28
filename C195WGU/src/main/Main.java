package main;

/**
 * package main
 * @author Isabelle Matthews
 *
 */

import Model.*;
import controller.AppointmentAdd;
import controller.CustomerList;
import database.AppointmentData;
import database.ContactData;
import database.CountryData;
import database.CustomerData;
import database.UserData;
import database.DivisionData;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import database.JDBC;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.Locale;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        stage.setTitle("login");
        stage.setScene(new Scene(root, 400, 400));
        stage.show();
    } catch (IllegalAccessError e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.showAndWait();
        }

        }

    public static void main(String[] args) throws SQLException {

        /* test to make sure time covert to EST
        AppointmentAdd.testLDTParse("2022-05-23 10:58:00"); */

        //CONNECT TO DATABASE
        JDBC.openConnection();


        //TEST JDBC TO UPDATE/DELETE data.

      /*  int rowsAffected = CustomerData.deleteCustomer(5);

        if(rowsAffected > 0){
            System.out.println("Updatesuccessful");
        }
        else {
            System.out.println("Delete Failed");
        } */


//TEST DISPLAY FOR LOAD FROM THE DATABASE
   /**   ObservableList<Appointment> list = AppointmentData.getAllAppointment();
          for (Appointment a: list) {
          System.out.println(a.getLocation()); */

        // test for language
       // Locale.setDefault(new Locale("fr"));


        //LAUNCH
           launch(args);


//CLOSE CONNECTION WITH DATABASE
           JDBC.closeConnection();
    }
}

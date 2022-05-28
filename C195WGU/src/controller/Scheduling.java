package controller;

/**
 * Appointment(Scheduling) table Controller
 * @author Isabelle Matthews
 */

import Model.Appointment;
import Model.Country;
import Model.Customer;
import Model.*;
import database.AppointmentData;
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
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is for Appointment Table.
 */
public class Scheduling implements Initializable {
    public TableView appointmentTable;
    public TableColumn appointment_ID;
    public TableColumn Title;
    public TableColumn Description;
    public TableColumn Location;
    public TableColumn Contact;
    public TableColumn Type;
    public TableColumn Start;
    public TableColumn End;
    public TableColumn customer_ID;
    public TableColumn user_ID;
    public ToggleGroup appointment;
    public RadioButton month;
    public RadioButton week;
    public RadioButton all;
    public ToggleGroup toggle;

    ObservableList<Customer> customerlist = FXCollections.observableArrayList();
    ObservableList<Appointment> appointmentlist = FXCollections.observableArrayList();
    ObservableList<Country> countrylist = FXCollections.observableArrayList();
    ObservableList<Contact> contactlist = FXCollections.observableArrayList();
    ObservableList<user> userlist = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        all.setToggleGroup(toggle);
        month.setToggleGroup(toggle);
        week.setToggleGroup(toggle);

    try {
            appointment_ID.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
            Title.setCellValueFactory(new PropertyValueFactory<>("Title"));
            Description.setCellValueFactory(new PropertyValueFactory<>("Description"));
            Location.setCellValueFactory(new PropertyValueFactory<>("Location"));
            Contact.setCellValueFactory(new PropertyValueFactory<>("ContactID"));
            Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
            Start.setCellValueFactory(new PropertyValueFactory<>("Start"));
            End.setCellValueFactory(new PropertyValueFactory<>("End"));
            customer_ID.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
            user_ID.setCellValueFactory(new PropertyValueFactory<>("UserID"));
            appointmentTable.setItems(AppointmentData.getAllAppointment());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method go to add appointment form
     * @param actionEvent
     * @throws IOException
     */
    public void addAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentAdd.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
    }

    /**
     * This method go to UPDATE APPOINTMENT form
     * @param actionEvent
     * @throws IOException
     */
    public void updateAppointment(ActionEvent actionEvent) throws IOException {

        if (appointmentTable.getSelectionModel().getSelectedItem() != null) {
            try {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/AppointmentUpdate.fxml"));
                loader.load();
                AppointmentUpdate controller = loader.getController();
                controller.pullAppointment((Appointment) appointmentTable.getSelectionModel().getSelectedItem());
                Scene scene = new Scene(loader.getRoot(), 700, 500);
                stage.setTitle("update Appointment");
                stage.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("ERROR");
                alert.showAndWait();
            }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Alert");
                alert.setContentText("You must select customer to update");
                alert.showAndWait();
        }
    }

    /**
     * This method is to CANCEL to go back to main
     * @param actionEvent
     * @throws IOException
     */
    public void backButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 400);
        stage.setTitle("Main");
        stage.setScene(scene);
    }


    /**
     * Radio button for week selection
     * @param actionEvent
     */
    public void week(ActionEvent actionEvent){
    }

    /**
     * Radio button for month selection
     * @param actionEvent
     */
    public void month(ActionEvent actionEvent){
    }

    /**
     * Radio button for all appointment selection
     * @param actionEvent
     */
    public void all(ActionEvent actionEvent){
    }


    /**
     * This method do DELETE APPOINTMENT FROM TABLE
     * @param actionEvent
     * @throws SQLException
     */
    public void removeButton(ActionEvent actionEvent) throws SQLException {

            Appointment appointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
            if (appointment != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this item?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    int rowsAffected = AppointmentData.deleteAppointment(appointment);
                    if (rowsAffected > 0) {
                        appointmentTable.setItems(AppointmentData.getAllAppointment());
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("You must select an appointment to remove");
                alert.showAndWait();
            }
    }

    /**
     * This method have Toggle for radio buttons to combine.
     * @param actionEvent
     * @throws SQLException
     */
    public void Toggle(ActionEvent actionEvent) throws SQLException {

        if (all.isSelected()) {
            appointmentlist = AppointmentData.getAll();
            appointmentTable.setItems(appointmentlist);
            appointmentTable.refresh();

        } else if (toggle.getSelectedToggle().equals(month)) {
            try {
                appointmentlist = AppointmentData.getAppointmentsMonth();
                appointmentTable.setItems(appointmentlist);
                appointmentTable.refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (toggle.getSelectedToggle().equals(week)) {
            try {
                appointmentlist = AppointmentData.getAppointmentsWeek();
                appointmentTable.setItems(appointmentlist);
                appointmentTable.refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

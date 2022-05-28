package controller;

/**
 * Appointment Update controller
 * @author Isabelle Matthews
 */

import Model.*;
import database.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * This class is for appointment Update form.
 */
public class AppointmentUpdate implements Initializable {
    public ComboBox<Contact> contactCombo;
    public ComboBox<Customer> customerCombo;
    public ComboBox<user> userCombo;
    public TextField appointmentID;
    public TextField title;
    public TextField description;
    public TextField type;
    public TextField startTxt;
    public TextField endTxt;
    public TextField location;
    String originalStart;
    String originalEnd;
    String originalType;
    String originalDescription;
    String originalLocation;
    String originalTitle;
    int originalUserId;
    int originalContactId;
    int originalCustomerId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactCombo.setItems(ContactData.getAllContact());
        customerCombo.setItems(CustomerData.getAllCustomers());
        userCombo.setItems(UserData.getAllUsers());

    }

    /**
     * This method is to save APPOINTMENT button to save the appointment
     * @param actionEvent actionevent
     * @throws IOException ioexception
     * @throws SQLException sql exception
     */
    public void saveButton(ActionEvent actionEvent) throws IOException, SQLException {

        if (title.getText().trim().isEmpty() || title.getText().trim().toLowerCase().equals("Title")) {
            AlertMessage.error(5, title);
            return;
        }
        if (description.getText().trim().isEmpty() || description.getText().trim().toLowerCase().equals("address")) {
            AlertMessage.error(6, description);
            return;
        }
        if (type.getText().trim().isEmpty() || type.getText().trim().toLowerCase().equals("phone")) {
            AlertMessage.error(7, type);
            return;
        }
        if (location.getText().trim().isEmpty() || location.getText().trim().toLowerCase().equals("postal code")) {
            AlertMessage.error(8, location);
            return;
        }
        if (startTxt.getText().trim().isEmpty() || startTxt.getText().trim().toLowerCase().equals("postal code")) {
            AlertMessage.error(9, startTxt);
            return;
        }
        if (endTxt.getText().trim().isEmpty() || endTxt.getText().trim().toLowerCase().equals("postal code")) {
            AlertMessage.error(10, endTxt);
            return;
        }
        ZonedDateTime start = testLDTParse(startTxt.getText());
        ZonedDateTime end = testLDTParse(endTxt.getText());
        LocalTime startBusiness = LocalTime.of(8, 00, 00);
        LocalTime endBusiness = LocalTime.of(22, 00, 00);

        if (start.toLocalTime().isBefore(startBusiness)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Appointment Start time is before start of business day.");
            alert.showAndWait();
            return;
        }
        if (start.toLocalTime().isAfter(endBusiness)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Appointment start time is after end of business day.");
            alert.showAndWait();
            return;
        }
        if (end.toLocalTime().isAfter(endBusiness)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Appointment End time is after end of business day");
            alert.showAndWait();
            return;
        }
        if (end.toLocalTime().isBefore(startBusiness)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Appointment End time is before start of business day");
            alert.showAndWait();
            return;
        }

        int appointment_ID = Integer.parseInt(appointmentID.getText());
        String Title = title.getText();
        String Description = description.getText();
        String Type = type.getText();
        String Location = location.getText();
        LocalDateTime Start = AppointmentUpdate.parseLocalDateTimeFromString(startTxt.getText());
        LocalDateTime End = AppointmentUpdate.parseLocalDateTimeFromString(endTxt.getText());
        int Contact_ID = (contactCombo.getValue().getContactID());
        int User_ID = (userCombo.getValue().getUserID());
        int Customer_ID = (customerCombo.getValue().getCustomer_ID());
        boolean isPastDate = isPriorDate(Start, End);

        if (isPastDate) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Appointment time is in the past.");
            alert.showAndWait();
        }

        boolean isOverLapping = checkOverlap(Start, End, appointment_ID);

            if (isOverLapping) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Appointment time is overlapping, please check again");
                alert.showAndWait();
            } else {

                AppointmentData.updateAppointment(appointment_ID, Title, Description, Type, Location, Start, End, Contact_ID, Customer_ID, User_ID);

                Parent root = FXMLLoader.load(getClass().getResource("/view/Scheduling.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 900, 500);
                stage.setTitle("Appointment");
                stage.setScene(scene);
            }
        }


    /**
     * This method is to do time conversion from string to localdatetime then EST
     * @param ldtStr ldtstr
     * @return zone date time
     */
    public static ZonedDateTime testLDTParse(String ldtStr) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(ldtStr, dtf);
        ZoneId estZid = ZoneId.of("America/New_York");
        ZonedDateTime localZdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        ZonedDateTime estZDT = ZonedDateTime.ofInstant(localZdt.toInstant(), estZid);
        return estZDT;
    }

    /**
     * This method is to convert from String to Local time for update data.
     * @param ldtStr ldtstr
     * @return localtime
     */
    public static LocalDateTime parseLocalDateTimeFromString(String ldtStr) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(ldtStr, dtf);
        return ldt;
    }

    /**
     * This method is to get appointment from appointment table into update form
     * @param appointment appointment
     */
    public void pullAppointment(Appointment appointment) {
        appointmentID.setText(String.valueOf(appointment.getAppointmentID()));
        title.setText(appointment.getTitle());
        description.setText(appointment.getDescription());
        type.setText(appointment.getType());
        location.setText(appointment.getLocation());

        String start = appointment.getStart().toString();
        start = start.replace("T", " ");
        start += ":00";
        startTxt.setText(start);
        originalStart = startTxt.getText();

        String end = appointment.getEnd().toString();
        end = end.replace("T", " ");
        end += ":00";
        endTxt.setText(end);
        originalEnd = endTxt.getText();

        originalDescription = description.getText();
        originalLocation = location.getText();
        originalTitle = title.getText();
        originalType = type.getText();


        for (Contact contact : contactCombo.getItems()) {
            if (contact.getContactID() == appointment.getContactID()) {
                contactCombo.setValue(contact);
                originalContactId = contact.getContactID();
                break;
            }
        }
        for (user use : userCombo.getItems()) {
            if (use.getUserID() == appointment.getUserID()) {
                userCombo.setValue(use);
                originalUserId = use.getUserID();
                break;
            }
        }
        for (Customer customer : customerCombo.getItems()) {
            if (customer.getCustomer_ID() == appointment.getCustomerID()) {
                System.out.println("customer.getCustomer_ID =" + customer.getCustomer_ID() + " appointment.getAppointmentID=" + appointment.getAppointmentID());
                customerCombo.setValue(customer);
                originalCustomerId = customer.getCustomer_ID();
                break;
            }
        }
    }


    /**
     * This method is to cancel appointment screen and go back to main screen
     * @param actionEvent action event
     * @throws IOException ioexception
     */
    public void cancelButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Scheduling.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 500);
        stage.setTitle("Appointment");
        stage.setScene(scene);
    }

    /**
     * This method is to check appointment to past date.
     *
     * @param start start
     * @param end   end
     * @return prior date
     */
    public boolean isPriorDate(LocalDateTime start, LocalDateTime end) {

        LocalDateTime now = LocalDateTime.now();
        if (start.isBefore(now) || end.isBefore(now)) {
            return true;
        }
        return false;
    }


    /**
     * This method is to check if there is any overlap appointment
     * @param startTime start
     * @param endTime end
     * @return overlap
     * @throws SQLException sql exception
     */
    public static boolean checkOverlap(LocalDateTime startTime, LocalDateTime endTime, int appointment_ID) throws SQLException {
        String sql = "SELECT * FROM client_schedule.appointments\n" +
                "WHERE ? > Start AND ? < End \n" +
                "OR ? > Start AND ? < End \n" +
                "OR Start = ? \n" +
                "OR End = ? \n" +
                "OR Start > ? AND Start < ? \n" +
                "OR End > ? AND End < ? \n" +
                "AND not appointment_Id = ?";

        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        preparedStatement.setTimestamp(1, Timestamp.valueOf(startTime));
        preparedStatement.setTimestamp(2, Timestamp.valueOf(startTime)); //start
        preparedStatement.setTimestamp(3, Timestamp.valueOf(endTime)); //end
        preparedStatement.setTimestamp(4, Timestamp.valueOf(endTime)); //end
        preparedStatement.setTimestamp(5, Timestamp.valueOf(startTime)); //start
        preparedStatement.setTimestamp(6, Timestamp.valueOf(endTime)); // edit
        preparedStatement.setTimestamp(7, Timestamp.valueOf(startTime));
        preparedStatement.setTimestamp(8, Timestamp.valueOf(endTime));
        preparedStatement.setTimestamp(9, Timestamp.valueOf(startTime));
        preparedStatement.setTimestamp(10, Timestamp.valueOf(endTime));
        preparedStatement.setInt(11, appointment_ID);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
         if (resultSet.getInt("Appointment_ID") == appointment_ID)
            return false;
         else return true;
        }
        return false;
    }
}
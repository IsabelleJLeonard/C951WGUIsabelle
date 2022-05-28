package controller;

/**
 * ADD controller for appointment table.
 * @author Isabelle Matthews
 */

import Model.Appointment;
import Model.Contact;
import Model.Customer;
import database.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * This class is for appointment add form.
 */
public class AppointmentAdd implements Initializable {
    public Button cancelButton;
    public TextField appointmentIdTxt;
    public TextField titleTxt;
    public TextField descriptionTxt;
    public TextField typeTxt;
    public TextField locationTxt;
    public TextField startTxt;
    public TextField endTxt;
    public ComboBox<Customer> customerCombo;
    public ComboBox<Model.user> userCombo;
    public ComboBox<Contact> contactCombo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerCombo.setItems(CustomerData.getAllCustomers());
        userCombo.setItems(UserData.getAllUsers());
        contactCombo.setItems(ContactData.getAllContact());
    }

    /**
     * change into localtime from string
     * @param ldtStr ldtstr
     * @return LDTparse
     */
    public static ZonedDateTime LDTParse(String ldtStr) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(ldtStr, dtf);
        ZoneId estZid = ZoneId.of("America/New_York");
        ZonedDateTime localZdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        ZonedDateTime estZDT = ZonedDateTime.ofInstant(localZdt.toInstant(), estZid);
        return estZDT;
    }

    /**
     * This method is to change only string to localdatetime
     * @param ldtStr ldtstr
     * @return localdatetime parse
     */
    public static LocalDateTime parseLocalDateTimeFromString(String  ldtStr) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(ldtStr, dtf);
        return ldt;
    }

    /**
     * This method is to save appointment to the table and alerts for out of business hours and any missing information from the form
     * @param actionEvent actionEvent
     * @throws IOException IOexepction
     * @throws SQLException SQL exception
     */
    public void onActionSaveAppointment(ActionEvent actionEvent) throws IOException, SQLException {
        // move error before SAVE, so it can check errors without saving error information after alert.
        if (titleTxt.getText().trim().isEmpty() || titleTxt.getText().trim().toLowerCase().equals("Title")) {
            AlertMessage.error(5, titleTxt);
            return;
        }
        if (descriptionTxt.getText().trim().isEmpty() || descriptionTxt.getText().trim().toLowerCase().equals("Description")) {
            AlertMessage.error(6, descriptionTxt);
            return;
        }
        if (typeTxt.getText().trim().isEmpty() || typeTxt.getText().trim().toLowerCase().equals("Type")) {
            AlertMessage.error(7, typeTxt);
            return;
        }
        if (locationTxt.getText().trim().isEmpty() || locationTxt.getText().trim().toLowerCase().equals("Location")) {
            AlertMessage.error(8, locationTxt);
            return;
        }
        if (contactCombo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("must select contact");
            alert.showAndWait();
            return;
        }
        if (userCombo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("must select user");
            alert.showAndWait();
            return;
        }
        if (customerCombo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("must select customer");
            alert.showAndWait();
            return;
        }

        //business hours 8am to 10pm EST alerts.
        ZonedDateTime start = LDTParse(startTxt.getText());
        ZonedDateTime end = LDTParse(endTxt.getText());
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

        String Title = titleTxt.getText();
        String Description = descriptionTxt.getText();
        String Type = typeTxt.getText();
        String Location = locationTxt.getText();
        LocalDateTime Start = AppointmentAdd.parseLocalDateTimeFromString(startTxt.getText());
        LocalDateTime End = AppointmentAdd.parseLocalDateTimeFromString(endTxt.getText());
        int Contact = (contactCombo.getValue().getContactID());
        int Customer = (customerCombo.getValue().getCustomer_ID());
        int User = (userCombo.getValue().getUserID());

        boolean isPastDate = isPriorDate(Start,End);
        if (isPastDate) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Appointment time is in the past.");
            alert.showAndWait();
        }
        else {
        boolean isOverLapping = checkOverlap(Start, End);

        if (isOverLapping) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Appointment time is overlapping, please check again");
            alert.showAndWait();
        } else {

            AppointmentData.addAppointment(Title, Description, Type, Location, Start, End, Contact, Customer, User);

        Parent root = FXMLLoader.load(getClass().getResource("/view/Scheduling.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Appointment");
        stage.setScene(scene);
        }
        }
    }

    /**
     * This method is to check past appointment vs new appointment.
     * @param start start
     * @param end end
     * @return return if time is before now.
     */
    public boolean isPriorDate(LocalDateTime start, LocalDateTime end) {

        LocalDateTime now = LocalDateTime.now();
        if (start.isBefore(now) || end.isBefore(now)) {
            return true;
        }
        return false;
    }


    /**
     * This method will go back to appointment schedule.
     * @param actionEvent actionevent
     * @throws IOException ioexception
     */
    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Scheduling.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Appointment");
        stage.setScene(scene);
    }

    /**
     * Customer Combo
     * @param actionEvent actionevent
     */
    public void customerCombo(ActionEvent actionEvent) {
    }

    /**
     * User combobox
     * @param actionEvent actionevent
     */
    public void userCombo(ActionEvent actionEvent) {
    }

    /**
     * contact Combobox
     * @param actionEvent actionevent
     */
    public void contactCombo(ActionEvent actionEvent) {
    }

    /**
     * This method is to check if there is any overlap appointment.
     * @param startTime start
     * @param endTime end
     * @return returm overlap information
     * @throws SQLException sqlexception
     */
    public static boolean checkOverlap(LocalDateTime startTime, LocalDateTime endTime) throws SQLException {
        String sql = "SELECT * FROM client_schedule.appointments\n" +
                "WHERE ? > Start AND ? < End \n" +
                "OR ? > Start AND ? < End \n" +
                "OR Start = ? \n" +
                "OR End = ? \n" +
                "OR Start > ? AND Start < ? \n" +
                "OR End > ? AND End < ? ";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        preparedStatement.setTimestamp(1, Timestamp.valueOf(startTime));
        preparedStatement.setTimestamp(2, Timestamp.valueOf(startTime));
        preparedStatement.setTimestamp(3, Timestamp.valueOf(endTime));
        preparedStatement.setTimestamp(4, Timestamp.valueOf(endTime));
        preparedStatement.setTimestamp(5, Timestamp.valueOf(startTime));
        preparedStatement.setTimestamp(6, Timestamp.valueOf(endTime));
        preparedStatement.setTimestamp(7, Timestamp.valueOf(startTime));
        preparedStatement.setTimestamp(8, Timestamp.valueOf(endTime));
        preparedStatement.setTimestamp(9, Timestamp.valueOf(startTime));
        preparedStatement.setTimestamp(10, Timestamp.valueOf(endTime));
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return true;
        }
        return false;

          /*  if (.equals(startTime) || end.equals(appointment.getEnd())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Appointment time is overlapping, please check again");
                alert.showAndWait();
                return;
            }
            if (start.isBefore(appointment.getStart()) && end.isAfter(appointment.getStart())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Appointment time is overlapping, please check again");
                alert.showAndWait();
                return;
            }
            if (start.isAfter(appointment.getStart()) && start.isBefore(appointment.getEnd())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Appointment time is overlapping, please check again");
                alert.showAndWait();
                return;
            }
            if (start.isBefore(appointment.getStart()) && end.isAfter(appointment.getStart())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Appointment time is overlapping, please check again");
                alert.showAndWait();
                return;
            }
            if (start.isAfter(appointment.getStart()) && end.isBefore(appointment.getStart())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Appointment time is overlapping, please check again");
                alert.showAndWait();
                return;
            }
            if (start.isBefore(appointment.getStart()) && end.equals(appointment.getEnd())) {
                System.out.println("No overlaps");
            }
            if (start.equals(appointment.getEnd()) && end.isBefore(appointment.getEnd())) {
                System.out.println("No overlaps");
            } else
                System.out.println("No overlaps");
        } */
    }
}
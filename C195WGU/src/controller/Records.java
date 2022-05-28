package controller;

/**
 * Records table with 3 variety report
@author Isabelle Matthews
 */

import Model.*;
import database.AppointmentData;
import database.ContactData;
import database.CountryData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class is for reports
 */
public class Records implements Initializable {
    //tab that holds whole 3 tables.
    public TabPane reportTab;

    // appointment table
    public TableView appointmentTotalTable;
    public Tab appointmentTab;
    public TableColumn typeTab;
    public TableColumn monthTab;
    public TableColumn appointmentTotal;

    //contact table
    public TableView contactTable;
    public Tab contactTab;
    public TableColumn reportcontactID;
    public TableColumn reportTitle;
    public TableColumn reportType;
    public TableColumn reportDescription;
    public TableColumn reportStart;
    public TableColumn reportEnd;
    public TableColumn reportID;

    //country table
    public TableView countryTable;
    public Tab countryTab;
    public TableColumn country;
    public TableColumn countryTotal;
    public ComboBox<Contact> contactCombo;

    ObservableList<Country> countrylist = FXCollections.observableArrayList();
    ObservableList<Contact> contactlist = FXCollections.observableArrayList();
    ObservableList<Appointment> appointmentlist = FXCollections.observableArrayList();
    ObservableList<Customer> customerlist = FXCollections.observableArrayList();

    @Override
    /**
     * Report initialize
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateAppointment();
        generateCountry();

        try {
            //appointment total table
            typeTab.setCellValueFactory(new PropertyValueFactory<>("Type"));
            monthTab.setCellValueFactory(new PropertyValueFactory<>("Month"));
            appointmentTotal.setCellValueFactory(new PropertyValueFactory<>("AppointmentTotal"));

            //contact appointment table
            reportcontactID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            reportTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            reportType.setCellValueFactory(new PropertyValueFactory<>("type"));
            reportDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            reportStart.setCellValueFactory(new PropertyValueFactory<>("start"));
            reportEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
            reportID.setCellValueFactory(new PropertyValueFactory<>("customerID"));

            //Country table
            country.setCellValueFactory(new PropertyValueFactory<>("CountryName"));
            countryTotal.setCellValueFactory(new PropertyValueFactory<>("customerTotal"));

            //contactTable.setItems(ContactData.getAllContact());
            contactCombo.setItems(ContactData.getAllContact());
            countryTable.setItems(CountryData.getTotalCountries());
            appointmentTotalTable.setItems(AppointmentData.getReport());
           
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This methos is for back button to go back to main menu
     * @param actionEvent
     * @throws IOException
     */
    public void backReport(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 400);
        stage.setTitle("Report");
        stage.setScene(scene);
    }

    /**
     * This is to generate button for contacts to pick contact before download appointment information.
     */
    public void generateContacts() {
        try {
            Contact contact = contactCombo.getValue();
            ObservableList<Appointment> appointments = AppointmentData.getContactAppointments(contact.getContactID());
            contactTable.setItems(appointments);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is to generate country and its total
     * */
    public void generateCountry() {
        try {
            ObservableList<CountryReport> countries = CountryData.getTotalCountries();
            countryTable.setItems(countries);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * generate appointment into type, month, and total
     * */
    public void generateAppointment() {
        try {
            ObservableList<AppointmentReport> appointments = AppointmentData.getReport();
            appointmentTotalTable.setItems(appointments);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This is for combo box for contactID to get appointment information into table
     */
    public void contactAction() {
            Contact contact = contactCombo.getValue();
            if (contact != null) {
                generateContacts();
            }
        }
    }

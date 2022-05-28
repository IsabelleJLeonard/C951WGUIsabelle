package controller;

/**
 * Customer Add Controller
 * @author Isabelle Matthews
 */

import Model.Country;
import Model.Customer;
import Model.Division;
import database.CountryData;
import database.CustomerData;
import database.DivisionData;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * This class is for Customer Add form
 */
public class CustomerAdd implements Initializable {
    public TextField customer_Name;
    public TextField Address;
    public TextField phone;
    public TextField postal_Code;
    public ComboBox<Division> divisionCombo;
    public ComboBox<Country> countryCombo;
    public TextField customerID;

    public CustomerAdd() throws IOException {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCombo.setItems(CountryData.getAllCountries());
    }

    /**
     * This method is to cancel CUSTOMER button and go back to customer list
     * @param actionEvent
     * @throws IOException
     */
    public void cancelButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/SecondScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 500);
        stage.setTitle("Customer List");
        stage.setScene(scene);
    }

    /**
     * This method is to save APPOINTMENT button to save new customer
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void saveButton(ActionEvent actionEvent) throws IOException, SQLException {
        try {
            // move error before SAVE, so it can check errors without saving error information after alert.
            if (customer_Name.getText().trim().isEmpty() || customer_Name.getText().trim().toLowerCase().equals("customer name")) {
                AlertMessage.error(4, customer_Name);
                return;
            }
            if (Address.getText().trim().isEmpty() || Address.getText().trim().toLowerCase().equals("address")) {
                AlertMessage.error(3, Address);
                return;
            }
            if (phone.getText().trim().isEmpty() || phone.getText().trim().toLowerCase().equals("phone")) {
                AlertMessage.error(2, phone);
                return;
            }
            if (postal_Code.getText().trim().isEmpty() || postal_Code.getText().trim().toLowerCase().equals("postal code")) {
                AlertMessage.error(1, postal_Code);
                return;
            }
            if (countryCombo.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("must select country");
                alert.showAndWait();
                return;
            }
            if (divisionCombo.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("must select division");
                alert.showAndWait();
                return;
            }

            String customerName = customer_Name.getText();
            String address = Address.getText();
            String phoneNum = phone.getText();
            String postalCode = postal_Code.getText();
            int countryID = (countryCombo.getValue().getCountryId());
            int divisionID = (divisionCombo.getValue().getDivisionID());
            CustomerData.addCustomer(customerName, address, phoneNum, postalCode, countryID, divisionID);

            Parent root = FXMLLoader.load(getClass().getResource("/view/SecondScreen.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 500);
            stage.setTitle("Customer List");
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  COMBO FOR COUNTRY then DIVISION DEPEND ON COUNTRY SELECTION
     * @param actionEvent
     */
    public void onCountryChange(ActionEvent actionEvent) {
        Country country = countryCombo.getValue();
        if (country != null) {
            divisionCombo.setItems(DivisionData.getDivisionByCountry(country.getCountryId()));
        }
    }
}
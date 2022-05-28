package controller;

/**
 * Customer Update Controller
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
import javafx.fxml.FXML;
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
import java.util.ResourceBundle;

/** This class is for customer update information */
public class CustomerUpdate implements Initializable {

    @FXML public TextField customerID;
    @FXML public TextField customerName;
    @FXML public TextField country;
    @FXML public TextField address;
    @FXML public TextField phone;
    @FXML public TextField postalCode;
    @FXML public ComboBox<Division> divisionCombo;
    @FXML public ComboBox<Country> countryCombo;
    @FXML public Button updateCustomer;
    @FXML public Button cancelButton;

    ObservableList<DivisionData> divisionList = FXCollections.observableArrayList();
    ObservableList<CountryData> countryList = FXCollections.observableArrayList();
    ObservableList<Division> divisions = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCombo.setItems(CountryData.getAllCountries());
    }


    /**
     * This method is to CANCEL BUTTON to go back to customer list screen
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
     * This method is to UPDATE CUSTOMER INFORMATION AND GO BACK TO CUSTOMER TABLE
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void updateCustomer(ActionEvent actionEvent) throws IOException, SQLException {
        try {
            int customer_ID = Integer.parseInt(customerID.getText());
            String customer_Name = customerName.getText();
            String Address = address.getText();
            String phoneNum = phone.getText();
            String postal_Code = postalCode.getText();
            int countryID = (countryCombo.getValue().getCountryId());
            int divisionID = (divisionCombo.getValue().getDivisionID());
            CustomerData.updateCustomer(customer_ID, customer_Name, Address, phoneNum, postal_Code, countryID, divisionID);

            if (customerName.getText().trim().isEmpty() || customerName.getText().trim().toLowerCase().equals("customer name")) {
                AlertMessage.error(4, customerName);
                return;
            }
            if (address.getText().trim().isEmpty() || address.getText().trim().toLowerCase().equals("address")) {
                    AlertMessage.error(3, address);
                    return;
            }
            if (phone.getText().trim().isEmpty() || phone.getText().trim().toLowerCase().equals("phone")) {
                AlertMessage.error(2, phone);
                return;
            }
            if (postalCode.getText().trim().isEmpty() || postalCode.getText().trim().toLowerCase().equals("postal code")) {
                AlertMessage.error(1, postalCode);
                return;
            }

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
     * Get the data from customer table and transfer it to update to edit.
     * Lambdas expression line 119 created for division combo box to depend on Country combo box selection.
      * This have created to reduce the need to code every detail.
     */
    public void pullCustomer(Customer customer) {
        customerID.setText(String.valueOf(customer.getCustomer_ID()));
        customerName.setText(customer.getCustomer_Name());
        address.setText(customer.getAddress());
        postalCode.setText(customer.getPostal_Code());
        phone.setText(customer.getPhone());
        Country cnty = CountryData.getCountryByDivision(customer.getDivisionID());
        countryCombo.setValue(cnty);

        divisionCombo.setItems(DivisionData.getDivisionByCountry(cnty.getCountryId()));
        DivisionData.getDivisionByCountry(cnty.getCountryId()).forEach(Division -> {
            if(Division.getDivisionID() == customer.getDivisionID())
                divisionCombo.setValue(Division);
                });
    }

/**
 * This method shows when country is selected, division will show what is linked to specific country.
      * @param actionEvent
     */
    public void onCountryChange (ActionEvent actionEvent) {
        Country country = countryCombo.getValue();

        if(country != null) {
            divisionCombo.setItems(DivisionData.getDivisionByCountry(country.getCountryId()));
        }
    }
}
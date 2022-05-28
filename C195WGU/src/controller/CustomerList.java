package controller;

/**
 * Customer List controller
 * @author Isabelle Matthews
 */

import Model.Appointment;
import Model.Country;
import Model.Customer;
import Model.Division;
import database.AppointmentData;
import database.CustomerData;
import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * This class is for Customer Table
 */
public class CustomerList implements Initializable {
    private Customer customer;
    public TableView customerTable;
    public TableColumn Customer_ID;
    public TableColumn customer_Name;
    public TableColumn Division;
    public TableColumn Country;
    public TableColumn Address;
    public TableColumn postal_Code;
    public TableColumn Phone;

    ObservableList<Customer> customerlist = FXCollections.observableArrayList();
    ObservableList<Appointment> appointmentlist = FXCollections.observableArrayList();
    ObservableList<Country> countrylist = FXCollections.observableArrayList();
    ObservableList<Division> divisionlist = FXCollections.observableArrayList();


    @Override
    /**
     * Customer List initialize
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("X");
        try {
            Customer_ID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
            customer_Name.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
            Division.setCellValueFactory(new PropertyValueFactory<>("DivisionID"));
            Country.setCellValueFactory(new PropertyValueFactory<>("CountryName"));
            Address.setCellValueFactory(new PropertyValueFactory<>("Address"));
            postal_Code.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
            Phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
            customerTable.setItems(CustomerData.getAllCustomers());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This method have BACK BUTTON that will go back to main screen
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
     * This method will ADD CUSTOMER to go to customer add form
     * @param actionEvent
     * @throws IOException
     */
    public void addCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerAdd.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 450, 400);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
    }

    /**
     * This method do EDIT BUTTON to go to update customer form
     * @param actionEvent
     * @throws IOException
     */
    public void editCustomer(ActionEvent actionEvent) throws IOException {

        if (customerTable.getSelectionModel().getSelectedItem() != null) {
            try {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/CustomerUpdate.fxml"));
                loader.load();
                CustomerUpdate controller = loader.getController();
                controller.pullCustomer((Customer) customerTable.getSelectionModel().getSelectedItem());
                Scene scene = new Scene(loader.getRoot(), 450, 400);
                stage.setTitle("update Customer");
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
     *  This method have deletet button is to delete the customer from the list and confirms the delete alert
     * @param actionEvent
     * @throws SQLException
     */
    public void removeCustomer(ActionEvent actionEvent) throws SQLException {
        Customer customer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        if (customer != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int rowsAffected = CustomerData.deleteCustomer(customer);
                if (rowsAffected > 0) {
                    customerTable.setItems(CustomerData.getAllCustomers());
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("You must select a customer to remove");
            alert.showAndWait();
        }
    }
}

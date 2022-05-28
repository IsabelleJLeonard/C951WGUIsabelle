package database;

/**
 * customer data
 * @author Isabelle Matthews
 */

import Model.*;
import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextField;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

/**
 * class for customer data
 */
public class CustomerData {


    /**
     * This method is to get all customers.
     * @return
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customerlist = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM customers, first_level_divisions, countries  WHERE customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.Country_ID = countries.Country_ID";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customer_ID = rs.getInt("Customer_ID");
                String customer_Name = rs.getString("Customer_Name");
                String Address = rs.getString("Address");
                String postal_Code = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int division_ID = rs.getInt("Division_ID");
                int countryID = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                String division = rs.getString("Division");
                Customer c = new Customer(customer_ID, customer_Name, Address, postal_Code, phone, division_ID, countryID, country, division);
                customerlist.add(c);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerlist;
    }

    /**
     * This methos is to ADD CUSTOMER
     * @param customer_Name
     * @param Address
     * @param phone
     * @param postal_Code
     * @param countryID
     * @param divisionID
     * @return
     * @throws SQLException
     */
    public static int addCustomer(String customer_Name, String Address, String phone, String postal_Code, int countryID, int divisionID) throws SQLException {
        String sql = "INSERT into customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customer_Name);
        ps.setString(2, Address);
        ps.setString(3, postal_Code);
        ps.setString(4, phone);
        ps.setInt(5, divisionID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * This methos it to UPDATE CUSTOMER
     * @param customerID
     * @param customer_Name
     * @param Address
     * @param phone
     * @param postal_Code
     * @param countryID
     * @param divisionID
     * @return
     * @throws SQLException
     */
    public static int updateCustomer(int customerID, String customer_Name, String Address, String phone, String postal_Code, int countryID, int divisionID) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, postal_Code = ?, phone = ?, division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customer_Name);
        ps.setString(2, Address);
        ps.setString(3, postal_Code);
        ps.setString(4, phone);
        ps.setInt(5, divisionID);
        ps.setInt(6, customerID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * This method is to DELETE CUSTOMER
     * @param customerID
     * @param customer_Name
     * @param Address
     * @param postal_Code
     * @param phone
     * @param divisionID
     * @return
     * @throws SQLException
     */
    public static int deleteCustomer(int customerID, String customer_Name, String Address, String postal_Code, String phone, int divisionID) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?,Customer_Name = ?, Address = ?, Postal_code = ?, Phone = ?, Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ps.setString(2, customer_Name);
        ps.setString(3, Address);
        ps.setString(4, postal_Code);
        ps.setString(5, phone);
        ps.setInt(6, divisionID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * This method is to delete customer
     * @param customer
     * @return
     * @throws SQLException
     */
    public static int deleteCustomer(Customer customer) throws SQLException {
            int customerID = customer.getCustomer_ID();
            String customer_Name = customer.getCustomer_Name();
            String Address = customer.getAddress();
            String postal_Code = customer.getPostal_Code();
            String phone = customer.getPhone();
            int divisionID = customer.getDivisionID();
            String sql = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerID);
            System.out.println(customerID);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
        }
}
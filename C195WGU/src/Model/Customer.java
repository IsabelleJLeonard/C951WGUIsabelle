package Model;

/**
 *
 * @author Isabelle Matthews
 */

import database.CountryData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * Class customer
 */
public class Customer {
     public static ObservableList<Customer> allcustomers = FXCollections.observableArrayList();
     private int customer_ID;
     private String customer_Name;
     private String address;
     private String postal_Code;
     private String phone;
     private int divisionID;
     private int countryID;
     private String country, division;

    /**
     * Customer java
     * @param customer_ID customerID
     * @param customer_Name customer name
     * @param address address
     * @param postal_Code postal code
     * @param phone phone
     * @param divisionID division id
     * @param countryID country id
     * @param country country name
     * @param division division
     */
    public Customer(int customer_ID, String customer_Name, String address, String postal_Code, String phone, int divisionID, int countryID, String country, String division) {
         this.customer_ID = customer_ID;
         this.customer_Name = customer_Name;
         this.address = address;
         this.postal_Code = postal_Code;
         this.phone = phone;
         this.divisionID = divisionID;
         this.countryID = countryID;
         this.country = country;
         this.division = division;
     }

     /**
      * Get country id
      * @return country id
      */
     public int getCountryID() {
         return countryID;
     }

     /**
      * set int country id
      * @param countryID countryid
      */
     public void setCountryID(int countryID) {
         this.countryID = countryID;
     }

     /**
      * Get string country
      * @return country
      */
     public String getCountry() {
         return country;
     }

     /**
      * Set string country
      * @param country country
      */
     public void setCountry(String country) {
         this.country = country;
     }

     /**
      * get string division
      * @return string division
      */
     public String getDivision() {
         return division;
     }

     /**
      * set divisiom
      * @param division division
      */
     public void setDivision(String division) {
         this.division = division;
     }


     /**
      * all customers
      * @return all customers
      */
     public static int allcustomers() {
         return allcustomers.size();
     }

     /**
      * get division id
      * @return division id
      */
     public int getDivisionID() {
         return divisionID;
     }

     /**
      * set division id
      * @param divisionID divisionid
      */
     public void setDivisionID(int divisionID) {
         this.divisionID = divisionID;
     }

     /**
      * get int customer id
      * @return customer id
      */
     public int getCustomer_ID() {return customer_ID;}

     /**
      * Set int customer id
      * @param customer_ID int customerid
      */
     public void setCustomer_ID(int customer_ID) {
         this.customer_ID = customer_ID;
     }

     /**
      * Get string address
      * @return string address
      */
     public String getAddress() {
         return address;
     }

     /**
      * Set address
      * @param address address
      */
     public void setAddress(String address) {
         this.address = address;
     }

     public String getCustomer_Name() {
         return customer_Name;
     }

     /**
      * Set customer name
      * @param customer_Name customer name
      */
     public void setCustomer_Name(String customer_Name) {
         this.customer_Name = customer_Name;
     }

     /**
      * Get phone
      * @return phone number
      */
     public String getPhone() {
         return phone;
     }

     /**
      * Set phone number
      * @param phone phone
      */
     public void setPhone(String phone) {
         this.phone = phone;
     }

     /**
      * Get postal code String
      * @return return postal code
      */
     public String getPostal_Code() {
         return postal_Code;
     }

     public void setPostal_Code(String postal_Code) {
         this.postal_Code = postal_Code;
     }

     public String getCountryName() {
         return CountryData.getCountryByDivision(divisionID).getCountryName();
     }


     @Override
     public String toString() {
         return (customer_Name);
     }
 }
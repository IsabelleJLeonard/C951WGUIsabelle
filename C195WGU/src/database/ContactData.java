package database;

/**
 * contact data class
 * @author Isabelle Matthews
 */

import Model.Contact;
import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

/**
 *  class for contact to get information from database
 * */
public class ContactData {

    /**
     * This gets all contact data from DB
     * @return
     */
    public static ObservableList<Contact> getAllContact() {
        ObservableList<Contact> clist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from contacts";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact c = new Contact(contactID, contactName, email);
                clist.add(c);            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();        }
        return clist;
    }
}


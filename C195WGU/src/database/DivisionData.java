package database;

/**
 * division data
 * @author Isabelle Matthews
 */

import Model.Division;
import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

/**
 * class division data
 */
public class DivisionData {

    /**
     * This method is to get all divisions from DB
     * @return
     */
    public static ObservableList<Division> getAllDivision() {

        ObservableList<Division> divisionlist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from first_level_divisions";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                Division d = new Division(divisionID, division);
                divisionlist.add(d);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return divisionlist;
    }

    /**
     * This method is to get division by country
     * @param countryID
     * @return
     */
    public static ObservableList<Division> getDivisionByCountry(int countryID) {

        ObservableList<Division> divisionlist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from first_level_divisions WHERE country_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, countryID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                Division d = new Division(divisionID, division);
                divisionlist.add(d);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return divisionlist;
    }

}
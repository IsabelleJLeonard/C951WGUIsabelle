package database;

/**
 * country data
 * @author Isabelle Matthews
 */

import Model.Country;
import Model.CountryReport;
import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

/**
 * class to get country data from DB
 * */
public class CountryData {

    /**
     * This is to get all countries from DB.
     * @return
     */
    public static ObservableList<Country> getAllCountries() {

        ObservableList<Country> clist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from countries";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country c = new Country(countryId, countryName);
                clist.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return clist;
    }

    /**
     * This is to get division from country choice.
     * @param divisionID
     * @return
     */
    public static Country getCountryByDivision(int divisionID) {

        try {
            String sql = "SELECT * from countries c inner join first_level_divisions d on c.country_ID = d.country_id and d.division_id=?";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1,divisionID);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country c = new Country(countryId, countryName);
                return c;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * This is to get total of countries.
     * @return
     */
    public static ObservableList<CountryReport> getTotalCountries() {
        ObservableList<CountryReport> countries = FXCollections.observableArrayList();
        try {
            String sql = "SELECT c.Country, count(cu.Customer_ID) as total from countries as c inner join first_level_divisions as d on c.country_ID = d.country_id inner join Customers as cu on cu.division_id= d.division_id group by c.country";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int countryTotal = resultSet.getInt("Total");
                String countryName = resultSet.getString("Country");
                CountryReport c = new CountryReport(countryName, countryTotal);
                countries.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countries;
    }
}

package database;

/**
 * user data
 * @author Isabelle Matthews
 */

import Model.user;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

/**
 *  user class data
 */
public class UserData {

    /**
     * This method is to get all user from data
     * @return
     */
    public static ObservableList<user> getAllUsers() {
        ObservableList<user> user = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                user u = new user(userID, userName, password);
                user.add(u);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    /**
     * This method holds log in information
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public static boolean login(String username, String password) throws SQLException {
             String statement  = "SELECT * FROM users WHERE user_name = ? and password = ?";
             Query.setPreparedStatement(JDBC.getConnection(), statement);
             PreparedStatement preparedStatement = Query.getPreparedStatement();

             preparedStatement.setString(1,username);
             preparedStatement.setString(2, password);

             try {
                 preparedStatement.execute();
                 ResultSet resultSet = preparedStatement.getResultSet();
                 return (resultSet.next());

        } catch (Exception e) {
            System.out.println("ERROR");
            return false;
        }
    }
}
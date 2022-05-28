package database;

/**
 * AppointmentData class
 * @author Isabelle Matthews
 */

import Model.Appointment;
import Model.AppointmentReport;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class AppointmentData {

    /**
     * This is to get all appointments into table
     * @return
     */
    public static ObservableList<Appointment> getAllAppointment() {

        ObservableList<Appointment> appointmentlist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int appointmentId = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                int contactID = resultSet.getInt("Contact_ID");
                String type = resultSet.getString("Type");
                LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
                int customerID = resultSet.getInt("Customer_ID");
                int userID = resultSet.getInt("User_ID");
                Appointment a = new Appointment(appointmentId, title, description, location, contactID, type, start, end, customerID, userID);

                appointmentlist.add(a);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentlist;
    }

    /**
     * This is to add appointment into Database
     * @param Title title
     * @param Description description
     * @param Type type
     * @param Location location
     * @param Start start
     * @param End end
     * @param Customer_ID customerid
     * @param User_ID userid
     * @param Contact_ID contactid
     * @return return new add information
     * @throws SQLException sqlexception
     */
    public static int addAppointment(String Title, String Description, String Type, String Location, LocalDateTime Start, LocalDateTime End, int Contact_ID,int Customer_ID, int User_ID) throws SQLException {
        String sql = "INSERT into appointments (Title, Description, Type, Location, Start, End, Contact_ID, Customer_ID, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, Title);
        ps.setString(2, Description);
        ps.setString(3, Type);
        ps.setString(4, Location);
        ps.setTimestamp(5, Timestamp.valueOf(Start));
        ps.setTimestamp(6, Timestamp.valueOf(End));
        ps.setInt(7, Contact_ID);
        ps.setInt(8, Customer_ID);
        ps.setInt(9, User_ID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * update appointment into Database
     * @param appointment_id appointmentid
     * @param title title
     * @param description description
     * @param type type
     * @param location location
     * @param start start
     * @param end end
     * @param Contact_ID contactid
     * @param User_ID userid
     * @param Customer_ID customerid
     * @return return update appointment
     * @throws SQLException sqlexecption
     */
    public static int updateAppointment(int appointment_id, String title, String description, String type, String location, LocalDateTime start, LocalDateTime end, int Contact_ID, int Customer_ID, int User_ID) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Type = ?, Location = ?, Start = ?, End = ?, Contact_ID = ?, Customer_ID = ?, User_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, type);
        ps.setString(4, location);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setInt(7, Contact_ID);
        ps.setInt(8, Customer_ID);
        ps.setInt(9, User_ID);
        ps.setInt(10, appointment_id);
        System.out.println(ps.toString());
        int rowsAffected = ps.executeUpdate();
        System.out.println("Records =" + rowsAffected);
        return rowsAffected;
    }

    /**
     * delete appointment from database
     * @param appointment appointment
     * @return remove appointment
     * @throws SQLException sql exception
     */
    public static int deleteAppointment(Appointment appointment) throws SQLException {
        int appointmentID = appointment.getAppointmentID();
        String Title = appointment.getTitle();
        String Description = appointment.getDescription();
        String Type = appointment.getType();
        String Location = appointment.getLocation();
        int Contact = appointment.getContactID();
        int User = appointment.getUserID();
        int Customer = appointment.getCustomerID();
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        System.out.println(appointmentID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * This method get all appointment for radio button in scheduling to show all appointment
     * @return all appointment
     * @throws SQLException sql exception
     */
    public static ObservableList<Appointment> getAll() throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID;";

        Query.setPreparedStatement(JDBC.getConnection(), queryStatement);
        PreparedStatement preparedStatement = Query.getPreparedStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                Appointment appointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name")
                );
                appointmentList.add(appointment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * This method is radio button select month for appointment within 30 days from today date
     * @return appointment in 30 days
     * @throws SQLException sql exception
     */
    public static ObservableList<Appointment> getAppointmentsMonth() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        LocalDateTime todaysDate = LocalDateTime.now();
        LocalDateTime lastMonth = todaysDate.minusDays(30);

        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE MONTH(START) = MONTH(NOW())";

        Query.setPreparedStatement(JDBC.getConnection(), queryStatement);
        PreparedStatement preparedStatement = Query.getPreparedStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                Appointment appointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name")
                );

                appointments.add(appointment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * This method is radio button for appointment to select week within 7 days from today date.
     * @return return week in appointment
     * @throws SQLException sql exception
     */
    public static ObservableList<Appointment> getAppointmentsWeek() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        LocalDateTime todaysDate = LocalDateTime.now();
        LocalDateTime oneWeek = todaysDate.plusDays(7);

        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE YEARWEEK(START) = YEARWEEK(NOW())";

        Query.setPreparedStatement(JDBC.getConnection(), queryStatement);
        PreparedStatement preparedStatement = Query.getPreparedStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                Appointment appointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name")
                );

                appointments.add(appointment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * This method do REPORT TABLE: get Contact's appointment list into report contact table
     * @param contactID contactid
     * @return return all contact/appointment
     * @throws SQLException sql exception
     */
    public static ObservableList<Appointment> getContactAppointments(int contactID) throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, Type, Start, End, Title, Description, Customer_ID FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID = c.Contact_ID WHERE a.Contact_ID = ?";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, contactID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Appointment appointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Type"),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID")
                );
                appointments.add(appointment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * This method do REPORT table: get appointment information, count how many appointment by type and month for report.
     * @return report for appointment
     * @throws SQLException sql exception
     */
    public static ObservableList<AppointmentReport> getReport() throws SQLException {
        ObservableList<AppointmentReport> appointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Type, month(start) as month, count(*) as total FROM appointments GROUP BY Type, MONTH(start);";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                AppointmentReport appointment = new AppointmentReport(
                        resultSet.getString("Type"),
                        resultSet.getInt("Month"),
                        resultSet.getInt("Total")
                );
                appointments.add(appointment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * This method get CustomerAppointment for report table.
     * @param customerID customerid
     * @return customer appointment
     * @throws SQLException sql exception
     */
    public static ObservableList<Appointment> getCustomerAppointment(int customerID) throws SQLException {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID = c.Contact_ID WHERE Customer_ID = ?";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, customerID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Appointment appointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Type"),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID")
                );
                appointments.add(appointment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }
}
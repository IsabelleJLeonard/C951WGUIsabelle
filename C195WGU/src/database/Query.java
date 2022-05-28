package database;

/**
 * Query data
 * @author Isabelle Matthews
 */

import java.io.IOException;
import java.sql.*;

import static database.JDBC.connection;

/** this class is query */
public class Query {
    private static String query;
    private static Statement stmt;
    private static ResultSet result;

    /** execute Query */
    public static void executeQuery(String q) {
        query = q;
        try {
            stmt = connection.createStatement();
            if (query.toLowerCase().startsWith("start"))
                result = stmt.executeQuery(q);
            if (query.toLowerCase().startsWith("delete") || query.toLowerCase().startsWith("insert") || query.toLowerCase().startsWith("update"))
                stmt.executeUpdate(q);

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /** result to get result back */
    public static ResultSet getResult() {
        return result;
    }

    private static PreparedStatement preparedStatement;

    public static void setPreparedStatement(Connection connection, String queryStatement) throws SQLException {
        preparedStatement = connection.prepareStatement(queryStatement);
    }

    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }
}

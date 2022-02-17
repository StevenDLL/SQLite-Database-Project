package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnectionUtil {

    private static final String JDBC_CLASS_NAME = "org.sqlite.JDBC";
    private static final String DB_LOCATION = "jdbc:sqlite:";

    public static Connection getConnection(String dbName) {
        try {
            Class.forName(JDBC_CLASS_NAME);
            return DriverManager.getConnection(DB_LOCATION + dbName);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean stopConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

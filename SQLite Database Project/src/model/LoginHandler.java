package model;

import utilities.DisplayAlert;
import utilities.dbConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginHandler {
    public Connection connection = null;
    public static final String DB_NAME = "MAIN_DB.db";

    public LoginHandler() {
        connection = dbConnectionUtil.getConnection(DB_NAME);
        if (connection == null) {
            DisplayAlert.displayInformationAlert("Unable to connect to database");
            System.exit(1);
        }
    }

    public boolean checkConnection() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkLogin(String username, String password) {
        resetConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlQuery = "SELECT * FROM admins WHERE Username=? AND Password=?";

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
                dbConnectionUtil.stopConnection(connection);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void resetConnection() {
        try {
            if (connection.isClosed()) {
                connection = dbConnectionUtil.getConnection(DB_NAME);
                if (connection == null) {
                    System.out.println("Connection reset failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.LoginHandler;
import model.Student;

import java.sql.*;

public class dbInteractionUtils {

    public static ObservableList<Student> doStudentSQLSearch(int ID, String firstName, String lastName, String userName) {

        ObservableList<Student> studentList = FXCollections.observableArrayList();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlQuery = "SELECT * FROM students WHERE ID=? AND Firstname=? AND Lastname=? AND Username=?";

        try {
            connection = dbConnectionUtil.getConnection(LoginHandler.DB_NAME);
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setQueryTimeout(30);
            preparedStatement.setInt(1, ID);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, userName);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                studentList.add(new Student(resultSet.getInt("ID"), resultSet.getString("Firstname"), resultSet.getString("Lastname"), resultSet.getString("Username"), resultSet.getString("Password"), resultSet.getInt("Address"), doAddressIdSQLSearch(resultSet.getInt("Address"))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentList;
    }

    public static ObservableList<Student> doStudentIDSQLSearch(int ID) {

        ObservableList<Student> studentList = FXCollections.observableArrayList();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlQuery = "SELECT * FROM students WHERE ID=?";

        try {
            connection = dbConnectionUtil.getConnection(LoginHandler.DB_NAME);
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setQueryTimeout(30);
            preparedStatement.setInt(1, ID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                studentList.add(new Student(resultSet.getInt("ID"), resultSet.getString("Firstname"), resultSet.getString("Lastname"), resultSet.getString("Username"), resultSet.getString("Password"), resultSet.getInt("Address"), doAddressIdSQLSearch(resultSet.getInt("Address"))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentList;
    }

    public static String doAddressIdSQLSearch(int addressID) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlQuery = "SELECT * FROM address WHERE AddressID=?";

        StringBuilder fullAddress = new StringBuilder();

        try {
            connection = dbConnectionUtil.getConnection(LoginHandler.DB_NAME);
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setQueryTimeout(30);
            preparedStatement.setInt(1, addressID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                fullAddress.append(resultSet.getString("Street_Address")).append(" ").append(resultSet.getString("City")).append(" ").append(resultSet.getString("State")).append(" ").append(resultSet.getString("Zip_Code")).append(" ").append(resultSet.getString("Country")).append(" ");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fullAddress.toString();

    }

    public static int doAddressSQLSearch(String streetAddress, String city, String state, String zipCode, String country) {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlQuery = "SELECT * FROM address WHERE Street_Address=? AND City=? AND State=? AND Zip_Code=? AND Country=?";

        int location = 0;

        try {
            preparedStatement = getAddressPreparedStatement(streetAddress, city, state, zipCode, country, sqlQuery);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                location = resultSet.getInt("AddressID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return location;

    }

    public static int addAddressSQL(String streetAddress, String city, String state, String zipCode, String country) {

        PreparedStatement preparedStatement = null;
        String sqlQuery = "INSERT INTO address (Street_Address, City, State, Zip_Code, Country) VALUES (?,?,?,?,?)";

        try {
            preparedStatement = getAddressPreparedStatement(streetAddress, city, state, zipCode, country, sqlQuery);
            preparedStatement.executeUpdate();
            return doAddressSQLSearch(streetAddress, city, state, zipCode, country);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;

    }

    private static PreparedStatement getAddressPreparedStatement(String streetAddress, String city, String state, String zipCode, String country, String sqlQuery) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = getStudentPreparedStatement(streetAddress, city, state, zipCode, sqlQuery);
        preparedStatement.setString(5, country);
        return preparedStatement;
    }

    public static boolean addStudentSQL(String firstName, String lastName, String userName, String password,
                                        String streetAddress, String city, String state, String zipCode, String country) {

        PreparedStatement preparedStatement = null;
        String sqlQuery = "INSERT INTO students (Firstname, Lastname, Username, Password, Address) VALUES (?,?,?,?,?);";

        try {
            preparedStatement = getStudentPreparedStatement(firstName, lastName, userName, password, sqlQuery);

            int location = doAddressSQLSearch(streetAddress, city, state, zipCode, country);

            if (location == 0) {
                location = dbInteractionUtils.addAddressSQL(streetAddress, city, state, zipCode, country);
            }

            preparedStatement.setInt(5, location);
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            if (!e.getMessage().contains("[SQLITE_CONSTRAINT_UNIQUE]")) {
                e.printStackTrace();
            }
            return false;
        }
    }

    private static PreparedStatement getStudentPreparedStatement(String firstName, String lastName, String userName, String password, String sqlQuery) throws SQLException {
        Connection connection;
        PreparedStatement preparedStatement;
        connection = dbConnectionUtil.getConnection(LoginHandler.DB_NAME);
        preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setQueryTimeout(30);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, userName);
        preparedStatement.setString(4, password);
        return preparedStatement;
    }

    public static ObservableList<Student> doShowAllSQLQuery() {
        ObservableList<Student> studentList = FXCollections.observableArrayList();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String sqlQuery = "SELECT * FROM students";

        try {
            connection = dbConnectionUtil.getConnection(LoginHandler.DB_NAME);
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                studentList.add(new Student(resultSet.getInt("ID"), resultSet.getString("Firstname"), resultSet.getString("Lastname"), resultSet.getString("Username"), resultSet.getString("Password"), resultSet.getInt("Address"), doAddressIdSQLSearch(resultSet.getInt("Address"))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentList;
    }

    public static boolean doSQLDrop(int ID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result = 0;
        String sqlQuery = "DELETE FROM students WHERE ID=?";

        try {
            connection = dbConnectionUtil.getConnection(LoginHandler.DB_NAME);
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setQueryTimeout(30);
            preparedStatement.setInt(1, ID);
            result = preparedStatement.executeUpdate();
            if (result > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    public static boolean doSQLUpdate(Student selectedItem) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result = 0;
        String sqlQuery = "UPDATE students SET Firstname=? ,Lastname=? ,Username=? ,Password=?,Address=? WHERE ID=?";
        try {
            connection = dbConnectionUtil.getConnection(LoginHandler.DB_NAME);
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setQueryTimeout(30);
            preparedStatement.setString(1, selectedItem.getFirstName());
            preparedStatement.setString(2, selectedItem.getLastName());
            preparedStatement.setString(3, selectedItem.getUserName());
            preparedStatement.setString(4, selectedItem.getPassword());

            String[] splitAddress = selectedItem.getSplitAddress();
            int location = doAddressSQLSearch(splitAddress[0], splitAddress[1], splitAddress[2], splitAddress[3], splitAddress[4]);

            if (location == 0) {
                location = dbInteractionUtils.addAddressSQL(splitAddress[0], splitAddress[1], splitAddress[2], splitAddress[3], splitAddress[4]);
                preparedStatement.setInt(5, location);
            } else {
                preparedStatement.setInt(5, location);
            }

            preparedStatement.setInt(6, selectedItem.getID());
            result = preparedStatement.executeUpdate();
            if (result > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}

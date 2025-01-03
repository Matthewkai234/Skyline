package model;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class Users {
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private String role;

    private static final String DB_URL = "jdbc:mysql://srv1467.hstgr.io:3306/u687850034_recordings";
    private static final String DB_USER = "u687850034_mytest22";
    private static final String DB_PASSWORD = "Qwe102030@";


    public Users(String firstName, String lastName, String email, String password, String selectedRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        this.role = selectedRole;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public static boolean isEmailInDatabase(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next() && resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean updatePassword(String email, String newPassword) {
        String updateQuery = "UPDATE users SET password_hash = ? WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, email);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean save() {
        String insertQuery = "INSERT INTO users (first_name, last_name, email, password_hash, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, this.firstName);
            preparedStatement.setString(2, this.lastName);
            preparedStatement.setString(3, this.email);
            preparedStatement.setString(4, this.passwordHash);
            preparedStatement.setString(5, this.role);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

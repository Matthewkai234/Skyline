package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordFiledController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Label togglePasswordLabel;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private Label toggleConfirmPasswordLabel;

    @FXML
    private Button createAccountButton;

    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    private final String DB_URL = "jdbc:mysql://localhost:3306/UserRegistration";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "Ali692004";

    @FXML
    public void togglePasswordVisibility() {
        if (isPasswordVisible) {
            passwordTextField.setVisible(false);
            passwordField.setVisible(true);
            passwordField.setText(passwordTextField.getText());
            togglePasswordLabel.setText("Show");
            isPasswordVisible = false;
        } else {
            passwordField.setVisible(false);
            passwordTextField.setVisible(true);
            passwordTextField.setText(passwordField.getText());
            togglePasswordLabel.setText("Hide");
            isPasswordVisible = true;
        }
    }

    @FXML
    public void toggleConfirmPasswordVisibility() {
        if (isConfirmPasswordVisible) {
            confirmPasswordTextField.setVisible(false);
            confirmPasswordField.setVisible(true);
            confirmPasswordField.setText(confirmPasswordTextField.getText());
            toggleConfirmPasswordLabel.setText("Show");
            isConfirmPasswordVisible = false;
        } else {
            confirmPasswordField.setVisible(false);
            confirmPasswordTextField.setVisible(true);
            confirmPasswordTextField.setText(confirmPasswordField.getText());
            toggleConfirmPasswordLabel.setText("Hide");
            isConfirmPasswordVisible = true;
        }
    }

    @FXML
    public void createAccount() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = isPasswordVisible ? passwordTextField.getText() : passwordField.getText();
        String confirmPassword = isConfirmPasswordVisible ? confirmPasswordTextField.getText() : confirmPasswordField.getText();

        if (firstName.isEmpty() || lastName.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Missing Name", "Please enter both your first and last name.");
            return;
        }

        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
            return;
        }

        if (!isStrongPassword(password)) {
            showAlert(Alert.AlertType.WARNING, "Weak Password",
                    "Password must:\n" +
                            "- Be at least 8 characters long\n" +
                            "- Contain uppercase and lowercase letters\n" +
                            "- Include at least one number\n" +
                            "- Include at least one special character");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Password Mismatch", "Passwords do not match. Please try again.");
            return;
        }

       
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        try {
            saveToDatabase(firstName, lastName, email, hashedPassword);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Account created successfully!");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while saving your account. Please try again.");
            e.printStackTrace();
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email != null && Pattern.compile(emailRegex).matcher(email).matches();
    }

    private boolean isStrongPassword(String password) {
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
        return password != null && Pattern.compile(passwordRegex).matcher(password).matches();
    }

    private void saveToDatabase(String firstName, String lastName, String email, String hashedPassword) throws SQLException {
        String insertQuery = "INSERT INTO Users (first_name, last_name, email, password_hash) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, hashedPassword); // Save the hashed password
            preparedStatement.executeUpdate();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

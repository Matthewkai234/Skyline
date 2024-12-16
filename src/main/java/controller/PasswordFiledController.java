package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

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
    private PasswordField confirmPasswordField;

    @FXML
    private Button togglePasswordButton;

    @FXML
    private Button toggleConfirmPasswordButton;

    @FXML
    private Button createAccountButton;

    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @FXML
    void togglePasswordVisibility(ActionEvent event) {
        isPasswordVisible = !isPasswordVisible;
        togglePasswordButton.setText(isPasswordVisible ? "🙈" : "👁");
        passwordField.setPromptText(isPasswordVisible ? passwordField.getText() : "Password*");
    }

    @FXML
    void toggleConfirmPasswordVisibility(ActionEvent event) {
        isConfirmPasswordVisible = !isConfirmPasswordVisible;
        toggleConfirmPasswordButton.setText(isConfirmPasswordVisible ? "🙈" : "👁");
        confirmPasswordField.setPromptText(isConfirmPasswordVisible ? confirmPasswordField.getText() : "Confirm Password*");
    }

    @FXML
    void createAccount(ActionEvent event) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

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

        showAlert(Alert.AlertType.INFORMATION, "Success", "Account created successfully!");
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email != null && Pattern.compile(emailRegex).matcher(email).matches();
    }

    private boolean isStrongPassword(String password) {
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
        return password != null && Pattern.compile(passwordRegex).matcher(password).matches();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

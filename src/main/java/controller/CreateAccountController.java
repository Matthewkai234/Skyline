package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import model.Users;
import java.util.regex.Pattern;

public class CreateAccountController {

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

    @FXML
    private Button userButton;

    @FXML
    private Button adminButton;

    @FXML
    private Label userRoleLabel;

    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;


    private String selectedRole = "";


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
    public void setUser() {
        selectedRole = "User";
        userRoleLabel.setText("Selected Role: User");
    }


    @FXML
    public void setAdmin() {
        selectedRole = "Admin";
        userRoleLabel.setText("Selected Role: Admin");
    }


    @FXML
    public void createAccount(ActionEvent event) {
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


        if (selectedRole.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Role Selection", "Please select whether you are a User or Admin.");
            return;
        }


        Users user = new Users(firstName, lastName, email, password, selectedRole);
        if (user.save()) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Account created successfully!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while saving your account.");
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


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

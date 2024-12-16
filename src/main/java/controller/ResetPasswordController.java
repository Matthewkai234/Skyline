package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ResetPasswordController {

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private TextField newPasswordTextField;

    @FXML
    private Label toggleNewPasswordVisibility;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private Label toggleConfirmPasswordVisibility;

    @FXML
    private Button submitButton;

    @FXML
    private Label errorMessageLabel;

    private boolean isNewPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @FXML
    public void toggleNewPassword() {
        if (isNewPasswordVisible) {
            newPasswordTextField.setVisible(false);
            newPasswordField.setVisible(true);
            newPasswordField.setText(newPasswordTextField.getText());
            toggleNewPasswordVisibility.setText("Show");
            isNewPasswordVisible = false;
        } else {
            newPasswordField.setVisible(false);
            newPasswordTextField.setVisible(true);
            newPasswordTextField.setText(newPasswordField.getText());
            toggleNewPasswordVisibility.setText("Hide");
            isNewPasswordVisible = true;
        }
    }

    @FXML
    public void toggleConfirmPassword() {
        if (isConfirmPasswordVisible) {
            confirmPasswordTextField.setVisible(false);
            confirmPasswordField.setVisible(true);
            confirmPasswordField.setText(confirmPasswordTextField.getText());
            toggleConfirmPasswordVisibility.setText("Show");
            isConfirmPasswordVisible = false;
        } else {
            confirmPasswordField.setVisible(false);
            confirmPasswordTextField.setVisible(true);
            confirmPasswordTextField.setText(confirmPasswordField.getText());
            toggleConfirmPasswordVisibility.setText("Hide");
            isConfirmPasswordVisible = true;
        }
    }

    @FXML
    public void handleSubmitButtonAction() {
        String newPassword = isNewPasswordVisible ? newPasswordTextField.getText() : newPasswordField.getText();
        String confirmPassword = isConfirmPasswordVisible ? confirmPasswordTextField.getText() : confirmPasswordField.getText();

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            errorMessageLabel.setText("Both fields are required.");
            errorMessageLabel.setVisible(true);
        } else if (!newPassword.equals(confirmPassword)) {
            errorMessageLabel.setText("Passwords do not match.");
            errorMessageLabel.setVisible(true);
        } else {
            errorMessageLabel.setVisible(false);
            // Proceed with password reset logic
            System.out.println("Password reset successfully!");
        }
    }
}

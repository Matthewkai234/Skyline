    package controller;

    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.*;

    import java.io.IOException;
    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.PreparedStatement;

    import javafx.stage.Stage;
    import org.mindrot.jbcrypt.BCrypt;

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
        private String userEmail;

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }


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

                String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

                if (updatePasswordInDatabase(hashedPassword)) {
                    errorMessageLabel.setText("Password reset successfully!");
                    errorMessageLabel.setStyle("-fx-text-fill: green;");
                    errorMessageLabel.setVisible(true);

                    // Delay briefly to show the success message
                    submitButton.getScene().getWindow().hide(); // Close current window

                    // Navigate to login page
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/login_page.fxml"));
                        Parent loginPageRoot = fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Login");
                        stage.setScene(new Scene(loginPageRoot));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    errorMessageLabel.setText("Failed to update password.");
                    errorMessageLabel.setVisible(true);
                }
            }
        }

        private boolean updatePasswordInDatabase(String newPassword) {
            String dbUrl = "jdbc:mysql://localhost:3306/skyline";
            String dbUser = "root";
            String dbPassword = "root1234";
            // String updateQuery = "UPDATE users SET password_hash = ? WHERE email = 'alideraralialiali@gmail.com'"; changed it here
            String updateQuery = "UPDATE users SET password_hash = ? WHERE email = ?";
            try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                 PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

                preparedStatement.setString(1, newPassword);
                preparedStatement.setString(2, userEmail); // Set email here

                int rowsUpdated = preparedStatement.executeUpdate();
                return rowsUpdated > 0;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
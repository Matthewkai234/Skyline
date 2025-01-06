package controller;

import application.SessionManager;
import database.Users;
import database.services.UsersDAOImp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

public class LoginController {
    @FXML
    private TextField emailField;


    @FXML
    private PasswordField passwordField;

    @FXML
    private void onLoginClick() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email == null || email.trim().isEmpty()) {
            showAlert("Error", "Please enter your email.");
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            showAlert("Error", "Please enter your password.");
            return;
        }

        UsersDAOImp userDAO = new UsersDAOImp();
        Users user = userDAO.findByEmail(email);

        if (user != null) {
            boolean isPasswordMatch = BCrypt.checkpw(password, user.getPasswordHash());

            if (isPasswordMatch) {
                SessionManager.getInstance().setLoggedInUser(user);
                loadPage("main.fxml");
            } else {
                showAlert("Error", "Invalid Email or Password.");
            }
        } else {
            showAlert("Error", "Invalid Email or Password.");
        }
    }

    @FXML
    public void forgetPasswordWindow() {
        System.out.println("Forget Password button clicked!");
        loadPage("ForgotPassword.fxml");
    }

    @FXML
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadPage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) emailField.getScene().getWindow();

            // Initialize the scene with initial dimensions
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("Title");
            stage.centerOnScreen();
            stage.setMinWidth(1000);
            stage.setMinHeight(700);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load page: " + fxmlFile);
        }
    }
}
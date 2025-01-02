package controller;

import database.Users;
import database.repositories.UsersDAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;

public class LoginController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private UsersDAOImpl userService;
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

        UsersDAOImpl userDAO = new UsersDAOImpl();
        Users user = userDAO.findByEmail(email);
      if (user != null) {
         boolean isPasswordMatch = BCrypt.checkpw(password, user.getPasswordHash());

         if (isPasswordMatch) {
            String role = user.getrole();

            if (role.equals("admin")) {
               loadPage("./view/adminHome.fxml");
            } else  {
               loadPage("./view/home.fxml");
            }
         } else {
            showAlert("Error", "Invalid Email or Password.");
         }
      } else {
         showAlert("Error", "Invalid Email or Password.");
      }
    }
    @FXML
    public void forgetPasswordWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ForgotPassword.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Forgot Password");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the Forgot Password window");
        }

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Stage stage = (Stage) emailField.getScene().getWindow();
            Scene scene = new Scene(root, 600, 400);

            stage.setScene(scene);
            stage.setTitle("Title");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load page: " + fxmlFile);
        }
    }
}
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

//      if (user != null) {
//         boolean isPasswordMatch = BCrypt.checkpw(password, user.getPasswordHash());
//
//         if (isPasswordMatch) {
//            String role = user.getrole();
//
//            if (role.equals("admin")) {
//               loadPage("admin_page.fxml");
//            } else  {
//               loadPage("agent_page.fxml");
//            }
//         } else {
//            showAlert("Error", "Invalid Email or Password.");
//         }
//      } else {
//         showAlert("Error", "Invalid Email or Password.");
//      }
   }
   @FXML
   public void forgetPasswordWindow() {
//      System.out.println("Forget Password button clicked!");
//      loadPage("reset_password.fxml");

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

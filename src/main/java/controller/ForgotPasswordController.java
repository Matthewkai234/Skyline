package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Random;

public class ForgotPasswordController {

    @FXML
    private TextField emailField;

    private final String senderEmail = "alideraralialiali@gmail.com";
    private final String senderPassword = "etcs xynt jxny vrnb";
    private String verificationCode;
    private String recipientEmail;

    public void handleExitButtonAction(ActionEvent event) {
        try {
            // Load the login page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login_page.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login"); // Set the title for the login page
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the login page.");
        }
    }

    @FXML
    private void handleSendEmail() {
        recipientEmail = emailField.getText().trim(); //get the email here


        if (recipientEmail.isEmpty() || !isValidEmail(recipientEmail)) {
            showAlert("Invalid Email", "Please enter a valid email address.");
            return;
        }

        if (!isEmailInDatabase(recipientEmail)) {
            showAlert("Email Not Found", "This email dose not have an account associated with the email address.");
            return;
        }

        verificationCode = generateVerificationCode();

        if (sendEmail(recipientEmail, verificationCode)) {
            showAlert("Success", "Verification code sent successfully!");
            navigateToNextPage("/view/verification.fxml", verificationCode, recipientEmail); // add here recipientEmail
        } else {
            showAlert("Error", "Failed to send the verification code. Please try again.");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private boolean isEmailInDatabase(String email) {
        String dbUrl = "jdbc:mysql://localhost:3306/skyline";
        String dbUser = "root";
        String dbPassword = "root1234";

        String query = "SELECT COUNT(*) FROM users WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Database Error", "Failed to connect to the database.");
        }

        return false;
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    private boolean sendEmail(String recipientEmail, String verificationCode) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Password Reset Verification Code");
            message.setText("Your verification code is: " + verificationCode);

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void navigateToNextPage(String fxmlPath, String verificationCode, String recipientEmail) { //add recipientEmail here
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            VerificationController controller = loader.getController();
            controller.setSentCode(verificationCode);
            controller.setUserEmail(recipientEmail); // add the email here to the verificationController

            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the verification page.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
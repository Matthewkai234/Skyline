package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class VerificationController {

    @FXML
    private TextField verificationCodeField;

    private String sentCode;


    public void setSentCode(String verificationCode) {
        this.sentCode = verificationCode;
    }

@FXML
    private void handleVerifyCode(ActionEvent event) {
        String enteredCode = verificationCodeField.getText().trim();

        if (enteredCode.equals(sentCode)) {
            showAlert("Success", "Verification successful!");
            navigateToResetPasswordPage(event);
        } else {
            showAlert("Error", "Invalid verification code. Please try again.");
        }
    }

    @FXML
    private void navigateToResetPasswordPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ResetPassword .fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the reset password page.");
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

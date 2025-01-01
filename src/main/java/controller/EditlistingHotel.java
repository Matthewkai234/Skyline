package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class EditlistingHotel{

    @FXML private TextField hotelIdField;
    @FXML private TextField hotelNameField;
    @FXML private TextField hotelLocationField;
    @FXML private Label successMessageLabel;

    // Temporary in-memory data for the hotel
    private String hotelId;
    private String hotelName;
    private String hotelLocation;

    @FXML
    private void initialize() {
        successMessageLabel.setVisible(false);
    }

    @FXML
    private void resetFormFields(ActionEvent event) {
        hotelIdField.clear();
        hotelNameField.clear();
        hotelLocationField.clear();
        successMessageLabel.setVisible(false);
    }


    @FXML
    private void handleSaveChanges(ActionEvent event) {
        hotelId = hotelIdField.getText();
        hotelName = hotelNameField.getText();
        hotelLocation = hotelLocationField.getText();

        if (hotelId.isEmpty() || hotelName.isEmpty() || hotelLocation.isEmpty()) {
            showAlert("Validation Error", "All fields are required.");
            return;
        }

        // Display success message
        successMessageLabel.setText("Hotel details updated successfully!");
        successMessageLabel.setVisible(true);

        // Log the updated details (for demonstration)
        System.out.println("Hotel Updated: ");
        System.out.println("ID: " + hotelId);
        System.out.println("Name: " + hotelName);
        System.out.println("Location: " + hotelLocation);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

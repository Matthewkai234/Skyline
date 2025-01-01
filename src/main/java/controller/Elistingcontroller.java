package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

public class Elistingcontroller {

    @FXML private TextField flightIdField;
    @FXML private TextField flightNumberField;
    @FXML private TextField airlineField;
    @FXML private TextField destinationField;
    @FXML private DatePicker departureDateField;
    @FXML private DatePicker arrivalDateField;
    @FXML private Label successMessageLabel;

    @FXML
    private void initialize() {
        successMessageLabel.setVisible(false);
    }

    @FXML
    private void resetFormFields(ActionEvent event) {
        flightIdField.clear();
        flightNumberField.clear();
        airlineField.clear();
        destinationField.clear();
        departureDateField.setValue(null);
        arrivalDateField.setValue(null);
        successMessageLabel.setVisible(false);
    }

    @FXML
    private void handleSaveChanges(ActionEvent event) {
        String flightId = flightIdField.getText();
        String flightNumber = flightNumberField.getText();
        String airline = airlineField.getText();
        String destination = destinationField.getText();
        String departureDate = departureDateField.getValue() != null ? departureDateField.getValue().toString() : null;
        String arrivalDate = arrivalDateField.getValue() != null ? arrivalDateField.getValue().toString() : null;

        // Validate required fields
        if (flightId.isEmpty() || flightNumber.isEmpty() || airline.isEmpty() || destination.isEmpty()) {
            showAlert("Validation Error", "Flight ID, Flight Number, Airline, and Destination are required.");
            return;
        }

        // Simulate saving the data (for now, just print to console)
        System.out.println("Saving flight details:");
        System.out.println("Flight ID: " + flightId);
        System.out.println("Flight Number: " + flightNumber);
        System.out.println("Airline: " + airline);
        System.out.println("Destination: " + destination);
        System.out.println("Departure Date: " + departureDate);
        System.out.println("Arrival Date: " + arrivalDate);

        // Display success message
        successMessageLabel.setText("Flight details saved successfully!");
        successMessageLabel.setVisible(true);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

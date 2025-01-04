package controller;

import database.interfaces.BookingHotelsService;
import model.BookingHotels;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import database.repositories.BookingHotelsServiceimp;

import java.io.IOException;
import java.util.regex.Pattern;

public class BookingHotelcontroller {
    private String Location;

    public void setLocation(String location) {
        this.Location = location;
    }



    @FXML
    private TextField hotelNameField;

    public void setHotelName(String hotelName) {
        if (hotelNameField != null) {
            hotelNameField.setText(hotelName);
        }
    }






    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private ComboBox<String> countryField;

    @FXML
    private DatePicker arrivalDateField;

    @FXML
    private TextField specialRequestsField;

    private BookingHotelsService bookingHotelsService;

    public BookingHotelcontroller() {
        this.bookingHotelsService = new BookingHotelsServiceimp();
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HotelsResults.fxml"));
            Parent previousPage = loader.load();

            HotelsResultscontroller previousPageController = loader.getController();

            previousPageController.setLocation(this.Location);
            previousPageController.loadHotelData();

            Scene previousScene = new Scene(previousPage);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(previousScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleReserveButtonAction(ActionEvent event) {
        try {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String hotelName = hotelNameField.getText();

            String phoneNumber = phoneNumberField.getText();
            String country = countryField.getValue();
            String arrivalDate = arrivalDateField.getValue() != null ? arrivalDateField.getValue().toString() : "";
            String specialRequests = specialRequestsField.getText();

            if (firstName.isEmpty()) {
                showAlert("First Name is required!");
                return;
            }
            if (lastName.isEmpty()) {
                showAlert("Last Name is required!");
                return;
            }
            if (email.isEmpty()) {
                showAlert("Email is required!");
                return;
            }
            if (phoneNumber.isEmpty()) {
                showAlert("Phone Number is required!");
                return;
            }
            if (country == null || country.isEmpty()) {
                showAlert("Country is required!");
                return;
            }
            if (arrivalDate.isEmpty()) {
                showAlert("Arrival Date is required!");
                return;
            }

            if (!isValidEmail(email)) {
                showAlert("Invalid Email Address!");
                return;
            }

            if (!isValidPhoneNumber(phoneNumber)) {
                showAlert("Phone Number must be 10 digits!");
                return;
            }

            BookingHotels booking = new BookingHotels();
            booking.setFirstName(firstName);
            booking.setLastName(lastName);
            booking.setEmail(email);
            booking.setHotelName(hotelName);

            booking.setPhoneNumber(phoneNumber);
            booking.setCountry(country);
            booking.setArrivalTime(arrivalDate);
            booking.setSpecialRequests(specialRequests);

            bookingHotelsService.saveBooking(booking);

            showAlert("Booking saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("An error occurred while saving the booking.");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "\\d{10}";
        Pattern pattern =   Pattern.compile(phoneRegex);
        return pattern.matcher(phoneNumber).matches();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
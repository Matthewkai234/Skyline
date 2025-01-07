package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import model.clients_booking_hotels;
import database.services.ClientsHotelBookingDAOImp;
import database.interfaces.ClientsHotelBookingsDAO;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class BookingHotelcontroller {

    @FXML
    private TextField clientFirstNameField;
    @FXML
    private TextField clientLastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private DatePicker checkInDatePicker;
    @FXML
    private DatePicker checkOutDatePicker;
    @FXML
    private TextField numGuestsField;
    @FXML
    private ComboBox<String> roomTypeCombo;
    @FXML
    private TextField specialRequestsField;
    @FXML
    private Button bookButton;
    @FXML
    private Label messageLabel;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private ComboBox<String> countryCodeCombo;

    @FXML
    private Label emailErrorLabel;
    @FXML
    private Label phoneNumberErrorLabel;

    private final Map<String, String> countryCodeMap = new HashMap<>();
    private final ClientsHotelBookingsDAO bookingService = new ClientsHotelBookingDAOImp();
    public void initialize(){
        // Room type options
        ObservableList<String> roomTypes = FXCollections.observableArrayList("Single", "Double", "Suite", "Couples");
        roomTypeCombo.setItems(roomTypes);

        // Country code options with names
        countryCodeMap.put("+963", "Syria");
        countryCodeMap.put("+970", "Palestine");
        countryCodeMap.put("+961", "Lebanon");
        countryCodeMap.put("+962", "Jordan");
        countryCodeMap.put("+81", "Japan");
        countryCodeMap.put("+82", "South Korea");
        countryCodeMap.put("+7", "Russia");
        countryCodeMap.put("+44", "United Kingdom");
        countryCodeMap.put("+34", "Spain");
        countryCodeMap.put("+353", "Ireland");
        countryCodeMap.put("+86", "China");


        ObservableList<String> countryCodes = FXCollections.observableArrayList(countryCodeMap.keySet());
        countryCodeCombo.setItems(countryCodes);

        countryCodeCombo.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item + " " + countryCodeMap.get(item));
                }
            }
        });
        countryCodeCombo.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("Code");
                } else {
                    setText(item + " " + countryCodeMap.get(item));
                }
            }
        });

        // Number of guests input validation
        numGuestsField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numGuestsField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            // Prevent 0 input
            if(newValue.equals("0")){
                numGuestsField.setText("");
            }
        });

        phoneNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                phoneNumberField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

    }
    @FXML
    public void handleBookButton(ActionEvent event) {
        clearErrorMessages();
        String clientFirstName = clientFirstNameField.getText();
        String clientLastName = clientLastNameField.getText();
        String email = emailField.getText();
        LocalDate checkInDate = checkInDatePicker.getValue();
        LocalDate checkOutDate = checkOutDatePicker.getValue();
        String numGuests = numGuestsField.getText();
        String roomType = roomTypeCombo.getValue();
        String specialRequests = specialRequestsField.getText();
        String phoneNumber = phoneNumberField.getText();
        String countryCode = countryCodeCombo.getValue();


        if (clientFirstName.isEmpty() || clientLastName.isEmpty() || checkInDate == null || checkOutDate == null || numGuests.isEmpty()|| roomType == null || countryCode == null || phoneNumber.isEmpty()){
            messageLabel.setText("Please fill out all required fields ");
            return;
        }

        if (!isValidEmail(email)) {
            emailErrorLabel.setText("Invalid email format");
            emailErrorLabel.setTextFill(Color.RED);
            return;
        }
        if (!isValidPhoneNumber(phoneNumber)) {
            phoneNumberErrorLabel.setText("Invalid phone number format");
            phoneNumberErrorLabel.setTextFill(Color.RED);
            return;
        }
        try {
            int numberOfGuests = Integer.parseInt(numGuests);
            // Create a new booking object
            clients_booking_hotels booking = new clients_booking_hotels();
            booking.setFirstName(clientFirstName);
            booking.setLastName(clientLastName);
            booking.setEmail(email);
            booking.setCheckInDate(checkInDate);
            booking.setCheckOutDate(checkOutDate);
            booking.setNumGuests(numberOfGuests);
            booking.setRoomType(roomType);
            booking.setSpecialRequests(specialRequests);
            booking.setPhoneNumber(phoneNumber);
            booking.setCountryCode(countryCode);

            // Save the booking information
            bookingService.saveBooking(booking);

            messageLabel.setText("Hotel Booked Successfully!");
            clearFields();
        } catch (NumberFormatException e) {
            messageLabel.setText("Invalid input for number of guests, please provide a number");
        }
    }

    private void clearFields(){
        clientFirstNameField.clear();
        clientLastNameField.clear();
        emailField.clear();
        checkInDatePicker.setValue(null);
        checkOutDatePicker.setValue(null);
        numGuestsField.clear();
        roomTypeCombo.setValue(null);
        specialRequestsField.clear();
        phoneNumberField.clear();
        countryCodeCombo.setValue(null);
        clearErrorMessages();

    }
    private void clearErrorMessages(){
        emailErrorLabel.setText("");
        phoneNumberErrorLabel.setText("");
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pattern.matcher(email).matches();
    }
    private boolean isValidPhoneNumber(String phoneNumber) {
        // Assuming a simple numeric phone number format with a length of at least 7
        return phoneNumber.matches("\\d{7,}");
    }
}
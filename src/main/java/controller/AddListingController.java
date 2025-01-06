package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.AdminListingFlightModel;
import model.Hotels;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class AddListingController {

    @FXML
    private ComboBox<String> listingTypeComboBox;
    @FXML
    private TextField flightIdField;
    @FXML
    private TextField airlineField;
    @FXML
    private TextField destinationField;
    @FXML
    private DatePicker departureDateField;
    @FXML
    private DatePicker arrivalDateField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField hotelPriceField;
    @FXML
    private TextField hotelRateField;

    @FXML
    private Label flightIdErrorLabel;
    @FXML
    private Label airlineErrorLabel;
    @FXML
    private Label destinationErrorLabel;
    @FXML
    private Label departureDateErrorLabel;
    @FXML
    private Label arrivalDateErrorLabel;
    @FXML
    private Label nameErrorLabel;
    @FXML
    private Label locationErrorLabel;
    @FXML
    private Label hotelPriceErrorLabel;
    @FXML
    private Label hotelRateErrorLabel;

    @FXML
    private AnchorPane fieldsPane;
    @FXML
    private VBox flightFieldsContainer;
    @FXML
    private VBox hotelFieldsContainer;

    private ListingsController parentController;
    private SessionFactory sessionFactory;

    @FXML
    public void initialize() {
        listingTypeComboBox.getItems().addAll("Hotel", "Flight");
        listingTypeComboBox.setValue("Flight"); // Set default value
        listingTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> toggleFields(newValue));
        toggleFields("Flight");

        try {
            Configuration config = new Configuration().configure("hibernate.cfg.xml")
                    .addAnnotatedClass(AdminListingFlightModel.class)
                    .addAnnotatedClass(Hotels.class);
            sessionFactory = config.buildSessionFactory();

            if (sessionFactory == null) {
                System.err.println("SessionFactory is null. Initialization failed.");
            } else {
                System.out.println("SessionFactory initialized successfully.");
            }
        } catch (Exception ex) {
            System.err.println("Failed to create session factory: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void toggleFields(String listingType) {
        clearErrorMessages();
        boolean isHotel = "Hotel".equalsIgnoreCase(listingType);
        flightFieldsContainer.setVisible(!isHotel);
        hotelFieldsContainer.setVisible(isHotel);
    }

    private boolean validateInput(String selectedType) {
        clearErrorMessages();
        boolean isValid = true;

        if ("Hotel".equalsIgnoreCase(selectedType)) {
            if (nameField.getText().trim().isEmpty()) {
                nameErrorLabel.setText("Hotel name is required");
                isValid = false;
            }
            if (locationField.getText().trim().isEmpty()) {
                locationErrorLabel.setText("Location is required");
                isValid = false;
            }
            if (hotelPriceField.getText().trim().isEmpty() || !hotelPriceField.getText().matches("\\d+(\\.\\d+)?")) {
                hotelPriceErrorLabel.setText("Valid price is required");
                isValid = false;
            }
            if (hotelRateField.getText().trim().isEmpty() || !hotelRateField.getText().matches("\\d+(\\.\\d+)?")) {
                hotelRateErrorLabel.setText("Valid rate is required");
                isValid = false;
            }
        } else if ("Flight".equalsIgnoreCase(selectedType)) {
            if (flightIdField.getText().trim().isEmpty()) {
                flightIdErrorLabel.setText("Flight ID is required");
                isValid = false;
            }
            if (airlineField.getText().trim().isEmpty()) {
                airlineErrorLabel.setText("Airline is required");
                isValid = false;
            }
            if (destinationField.getText().trim().isEmpty()) {
                destinationErrorLabel.setText("Destination is required");
                isValid = false;
            }
            if (departureDateField.getValue() == null) {
                departureDateErrorLabel.setText("Departure date is required");
                isValid = false;
            }
            if (arrivalDateField.getValue() == null) {
                arrivalDateErrorLabel.setText("Arrival date is required");
                isValid = false;
            }
        }
        return isValid;
    }

    @FXML
    private void addListing() {
        String selectedType = listingTypeComboBox.getValue();
        if (selectedType == null || selectedType.isEmpty()) {
            showAlert("Error", "Please select a listing type.");
            return;
        }
        if (!validateInput(selectedType)) {
            return;
        }

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            if ("Hotel".equalsIgnoreCase(selectedType)) {
                Hotels hotel = new Hotels();
                hotel.setHotelName(nameField.getText());
                hotel.setLocation(locationField.getText());
                hotel.setHotelPrice(Double.parseDouble(hotelPriceField.getText()));
                hotel.setHotelRate(Float.parseFloat(hotelRateField.getText()));

                System.out.println("Saving hotel: " + hotel);
                session.save(hotel); // ID will be auto-generated
                System.out.println("Hotel saved successfully with ID: " + hotel.getHotelId());

                if (parentController != null) {
                    parentController.loadHotelData();
                }
            } else if ("Flight".equalsIgnoreCase(selectedType)) {
                AdminListingFlightModel flight = new AdminListingFlightModel();
                flight.setFlightNumber(flightIdField.getText());
                flight.setAirline(airlineField.getText());
                flight.setDestination(destinationField.getText());
                flight.setDepartureDate(departureDateField.getValue().toString());
                flight.setArrivalDate(arrivalDateField.getValue().toString());

                System.out.println("Saving flight: " + flight);
                session.save(flight);
                System.out.println("Flight saved successfully");

                if (parentController != null) {
                    parentController.loadFlightData();
                }
            }
            session.getTransaction().commit();
            closeWindow();
        } catch (Exception e) {
            showAlert("Error", "Failed to save listing: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void clearErrorMessages() {
        flightIdErrorLabel.setText("");
        airlineErrorLabel.setText("");
        destinationErrorLabel.setText("");
        departureDateErrorLabel.setText("");
        arrivalDateErrorLabel.setText("");
        nameErrorLabel.setText("");
        locationErrorLabel.setText("");
        hotelPriceErrorLabel.setText("");
        hotelRateErrorLabel.setText("");
    }
    private void closeWindow() {
        Stage stage = (Stage) listingTypeComboBox.getScene().getWindow();
        stage.close();
    }

    public void setParentController(ListingsController parentController) {
        this.parentController = parentController;
    }
}
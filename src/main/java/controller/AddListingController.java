package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.AdminListingFlightModel;
import model.HotelsModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
    private TextField takeoffCountryField;
    @FXML
    private TextField landingCountryField;

    @FXML
    private TextField nameField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField hotelPriceField;
    @FXML
    private TextField hotelRateField;

    @FXML
    private VBox flightFieldsContainer;
    @FXML
    private VBox hotelFieldsContainer;

    private ListingsController parentController;
    private SessionFactory sessionFactory;

    @FXML
    public void initialize() {
        listingTypeComboBox.getItems().addAll("Hotel", "Flight");
        listingTypeComboBox.setValue("Flight"); // Default to Flight
        listingTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> toggleFields(newValue));
        toggleFields("Flight");

        try {
            Configuration config = new Configuration().configure("hibernate.cfg.xml")
                    .addAnnotatedClass(AdminListingFlightModel.class)
                    .addAnnotatedClass(HotelsModel.class);
            sessionFactory = config.buildSessionFactory();
        } catch (Exception ex) {
            showAlert("Error", "Failed to initialize database connection.");
            ex.printStackTrace();
        }
    }

    private void toggleFields(String listingType) {
        boolean isHotel = "Hotel".equalsIgnoreCase(listingType);
        flightFieldsContainer.setVisible(!isHotel);
        hotelFieldsContainer.setVisible(isHotel);
    }

    @FXML
    private void addListing() {
        String selectedType = listingTypeComboBox.getValue();
        if (!validateInput(selectedType)) {
            return;
        }

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            if ("Flight".equalsIgnoreCase(selectedType)) {
                AdminListingFlightModel flight = new AdminListingFlightModel();
                flight.setFlightNumber(flightIdField.getText());
                flight.setAirline(airlineField.getText());
                flight.setDestination(destinationField.getText());
                flight.setDepartureDate(departureDateField.getValue().toString());
                flight.setArrivalDate(arrivalDateField.getValue().toString());
                flight.setPrice((int) Double.parseDouble(priceField.getText()));
                flight.setTakeoffCountry(takeoffCountryField.getText());
                flight.setLandingCountry(landingCountryField.getText());
                session.save(flight);
                parentController.loadFlightData(); // Refresh parent flight data
                showAlert("Success", "Flight added successfully.");
            } else if ("Hotel".equalsIgnoreCase(selectedType)) {
                HotelsModel hotel = new HotelsModel();
                hotel.setHotelName(nameField.getText());
                hotel.setLocation(locationField.getText());
                hotel.setHotelPrice(Double.parseDouble(hotelPriceField.getText()));
                hotel.setHotelRate(Float.parseFloat(hotelRateField.getText()));

                session.save(hotel);
                parentController.loadHotelData(); // Refresh parent hotel data
                showAlert("Success", "Hotel added successfully.");
            }

            session.getTransaction().commit();
            closeWindow();
        } catch (Exception e) {
            showAlert("Error", "Failed to save listing. Please check your input.");
            e.printStackTrace();
        }
    }

    private boolean validateInput(String selectedType) {
        if ("Flight".equalsIgnoreCase(selectedType)) {
            if (flightIdField.getText().isEmpty() || airlineField.getText().isEmpty() || destinationField.getText().isEmpty()
                    || departureDateField.getValue() == null || arrivalDateField.getValue() == null || priceField.getText().isEmpty()
                    || takeoffCountryField.getText().isEmpty() || landingCountryField.getText().isEmpty()) {
                showAlert("Validation Error", "All flight fields are required.");
                return false;
            }
            try {
                Double.parseDouble(priceField.getText());
            } catch (NumberFormatException e) {
                showAlert("Validation Error", "Flight price must be a valid number.");
                return false;
            }
        } else if ("Hotel".equalsIgnoreCase(selectedType)) {
            if (nameField.getText().isEmpty() || locationField.getText().isEmpty()
                    || hotelPriceField.getText().isEmpty() || hotelRateField.getText().isEmpty()) {
                showAlert("Validation Error", "All hotel fields are required.");
                return false;
            }
            try {
                Double.parseDouble(hotelPriceField.getText());
                Float.parseFloat(hotelRateField.getText());
            } catch (NumberFormatException e) {
                showAlert("Validation Error", "Hotel price and rate must be valid numbers.");
                return false;
            }
        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) listingTypeComboBox.getScene().getWindow();
        stage.close();
    }

    public void setParentController(ListingsController parentController) {
        this.parentController = parentController;
    }
}

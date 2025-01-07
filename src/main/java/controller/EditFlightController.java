package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.AdminListingFlightModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditFlightController {

    @FXML
    private TextField flightNumberField;

    @FXML
    private TextField airlineField;

    @FXML
    private TextField takeoffCountryField;

    @FXML
    private TextField landingCountryField;

    @FXML
    private TextField priceField;

    @FXML
    private DatePicker departureDatePicker;

    @FXML
    private DatePicker arrivalDatePicker;

    @FXML
    private Button saveButton;

    private AdminListingFlightModel flight;
    private ListingsController parentController;

    public void setParentController(ListingsController parentController) {
        this.parentController = parentController;
    }

    public void setFlight(AdminListingFlightModel flight) {
        this.flight = flight;
        loadFlightData(); // Populate the form with flight details
    }

    @FXML
    public void initialize() {
        saveButton.setOnAction(event -> saveEdit());
    }

    private void loadFlightData() {
        if (flight != null) {
            flightNumberField.setText(flight.getFlightNumber());
            airlineField.setText(flight.getAirline());
            takeoffCountryField.setText(flight.getTakeoffCountry());
            landingCountryField.setText(flight.getLandingCountry());
            priceField.setText(String.valueOf(flight.getPrice()));

            if (flight.getDepartureDate() != null && !flight.getDepartureDate().isEmpty()) {
                departureDatePicker.setValue(LocalDate.parse(flight.getDepartureDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            if (flight.getArrivalDate() != null && !flight.getArrivalDate().isEmpty()) {
                arrivalDatePicker.setValue(LocalDate.parse(flight.getArrivalDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        }
    }

    private void saveEdit() {
        if (validateInput()) {
            updateFlight();
            closeWindow();
        }
    }

    private boolean validateInput() {
        if (flightNumberField.getText().isEmpty() || airlineField.getText().isEmpty() ||
                takeoffCountryField.getText().isEmpty() || landingCountryField.getText().isEmpty() ||
                priceField.getText().isEmpty()) {
            System.err.println("All fields are required.");
            return false;
        }
        try {
            Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            System.err.println("Price must be a valid number.");
            return false;
        }
        return true;
    }

    private void updateFlight() {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            flight.setFlightNumber(flightNumberField.getText());
            flight.setAirline(airlineField.getText());
            flight.setTakeoffCountry(takeoffCountryField.getText());
            flight.setLandingCountry(landingCountryField.getText());
            flight.setPrice((int) Double.parseDouble(priceField.getText()));

            if (departureDatePicker.getValue() != null) {
                flight.setDepartureDate(departureDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            if (arrivalDatePicker.getValue() != null) {
                flight.setArrivalDate(arrivalDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }

            session.update(flight);
            transaction.commit();

            parentController.loadFlightData(); // Refresh the flight data in the parent controller
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}

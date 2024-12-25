package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Flight;
import model.Hotel;
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
    private TextField hotelIdField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField locationField;
    @FXML
    private Label flightIdLabel;
    @FXML
    private Label airlineLabel;
    @FXML
    private Label destinationLabel;
    @FXML
    private Label departureDateLabel;
    @FXML
    private Label arrivalDateLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label hotelIdLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label locationLabel;
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
                    .addAnnotatedClass(Hotel.class)
                    .addAnnotatedClass(Flight.class);
            sessionFactory = config.buildSessionFactory();
        } catch (Exception ex) {
            System.out.println("Failed to create session factory: " + ex.getMessage());
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
        if (selectedType == null || selectedType.isEmpty()) {
            showAlert("Error", "Please select a listing type.");
            return;
        }

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            if ("Hotel".equalsIgnoreCase(selectedType)) {
                Hotel hotel = new Hotel();
                hotel.setHotelId(Integer.parseInt(hotelIdField.getText()));
                hotel.setName(nameField.getText());
                hotel.setLocation(locationField.getText());
                session.save(hotel);
                if (parentController != null) {
                    parentController.loadHotelData();
                }
            } else if ("Flight".equalsIgnoreCase(selectedType)) {
                Flight flight = new Flight();
                flight.setFlightNumber(flightIdField.getText());
                flight.setAirline(airlineField.getText());
                flight.setDestination(destinationField.getText());
                LocalDate departureDate = departureDateField.getValue();
                if(departureDate != null){
                    flight.setDepartureDate(departureDate.toString());
                }
                LocalDate arrivalDate = arrivalDateField.getValue();
                if(arrivalDate != null){
                    flight.setArrivalDate(arrivalDate.toString());
                }
                session.save(flight);
                if (parentController != null) {
                    parentController.loadFlightData();
                }
            }
            session.getTransaction().commit();
            Stage stage = (Stage) listingTypeComboBox.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            showAlert("Error", "Failed to save listing: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setParentController(ListingsController parentController) {
        this.parentController = parentController;
    }
}
package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Flight;
import model.Hotel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class EditlistingController {


    @FXML
    private TextField flightNumberField;
    @FXML
    private TextField airlineField;
    @FXML
    private TextField destinationField;
    @FXML
    private TextField departureDateField;
    @FXML
    private TextField arrivalDateField;
    @FXML
    private TextField hotelIdField;
    @FXML
    private TextField hotelNameField;
    @FXML
    private TextField locationField;
    @FXML
    private Button saveButton;
    private Flight flight;
    private Hotel hotel;

    private ListingsController parentController;
    private SessionFactory sessionFactory;


    public void setParentController(ListingsController parentController) {
        this.parentController = parentController;
    }

    public void setFlight(Flight flight){
        this.flight = flight;
        loadFlightData();
    }
    public void setHotel(Hotel hotel){
        this.hotel = hotel;
        loadHotelData();
    }
    @FXML
    public void initialize() {
        try{
            Configuration config = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Flight.class).addAnnotatedClass(Hotel.class);
            sessionFactory = config.buildSessionFactory();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            System.out.println("failed to create the session");
        }
        saveButton.setOnAction(event -> saveEdit());
    }
    private void loadFlightData(){
        if(flight !=null){
            flightNumberField.setText(flight.getFlightNumber());
            airlineField.setText(flight.getAirline());
            destinationField.setText(flight.getDestination());
            departureDateField.setText(flight.getDepartureDate());
            arrivalDateField.setText(flight.getArrivalDate());
        }
    }
    private void loadHotelData(){
        if(hotel !=null){
            hotelIdField.setText(String.valueOf(hotel.getHotelId()));
            hotelNameField.setText(hotel.getName());
            locationField.setText(hotel.getLocation());
        }
    }
    private void saveEdit() {
        if (flight != null) {
            updateFlight();
        } else if (hotel != null) {
            updateHotel();
        }
        closeWindow();

    }

    private void updateFlight() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            flight.setFlightNumber(flightNumberField.getText());
            flight.setAirline(airlineField.getText());
            flight.setDestination(destinationField.getText());
            flight.setDepartureDate(departureDateField.getText());
            flight.setArrivalDate(arrivalDateField.getText());

            session.update(flight);
            transaction.commit();
            parentController.loadFlightData(); // Refresh the table view in the parent controller
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateHotel() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            hotel.setName(hotelNameField.getText());
            hotel.setLocation(locationField.getText());
            session.update(hotel);
            transaction.commit();
            parentController.loadHotelData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void closeWindow(){
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
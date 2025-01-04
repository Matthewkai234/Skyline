package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.AdminListingFlightModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditFlightController {


    @FXML
    private TextField flightNumberField;
    @FXML
    private TextField airlineField;
    @FXML
    private TextField destinationField;
    @FXML
    private DatePicker departureDatePicker;
    @FXML
    private DatePicker arrivalDatePicker;
    @FXML
    private Button saveButton;
    private AdminListingFlightModel flight;
    private ListingsController parentController;
    private SessionFactory sessionFactory;


    public void setParentController(ListingsController parentController) {
        this.parentController = parentController;
    }

    public void setFlight(AdminListingFlightModel flight) {
        this.flight = flight;
        loadFlightData();
    }


    @FXML
    public void initialize() {
        try {
            Configuration config = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(AdminListingFlightModel.class);
            sessionFactory = config.buildSessionFactory();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("failed to create the session");
        }
        saveButton.setOnAction(event -> saveEdit());
    }

    private void loadFlightData() {
        if (flight != null) {
            flightNumberField.setText(flight.getFlightNumber());
            airlineField.setText(flight.getAirline());
            destinationField.setText(flight.getDestination());
            // Load date into the DatePicker if the date exists
            if(flight.getDepartureDate() != null && !flight.getDepartureDate().isEmpty()){
                departureDatePicker.setValue(LocalDate.parse(flight.getDepartureDate(),DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            if(flight.getArrivalDate() != null && !flight.getArrivalDate().isEmpty()){
                arrivalDatePicker.setValue(LocalDate.parse(flight.getArrivalDate(),DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        }
    }

    private void saveEdit() {
        updateFlight();
        closeWindow();

    }

    private void updateFlight() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            flight.setFlightNumber(flightNumberField.getText());
            flight.setAirline(airlineField.getText());
            flight.setDestination(destinationField.getText());

            if(departureDatePicker.getValue() != null){
                flight.setDepartureDate(departureDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            if(arrivalDatePicker.getValue() != null){
                flight.setArrivalDate(arrivalDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }


            session.update(flight);
            transaction.commit();
            parentController.loadFlightData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
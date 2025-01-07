package controller;

import database.Flight;
import database.services.*;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.FlightsBookingModel;
import model.HotelsBookingModel;
import model.LatestBookingModel;
import model.clients_booking_flights;
import java.time.LocalDate;

public class FlightBookingController {

    @FXML
    private TextField userName;

    @FXML
    private DatePicker StartDate;

    @FXML
    private DatePicker ArriveDate;

    @FXML
    private TextField destination;

    @FXML
    private TextField AirLine;

    @FXML
    private TextField status;

    @FXML
    private TextField takeOff;

    @FXML
    private Label messageLabel;

    private int flightId;

    private final SearshFlightDOAImp searchFlightDAO = new SearshFlightDOAImp();

    public void initFlightDetails(int flightId) {
        this.flightId = flightId;

        Flight flight = searchFlightDAO.getFlight(flightId);

        if (flight != null) {
            System.out.println("Flight found: " + flight.getAirline());

            StartDate.setValue(flight.getStartDate());
            ArriveDate.setValue(flight.getArriveDate());
            AirLine.setText(flight.getAirline());
            takeOff.setText(flight.getTakeoffContry());
            destination.setText(flight.getLandingCountry());
            status.setText("Pending");
            status.setEditable(false);
        } else {
            System.out.println("No flight found with ID: " + flightId);
        }
    }

    @FXML
    public void Booking() {
        String userNameValue = userName.getText();
        LocalDate startDateValue = StartDate.getValue();
        LocalDate arriveDateValue = ArriveDate.getValue();
        String destinationValue = destination.getText();
        String airlineValue = AirLine.getText();
        String statusValue = status.getText();
        String takeOffValue = takeOff.getText();

        if (userNameValue.isEmpty() || startDateValue == null || arriveDateValue == null ||
                destinationValue.isEmpty() || airlineValue.isEmpty() || statusValue.isEmpty()) {
            showMessage("Please fill all fields.", "error");
            return;
        }

        FlightsBookingModel.Status bookingStatus;
        try {
            bookingStatus = FlightsBookingModel.Status.valueOf(statusValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            showMessage("Invalid status value. Use PENDING, CANCELLED, or BOOKED.", "error");
            return;
        }

        clients_booking_flights booking = new clients_booking_flights();
        booking.setBookingDate(startDateValue);
        booking.setAirline(airlineValue);
        booking.setDestination(destinationValue);
        booking.setStatus(bookingStatus.toString());
        booking.setCustomerName(userNameValue);
        booking.setTakeOff(takeOffValue);


        try {
            clients_booking_flightsDOAImp bookingDAO = new clients_booking_flightsDOAImp();
            bookingDAO.AddBooking(booking);

            FlightsBookingDAOImp flightsBookingDAOImp = new FlightsBookingDAOImp();
            flightsBookingDAOImp.insert(new FlightsBookingModel(LocalDate.now(), userNameValue, takeOffValue,destinationValue, airlineValue, FlightsBookingModel.Status.PENDING));

            LatestBookingDAOImp latestBookingDAOImp = new LatestBookingDAOImp();
            latestBookingDAOImp.insert( new LatestBookingModel(userNameValue, "Flight", airlineValue));
            showMessage("Booking successfully added!", "success");
        } catch (Exception e) {
            showMessage("Failed to book the flight. Please try again.", "error");
            e.printStackTrace();
        }
    }

    private void showMessage(String message, String type) {
        messageLabel.setText(message);
        if ("success".equalsIgnoreCase(type)) {
            messageLabel.setStyle("-fx-text-fill: green;");
        } else if ("error".equalsIgnoreCase(type)) {
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }
}
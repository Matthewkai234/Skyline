package controller;

import database.Flight;
import database.services.SearshFlightDOAImp;
import database.services.FlightsBookingDAOImp;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.FlightsBookingModel;

import java.time.LocalDate;

public class FlightBookingController {

    @FXML
    private TextField userName;

    @FXML
    private TextField StartDate;

    @FXML
    private TextField ArriveDate;

    @FXML
    private TextField destination;

    @FXML
    private TextField AirLine;

    @FXML
    private TextField status;

    private int flightId;

    private final SearshFlightDOAImp searchFlightDAO = new SearshFlightDOAImp();

    public void initFlightDetails(int flightId) {
        this.flightId = flightId;

        Flight flight = searchFlightDAO.getFlight(flightId);

        System.out.println("ddd = = = = "+flight.getStartDate());


        if (flight != null) {
            System.out.println("Flight found: " + flight.getAirline());

            StartDate.setText(flight.getStartDate().toString());
            ArriveDate.setText(flight.getArriveDate().toString());
            AirLine.setText(flight.getAirline());
            destination.setText(flight.getTakeoffContry());
            status.setText("Pending");
        }



    }

    @FXML
    public void Booking() {
        String userNameValue = userName.getText();
        String startDateValue = StartDate.getText();
        String arriveDateValue = ArriveDate.getText();
        String destinationValue = destination.getText();
        String airlineValue = AirLine.getText();
        String statusValue = status.getText();


        if (userNameValue.isEmpty() || startDateValue.isEmpty() || arriveDateValue.isEmpty() ||
                destinationValue.isEmpty() || airlineValue.isEmpty() || statusValue.isEmpty()) {
            return;
        }

        LocalDate startDate;
        LocalDate arriveDate;

        try {
            startDate = LocalDate.parse(startDateValue);
            arriveDate = LocalDate.parse(arriveDateValue);
        } catch (Exception e) {
            return;
        }

        FlightsBookingModel.Status bookingStatus;
        try {
            bookingStatus = FlightsBookingModel.Status.valueOf(statusValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            return;
        }

        FlightsBookingModel booking = new FlightsBookingModel();
        booking.setBookingDate(startDate);
        booking.setAirline(airlineValue);
        booking.setDestination(destinationValue);
        booking.setStatus(bookingStatus);
        booking.setCustomerName(userNameValue);
        booking.setTakeOff("aaa");



        FlightsBookingDAOImp bookingDAO = new FlightsBookingDAOImp();
        bookingDAO.insert(booking);

    }
}

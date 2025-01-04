package controller;

import application.SkylineApplication;
import database.Flight;
import database.services.SearshFlightDOAImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.io.IOException;

public class ReviewForFlightController {
    SearshFlightDOAImp searchFlightDAOImp = new SearshFlightDOAImp();

    @FXML
    private Text start;
    @FXML
    private Text from;
    @FXML
    private Text End;
    @FXML
    private Text to;
    @FXML
    private Text durationTrip;
    @FXML
    private Text airlineSummary;
    @FXML
    private Text price;
    @FXML
    private Text finalPrice;

    private int selectedFlightId;

    public void setFlightId(int flightId) {
        this.selectedFlightId = flightId;
        System.out.println("Flight ID: " + flightId);

        Flight flight = searchFlightDAOImp.getFlight(flightId);
        if (flight != null) {
            System.out.println("Price: " + flight.getPrice());

            airlineSummary.setText(flight.getAirline());
            start.setText(flight.getStartDate().toString());
            from.setText(flight.getTakeoffContry());
            End.setText(flight.getArriveDate().toString());
            to.setText(flight.getLandingCountry());
            airlineSummary.setText(flight.getAirline());
            price.setText(flight.getPrice() + "");
          //  finalPrice.setText(flight.getPrice() + "");
        } else {
            System.out.println("Flight not found!");
        }
    }

    @FXML
    protected void handelBookBtn(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SkylineApplication.class.getResource("/view/ClientBooking.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            FlightBookingController bookingController = fxmlLoader.getController();
            bookingController.initFlightDetails(selectedFlightId);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Booking Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

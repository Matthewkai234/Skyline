package controller;
import application.SkylineApplication;
import model.FlightModel;
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
    SearshFlightDOAImp searshFlightDOAImp = new SearshFlightDOAImp();

    @FXML
    private Text Airline;
    public Text start;
    public Text from;
    public Text End;
    public Text to;
    public Text durationTrip;
    public Text AirLineSymary;
    public Text price;
    public Text finalPrise;




    public void setFlightId(int flightId) {

        System.out.println(flightId);


        FlightModel flight = searshFlightDOAImp.getFlight(flightId);
        System.out.println(flight.getPrice());

        Airline.setText(flight.getAirline());
        start.setText(flight.getStartDate().toString());
        from.setText(flight.getTakeoffContry());
        End.setText(flight.getArriveDate().toString());
        to.setText(flight.getLandingCountry());
        durationTrip.setText("100");
        AirLineSymary.setText(flight.getAirline());
        price.setText(flight.getPrice()+"");
        finalPrise.setText(flight.getPrice()+"");




    }



    @FXML
    protected void handelBookBtn(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SkylineApplication.class.getResource("/view/ClientFlightBooking.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Home Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
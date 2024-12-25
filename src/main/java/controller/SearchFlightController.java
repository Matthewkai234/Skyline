package controller;

import application.SkylineApplication;
import database.Flight;
import database.repositories.SearshFlightDOAImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SearchFlightController {

    @FXML
    private TextField fromContry;

    @FXML
    private TextField toContry;

    @FXML
    private DatePicker dateFlight;

    SearshFlightDOAImp searshFlightDOAImp = new SearshFlightDOAImp();

    @FXML
    private TableView<Flight> Table;

    @FXML
    protected void handelSearchBtn(ActionEvent event) {
        String from = fromContry.getText();
        String to = toContry.getText();
        String date = dateFlight.getValue() != null ? dateFlight.getValue().toString() : "Not Selected";

        System.out.println("from = " + from);
        System.out.println("to = " + to);
        System.out.println("date = " + date);

        List<Flight> flights = searshFlightDOAImp.searchFlights(from, to);

        System.out.println("befor send = " + flights);

        flights.stream().forEach(flight -> {
            System.out.println("Flight ID: " + flight.getFlightId());
            System.out.println("Takeoff Country: " + flight.getTakeoffContry());
            System.out.println("Landing Country: " + flight.getLandingCountry());
            System.out.println("-----------------------------------");
        });

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SkylineApplication.class.getResource("/view/flightSearshResultTaple.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            FlightSearshResultTapleController resultController = fxmlLoader.getController();

            resultController.setFlightData(flights);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Flight Search Results");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

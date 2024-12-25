package controller;

import database.Flight;
import database.repositories.SearshFlightDOAImp;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class FlightSearshResultTapleController {
    @FXML
    private TableColumn<Flight, Integer> FlightId;

    @FXML
    private TableColumn<Flight, String> takeoffContry;

    @FXML
    private TableColumn<Flight, String> LandingCountry;

    @FXML
    private TableColumn<Flight, Integer> price;

    @FXML
    private TableColumn<Flight, String> Data;

    @FXML
    private TableColumn<Flight,String> AirLine;

    // filter

    @FXML
    private TextField StartPrice;

    @FXML
    private TextField EndPrice;

    @FXML
    private TextField Airline;




    @FXML
    private TableView<Flight> Table;

    private final ObservableList<Flight> flightList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        FlightId.setCellValueFactory(new PropertyValueFactory<>("flightId"));
        takeoffContry.setCellValueFactory(new PropertyValueFactory<>("takeoffContry"));
        LandingCountry.setCellValueFactory(new PropertyValueFactory<>("landingCountry"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        AirLine.setCellValueFactory(new PropertyValueFactory<>("Airline"));

        Data.setCellValueFactory(cellData -> {
            if (cellData.getValue().getStartDate() != null) {
                return new SimpleStringProperty(cellData.getValue().getStartDate().toString());
            } else {
                return new SimpleStringProperty("N/A");
            }
        });
    }

    public void setFlightData(List<Flight> flights) {
        flightList.clear();
        flightList.addAll(flights);

        flights.forEach(flight -> {
            System.out.println("Flight ID: " + flight.getFlightId());
            System.out.println("Takeoff Country: " + flight.getTakeoffContry());
            System.out.println("Landing Country: " + flight.getLandingCountry());
            System.out.println("-----------------------------------");
        });

        Table.setItems(flightList);
    }

    @FXML
    void onTableRowClick() {
        Flight selectedFlight = Table.getSelectionModel().getSelectedItem();

        if (selectedFlight != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReviewForFlight.fxml"));
                Parent root = loader.load();

                ReviewForFlightController flightDetailsController = loader.getController();
                flightDetailsController.setFlightId(selectedFlight.getFlightId());

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Flight Details");
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No flight selected!");
        }
    }

    @FXML
    void FilterData(){

        System.out.println("Filter Data Func");

        SearshFlightDOAImp searshFlightDOAImp = new SearshFlightDOAImp();

        System.out.println("Filter Data Func");

        String startPriceText = StartPrice.getText();
        String endPriceText = EndPrice.getText();

        String airline = Airline.getText();
        Integer startPrice = (startPriceText.isEmpty()) ? null : Integer.parseInt(startPriceText);
        Integer endPrice = (endPriceText.isEmpty()) ? null : Integer.parseInt(endPriceText);

        System.out.println("Start Price: " + startPrice);
        System.out.println("End Price: " + endPrice);
        System.out.println("Airline: " + airline);

        List<Flight> Felterdflights = searshFlightDOAImp.Filter("USA","Canada",startPrice, endPrice, airline);

        System.out.println(Felterdflights.size());


        flightList.clear();
        flightList.addAll(Felterdflights);

        Table.setItems(flightList);


    }
}
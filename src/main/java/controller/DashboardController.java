package controller;

import model.FlightsBookingModel;
import model.HotelsBookingModel;
import database.services.FlightsBookingDAOImp;
import database.services.HotelsBookingDAOImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DashboardController implements Initializable {


    @FXML
    private Label airBookingLabel;

    @FXML
    private Label hotelBookingLabel;

    @FXML
    private BarChart<String, Number> bookingChart;

    @FXML
    private ListView<Object> recentBookingsList;

    private FlightsBookingDAOImp flightsBookingRepository = new FlightsBookingDAOImp();
    private HotelsBookingDAOImp hotelsBookingRepository = new HotelsBookingDAOImp();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTotalBookings();
        loadBookingChart();
        loadRecentBookings();
        setupRecentBookingsListView();
    }
    private void loadTotalBookings() {
        long totalFlights = flightsBookingRepository.getAll().size();
        long totalHotels = hotelsBookingRepository.getAll().size();

        airBookingLabel.setText(String.valueOf(totalFlights));
        hotelBookingLabel.setText(String.valueOf(totalHotels));
    }

    private void loadBookingChart() {
        // Prepare data for the chart
        ObservableList<FlightsBookingModel> flightsBookings = FXCollections.observableArrayList(flightsBookingRepository.getAll());
        ObservableList<HotelsBookingModel> hotelsBookings = FXCollections.observableArrayList(hotelsBookingRepository.getAll());


        long bookedFlights = flightsBookings.stream().filter(booking -> booking.getStatus().equals(FlightsBookingModel.Status.BOOKED.toString())).count();
        long pendingFlights = flightsBookings.stream().filter(booking -> booking.getStatus().equals(FlightsBookingModel.Status.PENDING.toString())).count();
        long cancelledFlights = flightsBookings.stream().filter(booking -> booking.getStatus().equals(FlightsBookingModel.Status.CANCELLED.toString())).count();
        long bookedHotels = hotelsBookings.stream().filter(booking -> booking.getStatus().equals(HotelsBookingModel.Status.BOOKED.toString())).count();
        long pendingHotels = hotelsBookings.stream().filter(booking -> booking.getStatus().equals(HotelsBookingModel.Status.PENDING.toString())).count();
        long cancelledHotels = hotelsBookings.stream().filter(booking -> booking.getStatus().equals(HotelsBookingModel.Status.CANCELLED.toString())).count();


        // Create Series for BarChart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Booked Flights", bookedFlights));
        series.getData().add(new XYChart.Data<>("Pending Flights", pendingFlights));
        series.getData().add(new XYChart.Data<>("Cancelled Flights", cancelledFlights));
        series.getData().add(new XYChart.Data<>("Booked Hotels", bookedHotels));
        series.getData().add(new XYChart.Data<>("Pending Hotels", pendingHotels));
        series.getData().add(new XYChart.Data<>("Cancelled Hotels", cancelledHotels));


        // Add Series to the chart
        bookingChart.getData().add(series);
    }

    private void loadRecentBookings() {
        ObservableList<Object> bookings = FXCollections.observableArrayList();
        List<FlightsBookingModel> flightBookings = flightsBookingRepository.getAll().stream()
                .filter(booking -> !booking.getStatus().equals(FlightsBookingModel.Status.CANCELLED.toString()))
                .limit(5)
                .collect(Collectors.toList());
        for (FlightsBookingModel booking : flightBookings) {
            bookings.add(booking);
        }
        List<HotelsBookingModel> hotelBookings = hotelsBookingRepository.getAll().stream()
                .filter(booking -> !booking.getStatus().equals(HotelsBookingModel.Status.CANCELLED.toString()))
                .limit(5)
                .collect(Collectors.toList());
        for (HotelsBookingModel booking : hotelBookings) {
            bookings.add(booking);
        }

        recentBookingsList.setItems(bookings);
    }
    private void setupRecentBookingsListView() {
        recentBookingsList.setCellFactory(param -> new ListCell<Object>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10);
                    Label label;
                    Button cancelButton = new Button("Cancel");
                    cancelButton.setStyle("-fx-background-color: #00aff0; -fx-text-fill: white; -fx-padding: 5 10; -fx-background-radius: 4; -fx-font-size: 10px;");
                    if(item instanceof FlightsBookingModel){
                        FlightsBookingModel flightBooking = (FlightsBookingModel) item;
                        label = new Label("Flight - " + flightBooking.getCustomerName() +  " ("+flightBooking.getAirline() +") "+ " to " + flightBooking.getDestination() + " Status: "+ flightBooking.getStatus());
                        cancelButton.setOnAction(event -> {
                            flightBooking.setStatus(FlightsBookingModel.Status.CANCELLED);
                            flightsBookingRepository.update(flightBooking);
                            loadRecentBookings();
                        });
                    }
                    else if (item instanceof HotelsBookingModel){
                        HotelsBookingModel hotelBooking = (HotelsBookingModel) item;
                        label = new Label("Hotel - " + hotelBooking.getCustomerName() + " (" + hotelBooking.getHotelName() + ") " + " in " + hotelBooking.getLocation() + " Status: " + hotelBooking.getStatus());
                        cancelButton.setOnAction(event -> {
                            hotelBooking.setStatus(HotelsBookingModel.Status.CANCELLED);
                            hotelsBookingRepository.update(hotelBooking);
                            loadRecentBookings();
                        });
                    }else {
                        label = new Label("");
                        cancelButton.setVisible(false);
                    }
                    hbox.getChildren().addAll(label, cancelButton);
                    hbox.setStyle("-fx-alignment: center-left;");
                    setGraphic(hbox);
                }
            }
        });
    }

    @FXML
    private void goToBookings(javafx.event.ActionEvent event) throws IOException {
    }
}
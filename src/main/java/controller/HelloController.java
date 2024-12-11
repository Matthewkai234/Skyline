package controller;

import database.FlightsBookingModel;
import database.repositories.FlightsBookingRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.time.LocalDate;
import java.util.List;

public class HelloController {
    private final FlightsBookingRepository flightsBookingRepository = new FlightsBookingRepository();

    @FXML
    private ListView<String> bookingsListView;

    @FXML
    private Label welcomeText;

    @FXML
    protected void getSomeBookingsStatus() {
        List<FlightsBookingModel> flightsBookingModel = flightsBookingRepository.getAll();

        ObservableList<String> observableList = FXCollections
                .observableList(flightsBookingModel
                        .stream()
                        .map(FlightsBookingModel::getStatus)
                        .toList()
                );

        bookingsListView.setItems(observableList);
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");

        FlightsBookingModel flightsBookingModel = new FlightsBookingModel();
        flightsBookingModel.setStatus(FlightsBookingModel.Status.PENDING);
        flightsBookingModel.setBookingDate(LocalDate.now());

        flightsBookingRepository.insert(flightsBookingModel);
    }
}
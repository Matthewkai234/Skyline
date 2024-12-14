package controller;

import database.FlightsBookingModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.time.LocalDate;

public class BookingsController {

    @FXML
    private TableView<FlightsBookingModel> tableView;

    @FXML
    private TableColumn<FlightsBookingModel, String> bookingIdColumn;

    @FXML
    private TableColumn<FlightsBookingModel, String> bookingDateColumn;

    @FXML
    private TableColumn<FlightsBookingModel, String> bookingStatusColumn;

    private ObservableList<FlightsBookingModel> flightsBookingsList;

    @FXML
    private void loadData() {
        flightsBookingsList = FXCollections.observableArrayList(
                new FlightsBookingModel(1, LocalDate.now(), FlightsBookingModel.Status.BOOKED),
                new FlightsBookingModel(2, LocalDate.now(), FlightsBookingModel.Status.CANCELLED),
                new FlightsBookingModel(3, LocalDate.now(), FlightsBookingModel.Status.PENDING)
        );

        tableView.setItems(flightsBookingsList);
    }


}

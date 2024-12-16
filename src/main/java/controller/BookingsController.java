package controller;

import database.FlightsBookingModel;
import database.repositories.FlightsBookingRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BookingsController implements Initializable {

    @FXML
    private TableView<FlightsBookingModel> tableView;

    @FXML
    private TableColumn<FlightsBookingModel, Integer> bookingIdColumn;

    @FXML
    private TableColumn<FlightsBookingModel, LocalDate> bookingDateColumn;

    @FXML
    private TableColumn<FlightsBookingModel, String> bookingStatusColumn;

    private ObservableList<FlightsBookingModel> flightsBookingsList;
    FlightsBookingRepository flightsBookingRepository = new FlightsBookingRepository();

    ObservableList<FlightsBookingModel> initialData() {
//        FlightsBookingModel book1 = new FlightsBookingModel(LocalDate.now(),FlightsBookingModel.Status.BOOKED);
//        FlightsBookingModel book2 = new FlightsBookingModel(LocalDate.now(), FlightsBookingModel.Status.CANCELLED);
//        FlightsBookingModel book3 = new FlightsBookingModel(LocalDate.now(), FlightsBookingModel.Status.PENDING);

        var bookings = flightsBookingRepository.getAll();
        return FXCollections.<FlightsBookingModel>observableArrayList(bookings);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookingIdColumn.setCellValueFactory (new PropertyValueFactory<FlightsBookingModel, Integer>( "booking_id"));
        bookingDateColumn.setCellValueFactory (new PropertyValueFactory<FlightsBookingModel, LocalDate>( "bookingDate"));
        bookingStatusColumn.setCellValueFactory (new PropertyValueFactory<FlightsBookingModel, String>( "status"));

        tableView.setItems(initialData());

        initialData().forEach(booking -> {
            System.out.println("Booking ID: " + booking.getBooking_id());
            System.out.println("Booking Date: " + booking.getBookingDate());
            System.out.println("Booking Status: " + booking.getStatus());
            System.out.println("----------------------");
        });
    }



}



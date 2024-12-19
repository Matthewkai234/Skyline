package controller;

import database.FlightsBookingModel;
import database.repositories.FlightsBookingRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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


//        flightsBookingRepository.insert(new FlightsBookingModel(LocalDate.now(),FlightsBookingModel.Status.PENDING));
//        flightsBookingRepository.insert(new FlightsBookingModel(LocalDate.now(),FlightsBookingModel.Status.PENDING));
//        flightsBookingRepository.insert(new FlightsBookingModel(LocalDate.now(),FlightsBookingModel.Status.PENDING));
//        flightsBookingRepository.insert(new FlightsBookingModel(LocalDate.now(),FlightsBookingModel.Status.PENDING));
//        flightsBookingRepository.insert(new FlightsBookingModel(LocalDate.now(),FlightsBookingModel.Status.CANCELLED));
//        flightsBookingRepository.insert(new FlightsBookingModel(LocalDate.now(),FlightsBookingModel.Status.CANCELLED));

        var bookings = flightsBookingRepository.getAll();
        return FXCollections.<FlightsBookingModel>observableArrayList(bookings);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookingIdColumn.setCellValueFactory (new PropertyValueFactory<FlightsBookingModel, Integer>( "booking_id"));
        bookingDateColumn.setCellValueFactory (new PropertyValueFactory<FlightsBookingModel, LocalDate>( "bookingDate"));
        bookingStatusColumn.setCellValueFactory (new PropertyValueFactory<FlightsBookingModel, String>( "status"));

        bookingStatusColumn.setCellFactory(column -> {
            return new TableCell<FlightsBookingModel, String>() {
                private final Button button = new Button();
                @Override
                protected void updateItem(String status, boolean empty) {
                    super.updateItem(status, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        button.setText(status);
                        button.setOnAction(event -> {
                            FlightsBookingModel booking = getTableView().getItems().get(getIndex());
                            booking.setStatus(FlightsBookingModel.Status.CANCELLED);
                            flightsBookingRepository.update(booking);
                            tableView.refresh();
                        });
                        setGraphic(button);
                    }
                }
            };
        });

        tableView.setItems(initialData());

        initialData().forEach(booking -> {
            System.out.println("Booking ID: " + booking.getBooking_id());
            System.out.println("Booking Date: " + booking.getBookingDate());
            System.out.println("Booking Status: " + booking.getStatus());
            System.out.println("----------------------");
        });
    }

    public void refreshTableData() {
        ObservableList<FlightsBookingModel> allBookings = FXCollections.observableArrayList(flightsBookingRepository.getAll());
        ObservableList<FlightsBookingModel> filteredBookings = allBookings.stream()
                .filter(booking -> !booking.getStatus().equals(FlightsBookingModel.Status.CANCELLED.toString()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        tableView.setItems(filteredBookings);
    }

}



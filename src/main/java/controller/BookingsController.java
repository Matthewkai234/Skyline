package controller;

import model.BookingHistoryModel;
import model.FlightsBookingModel;
import model.HotelsBookingModel;
import database.services.FlightsBookingDAOImp;
import database.services.HotelsBookingDAOImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
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
    @FXML
    private TableColumn<FlightsBookingModel, String> takeOffColumn;
    @FXML
    private TableColumn<FlightsBookingModel, String> destinationColumn;
    @FXML
    private TableColumn<FlightsBookingModel, String> airlineColumn;
    @FXML
    private TableColumn<FlightsBookingModel, String> customerNameColumn;
    //-------------------------------------------------------------------//
    @FXML
    TableView<HotelsBookingModel> hotelsTableView;
    @FXML
    private TableColumn<HotelsBookingModel, Integer> hBookingIdColumn;
    @FXML
    private TableColumn<HotelsBookingModel, LocalDate> hBookingDateColumn;
    @FXML
    private TableColumn<HotelsBookingModel, String> hBookingStatusColumn;
    @FXML
    private TableColumn<HotelsBookingModel, String> hLocationColumn;
    @FXML
    private TableColumn<HotelsBookingModel, String> hHotelNameBookingColumn;
    @FXML
    private TableColumn<HotelsBookingModel, String> hCustomerNameColumn;
    //-------------------------------------------------------------------//
    @FXML
    TableView<BookingHistoryModel> bookingHistoryTableView;
    @FXML
    private TableColumn<BookingHistoryModel, Integer> bhBookingIdColumn;
    @FXML
    private TableColumn<BookingHistoryModel, LocalDate> bhBookingDateColumn;
    @FXML
    private TableColumn<BookingHistoryModel, String> bhTypeColumn;
    @FXML
    private TableColumn<BookingHistoryModel, String> bhItemNameColumn;

    @FXML
    private TableColumn<BookingHistoryModel, String> bhCustomerNameColumn;
    //-------------------------------------------------------------------//
    FlightsBookingDAOImp flightsBookingRepository = new FlightsBookingDAOImp();
    ObservableList<FlightsBookingModel> initialFlightsData() {
        var bookings = flightsBookingRepository.getAll();
        return FXCollections.<FlightsBookingModel>observableArrayList(bookings);
    }
    //-------------------------------------------------------------------//
    HotelsBookingDAOImp hotelsBookingRepository = new HotelsBookingDAOImp();
    ObservableList<HotelsBookingModel> initialHotelsData(){
        var bookings = hotelsBookingRepository.getAll();
        return FXCollections.<HotelsBookingModel>observableArrayList(bookings);
    }
    //-------------------------------------------------------------------//
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeFlightsBookings(url, resourceBundle);
        initializeHotelsBookings(url, resourceBundle);
        initializeBookingHistory(url,resourceBundle);
    }
    //-------------------------------------------------------------------//
    public void initializeBookingHistory(URL url, ResourceBundle resourceBundle){
        bhBookingIdColumn.setCellValueFactory (new PropertyValueFactory<BookingHistoryModel, Integer>( "bookingId"));
        bhBookingDateColumn.setCellValueFactory (new PropertyValueFactory<BookingHistoryModel, LocalDate>( "bookingDate"));
        bhTypeColumn.setCellValueFactory(new PropertyValueFactory<BookingHistoryModel, String>("type"));
        bhItemNameColumn.setCellValueFactory (new PropertyValueFactory<BookingHistoryModel, String>( "itemName"));
        bhCustomerNameColumn.setCellValueFactory (new PropertyValueFactory<BookingHistoryModel, String>( "customerName"));
        bookingHistoryTableView.setItems(initialBookingHistoryData());
    }
    //-------------------------------------------------------------------//
    private ObservableList<BookingHistoryModel> initialBookingHistoryData() {
        ObservableList<BookingHistoryModel> history = FXCollections.observableArrayList();

        List<FlightsBookingModel> flightBookings = flightsBookingRepository.getAll().stream()
                .filter(booking -> booking.getStatus().equals(FlightsBookingModel.Status.BOOKED.toString()))
                .collect(Collectors.toList());

        for (FlightsBookingModel booking : flightBookings) {
            history.add(new BookingHistoryModel(
                            booking.getBooking_id(),
                            booking.getBookingDate(),
                            "flight",
                            booking.getAirline(),
                            booking.getCustomerName()
                    )
            );
        }
        List<HotelsBookingModel> hotelBookings = hotelsBookingRepository.getAll().stream()
                .filter(booking -> booking.getStatus().equals(HotelsBookingModel.Status.BOOKED.toString()))
                .collect(Collectors.toList());

        for (HotelsBookingModel booking : hotelBookings){
            history.add(new BookingHistoryModel(
                    booking.getBooking_id(),
                    booking.getBookingDate(),
                    "hotel",
                    booking.getHotelName(),
                    booking.getCustomerName()
            ));
        }
        return history;
    }
    //-------------------------------------------------------------------//
    public void initializeFlightsBookings(URL url, ResourceBundle resourceBundle) {
        bookingIdColumn.setCellValueFactory (new PropertyValueFactory<FlightsBookingModel, Integer>( "booking_id"));
        bookingDateColumn.setCellValueFactory (new PropertyValueFactory<FlightsBookingModel, LocalDate>( "bookingDate"));
        takeOffColumn.setCellValueFactory (new PropertyValueFactory<FlightsBookingModel, String>( "takeOff"));
        destinationColumn.setCellValueFactory (new PropertyValueFactory<FlightsBookingModel, String>( "destination"));
        airlineColumn.setCellValueFactory (new PropertyValueFactory<FlightsBookingModel, String>( "airline"));
        customerNameColumn.setCellValueFactory (new PropertyValueFactory<FlightsBookingModel, String>( "customerName"));
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

                        button.setStyle("-fx-text-fill: white; -fx-padding: 8 15; -fx-background-radius: 4;");
                        switch (status) {
                            case "PENDING":
                                button.setStyle(button.getStyle() + "-fx-background-color: #FFC107; -fx-font-weight: bold; -fx-text-fill: White;");
                                break;
                            case "CANCELLED":
                                button.setStyle(button.getStyle() + "-fx-background-color: #8B0000; -fx-font-weight: bold");
                                break;
                            case "BOOKED":
                                button.setStyle(button.getStyle() + "-fx-background-color: green;");
                                break;
                            default:
                                button.setStyle(button.getStyle() + "-fx-background-color: grey;");
                                break;
                        }

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
        tableView.setItems(initialFlightsData());
    }


    public void initializeHotelsBookings(URL url, ResourceBundle resourceBundle) {
        hBookingIdColumn.setCellValueFactory (new PropertyValueFactory<HotelsBookingModel, Integer>( "booking_id"));
        hBookingDateColumn.setCellValueFactory (new PropertyValueFactory<HotelsBookingModel, LocalDate>( "bookingDate"));
        hHotelNameBookingColumn.setCellValueFactory (new PropertyValueFactory<HotelsBookingModel, String>( "hotelName"));
        hLocationColumn.setCellValueFactory (new PropertyValueFactory<HotelsBookingModel, String>( "location"));
        hCustomerNameColumn.setCellValueFactory (new PropertyValueFactory<HotelsBookingModel, String>( "customerName"));
        hBookingStatusColumn.setCellValueFactory (new PropertyValueFactory<HotelsBookingModel, String>( "status"));


        hBookingStatusColumn.setCellFactory(column -> {
            return new TableCell<HotelsBookingModel, String>() {
                private final Button button = new Button();
                @Override
                protected void updateItem(String status, boolean empty) {
                    super.updateItem(status, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        button.setText(status);

                        button.setStyle("-fx-text-fill: white; -fx-padding: 8 15; -fx-background-radius: 4;");
                        switch (status) {
                            case "PENDING":
                                button.setStyle(button.getStyle() + "-fx-background-color: #FFC107; -fx-font-weight: bold; -fx-text-fill: White;");
                                break;
                            case "CANCELLED":
                                button.setStyle(button.getStyle() + "-fx-background-color: #8B0000; -fx-font-weight: bold");
                                break;
                            case "BOOKED":
                                button.setStyle(button.getStyle() + "-fx-background-color: green;");
                                break;
                            default:
                                button.setStyle(button.getStyle() + "-fx-background-color: grey;");
                                break;
                        }

                        button.setOnAction(event -> {
                            HotelsBookingModel booking = getTableView().getItems().get(getIndex());
                            booking.setStatus(HotelsBookingModel.Status.CANCELLED);
                            hotelsBookingRepository.update(booking);
                            hotelsTableView.refresh();
                        });
                        setGraphic(button);
                    }
                }
            };

        });

        hotelsTableView.setItems(initialHotelsData());
    }
    //-------------------------------------------------------------------//
    public void refreshTableData() {
        ObservableList<FlightsBookingModel> allBookings = FXCollections.observableArrayList(flightsBookingRepository.getAll());
        ObservableList<FlightsBookingModel> filteredBookings = allBookings.stream()
                .filter(booking -> !booking.getStatus().equals(FlightsBookingModel.Status.CANCELLED.toString()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        tableView.setItems(filteredBookings);
        refreshBookingHistoryData();
    }

    public void refreshHotelsTableData() {
        ObservableList<HotelsBookingModel> allBookings = FXCollections.observableArrayList(hotelsBookingRepository.getAll());
        ObservableList<HotelsBookingModel> filteredBookings = allBookings.stream()
                .filter(booking -> !booking.getStatus().equals(HotelsBookingModel.Status.CANCELLED.toString()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        hotelsTableView.setItems(filteredBookings);
        refreshBookingHistoryData();
    }

    private void refreshBookingHistoryData(){
        bookingHistoryTableView.setItems(initialBookingHistoryData());
    }

}
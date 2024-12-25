package controller;

import database.FlightsBookingModel;
import database.HotelsBookingModel;
import database.repositories.FlightsBookingRepository;
import database.repositories.HotelsBookingRepository;
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
    private ObservableList<FlightsBookingModel> flightsBookingsList;
    FlightsBookingRepository flightsBookingRepository = new FlightsBookingRepository();
    ObservableList<FlightsBookingModel> initialFlightsData() {
//            flightsBookingRepository.insert(new FlightsBookingModel(LocalDate.now(),"Grigori Rasputin ","Moscow", "Athens", "Poseidon",FlightsBookingModel.Status.BOOKED));
//            flightsBookingRepository.insert(new FlightsBookingModel(LocalDate.now(),"Kai Fiennes","Tokyo", "Beijing", "深圳航空",FlightsBookingModel.Status.BOOKED));
//            flightsBookingRepository.insert(new FlightsBookingModel(LocalDate.now(),"Aang Chaichana","Jakarta", "Bangkok", "Bangkok Airways",FlightsBookingModel.Status.BOOKED));
//            flightsBookingRepository.insert(new FlightsBookingModel(LocalDate.now(),"Katerina Petrova","Rome", "Sofia", "Fly2Sky",FlightsBookingModel.Status.PENDING));
//            flightsBookingRepository.insert(new FlightsBookingModel(LocalDate.now(),"Defne Topal","Istanbul", "Amman", "Turkish Airlines",FlightsBookingModel.Status.PENDING));
//            flightsBookingRepository.insert(new FlightsBookingModel(LocalDate.now(),"Matthew Halim","Amman", "London", "Spartan Airlines",FlightsBookingModel.Status.PENDING));
//            flightsBookingRepository.insert(new FlightsBookingModel(LocalDate.now(),"Jam Lee","Amman", "Dublin", "CityJet ",FlightsBookingModel.Status.CANCELLED));
//            flightsBookingRepository.insert(new FlightsBookingModel(LocalDate.now(),"Guy Lian","London", "Singapore", "Scoot Pte. Ltd",FlightsBookingModel.Status.CANCELLED));
        var bookings = flightsBookingRepository.getAll();
        return FXCollections.<FlightsBookingModel>observableArrayList(bookings);
    }
    //-------------------------------------------------------------------//
    private ObservableList<HotelsBookingModel> hotelsBookingList;
    HotelsBookingRepository hotelsBookingRepository = new HotelsBookingRepository();
    ObservableList<HotelsBookingModel> initialHotelsData(){
//        hotelsBookingRepository.insert(new HotelsBookingModel(LocalDate.now(),"Angelina Jolie","Paradise","London",HotelsBookingModel.Status.BOOKED));
//        hotelsBookingRepository.insert(new HotelsBookingModel(LocalDate.now(),"Jenna Ortega","Paradise","Transylvania",HotelsBookingModel.Status.PENDING));
//        hotelsBookingRepository.insert(new HotelsBookingModel(LocalDate.now(),"Tyler Hoechlin","Paradise","California",HotelsBookingModel.Status.CANCELLED));
        var bookings = hotelsBookingRepository.getAll();
        return FXCollections.<HotelsBookingModel>observableArrayList(bookings);
    }
    //-------------------------------------------------------------------//
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeFlightsBookings(url, resourceBundle);
        initializeHotelsBookings(url, resourceBundle);
    }
    //-------------------------------------------------------------------//



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
    }

    public void refreshHotelsTableData() {
        ObservableList<HotelsBookingModel> allBookings = FXCollections.observableArrayList(hotelsBookingRepository.getAll());
        ObservableList<HotelsBookingModel> filteredBookings = allBookings.stream()
                .filter(booking -> !booking.getStatus().equals(HotelsBookingModel.Status.CANCELLED.toString()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        hotelsTableView.setItems(filteredBookings);
    }

}



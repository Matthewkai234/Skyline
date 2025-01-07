package controller;

import model.FlightModel;
import database.services.SearshFlightDOAImp;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class FlightSearchResultTableController {
    private String from;
    private String to;

    @FXML
    private TableColumn<FlightModel, Integer> FlightId;

    @FXML
    private TableColumn<FlightModel, String> takeoffContry;

    @FXML
    private TableColumn<FlightModel, String> LandingCountry;

    @FXML
    private TableColumn<FlightModel, Integer> price;

    @FXML
    private TableColumn<FlightModel, String> takeoffDate;
    @FXML
    private TableColumn<FlightModel, String> arrivalDate;

    @FXML
    private TableColumn<FlightModel, String> AirLine;

    @FXML
    private TableColumn<FlightModel, Void> bookColumn;

    @FXML
    private TextField StartPrice;

    @FXML
    private TextField EndPrice;

    @FXML
    private TextField Airline;

    @FXML
    private TableView<FlightModel> Table;

    private final ObservableList<FlightModel> flightList = FXCollections.observableArrayList();

    @FXML
    public void setSearchCriteria(String from, String to, List<FlightModel> flights) {
        this.from = from;
        this.to = to;

        flightList.clear();
        flightList.addAll(flights);

        Table.setItems(flightList);
    }

    @FXML
    public void initialize() {
        FlightId.setCellValueFactory(new PropertyValueFactory<>("flightId"));
        takeoffContry.setCellValueFactory(new PropertyValueFactory<>("takeoffContry"));
        LandingCountry.setCellValueFactory(new PropertyValueFactory<>("landingCountry"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        AirLine.setCellValueFactory(new PropertyValueFactory<>("Airline"));




        takeoffDate.setCellValueFactory(cellData -> {
            if (cellData.getValue().getStartDate() != null) {
                return new SimpleStringProperty(cellData.getValue().getStartDate().toString());
            } else {
                return new SimpleStringProperty("N/A");
            }
        });
        arrivalDate.setCellValueFactory(cellData -> {
            if (cellData.getValue().getStartDate() != null) {
                return new SimpleStringProperty(cellData.getValue().getArriveDate().toString());
            } else {
                return new SimpleStringProperty("N/A");
            }
        });



        addBookButtonToTable();
    }

    private void addBookButtonToTable() {
        Callback<TableColumn<FlightModel, Void>, TableCell<FlightModel, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<FlightModel, Void> call(final TableColumn<FlightModel, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Book");

                    {
                        btn.setStyle("-fx-background-color: #00affa; -fx-text-fill: white; -fx-font-weight: bold;");
                        btn.setOnAction(event -> {
                            FlightModel flight = getTableView().getItems().get(getIndex());
                            handleBooking(flight);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                            setStyle("-fx-alignment: CENTER;");
                        }
                    }
                };
            }
        };

        bookColumn.setCellFactory(cellFactory);
    }

    private void handleBooking(FlightModel flight) {
        if (flight != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ClientFlightBooking.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                FlightBookingController bookingController = fxmlLoader.getController();
                bookingController.initFlightDetails(flight.getFlightId());

                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Booking Page");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void setFlightData(List<FlightModel> flights) {
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
        FlightModel selectedFlight = Table.getSelectionModel().getSelectedItem();

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
        }
    }

    @FXML
    void filterData() {
        System.out.println("Filter Data Func");

        SearshFlightDOAImp searshFlightDOAImp = new SearshFlightDOAImp();

        String startPriceText = StartPrice.getText();
        String endPriceText = EndPrice.getText();

        String airline = Airline.getText();
        Integer startPrice = (startPriceText.isEmpty()) ? null : Integer.parseInt(startPriceText);
        Integer endPrice = (endPriceText.isEmpty()) ? null : Integer.parseInt(endPriceText);

        System.out.println("From: " + from);
        System.out.println("To: " + to);
        System.out.println("Start Price: " + startPrice);
        System.out.println("End Price: " + endPrice);
        System.out.println("Airline: " + airline);

        List<FlightModel> filteredFlights = searshFlightDOAImp.Filter(from, to, startPrice, endPrice, airline);

        flightList.clear();
        flightList.addAll(filteredFlights);

        Table.setItems(flightList);
    }
}

package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.LatestBookingModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminHomeController {

    public void addCustomerWindow(ActionEvent actionEvent) throws IOException {
        loadNewWindow("CreateAccount.fxml");
    }

    private void loadNewWindow(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + fxmlFile));
            Node content = loader.load();
            Stage newStage = new Stage();
            Scene scene = new Scene((Parent) content);
            newStage.setScene(scene);
            newStage.setTitle("Skyline");
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TableView<LatestBookingModel> bookingTable;
    @FXML
    private TableColumn<LatestBookingModel, String> bookingIdColumn;
    @FXML
    private TableColumn<LatestBookingModel, String> typeColumn;
    @FXML
    private TableColumn<LatestBookingModel, String> customerNameColumn;
    @FXML
    private TableColumn<LatestBookingModel, String> detailsColumn;

    @FXML
    private Label airBookingLabel;
    @FXML
    private Label hotelBookingLabel;

    private final ObservableList<LatestBookingModel> bookingList = FXCollections.observableArrayList();

    public void initialize() {
        setupBookingTable();
        loadLatestBookingsFromDatabase();
        updateBookingCounts();
    }

    private void setupBookingTable() {
        bookingIdColumn.setCellValueFactory(data -> data.getValue().bookingIdProperty());
        typeColumn.setCellValueFactory(data -> data.getValue().typeProperty());
        customerNameColumn.setCellValueFactory(data -> data.getValue().customerNameProperty());
        detailsColumn.setCellValueFactory(data -> data.getValue().detailsProperty());

        bookingTable.setItems(bookingList);
    }

    private void loadLatestBookingsFromDatabase() {
        String url = "jdbc:mysql://localhost:3306/skyline";
        String user = "root";
        String password = "root1234";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            // Query to get the latest bookings from AirBooking
            String airBookingQuery = "SELECT booking_id, 'Flight' AS type, customer_name, flight_number AS details " +
                    "FROM AirBooking ORDER BY booking_date DESC LIMIT 5";
            ResultSet airResultSet = statement.executeQuery(airBookingQuery);

            while (airResultSet.next()) {
                bookingList.add(new LatestBookingModel(
                        airResultSet.getString("booking_id"),
                        airResultSet.getString("type"),
                        airResultSet.getString("customer_name"),
                        airResultSet.getString("details")
                ));
            }

            // Query to get the latest bookings from HotelBooking
            String hotelBookingQuery = "SELECT booking_id, 'Hotel' AS type, customer_name, hotel_name AS details " +
                    "FROM HotelBooking ORDER BY booking_date DESC LIMIT 5";
            ResultSet hotelResultSet = statement.executeQuery(hotelBookingQuery);

            while (hotelResultSet.next()) {
                bookingList.add(new LatestBookingModel(
                        hotelResultSet.getString("booking_id"),
                        hotelResultSet.getString("type"),
                        hotelResultSet.getString("customer_name"),
                        hotelResultSet.getString("details")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateBookingCounts() {
        String url = "jdbc:mysql://localhost:3306/skyline";
        String user = "root";
        String password = "root1234";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            // Query to count total air bookings
            String airBookingCountQuery = "SELECT COUNT(*) AS total FROM AirBooking";
            ResultSet airResultSet = statement.executeQuery(airBookingCountQuery);

            if (airResultSet.next()) {
                airBookingLabel.setText(airResultSet.getString("total"));
            }

            // Query to count total hotel bookings
            String hotelBookingCountQuery = "SELECT COUNT(*) AS total FROM HotelBooking";
            ResultSet hotelResultSet = statement.executeQuery(hotelBookingCountQuery);

            if (hotelResultSet.next()) {
                hotelBookingLabel.setText(hotelResultSet.getString("total"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

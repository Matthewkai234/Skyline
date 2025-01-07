package controller;

import database.services.LatestBookingDAOImp;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.LatestBookingModel;

import java.io.IOException;

public class AdminHomeController {

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

    private final LatestBookingDAOImp latestBookingDAO = new LatestBookingDAOImp();

    @FXML
    public void initialize() {
        // Initialize table columns
        bookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("booking_id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        detailsColumn.setCellValueFactory(new PropertyValueFactory<>("details"));

        // Fetch initial data
        loadInitialData();
    }
    //loads the data and updates the table
    private void loadInitialData() {
        ObservableList<LatestBookingModel> bookings = fetchBookings();
        bookingTable.setItems(bookings);
        updateBookingCounts(bookings);
    }
    //fetches the data
    private ObservableList<LatestBookingModel> fetchBookings() {
        var bookings = latestBookingDAO.getAll();
        return FXCollections.<LatestBookingModel>observableArrayList(bookings);
    }

    private void updateBookingCounts(ObservableList<LatestBookingModel> bookings) {
        int airCount = 0;
        int hotelCount = 0;

        for (LatestBookingModel booking : bookings) {
            if (booking.getType().equals("Flight")) {
                airCount++;
            } else if (booking.getType().equals("Hotel")) {
                hotelCount++;
            }
        }
        airBookingLabel.setText(String.valueOf(airCount));
        hotelBookingLabel.setText(String.valueOf(hotelCount));
    }

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
}
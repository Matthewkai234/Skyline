package controller;

import database.Flight;
import database.Hotel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Objects;

public class ListingsController {

    @FXML private TableView<Flight> flightTable;
    @FXML private TableColumn<Flight, String> flightNumberColumn;
    @FXML private TableColumn<Flight, String> airlineColumn;
    @FXML private TableColumn<Flight, String> destinationColumn;
    @FXML private TableColumn<Flight, String> departureDateColumn;
    @FXML private TableColumn<Flight, String> arrivalDateColumn;
    @FXML private TableColumn<Flight, Void> flightActionsColumn;

    @FXML private TableView<Hotel> hotelTable;
    @FXML private TableColumn<Hotel, String> hotelIdColumn;
    @FXML private TableColumn<Hotel, String> hotelNameColumn;
    @FXML private TableColumn<Hotel, String> locationColumn;
    @FXML private TableColumn<Hotel, Void> hotelActionsColumn;

    private final ObservableList<Flight> flightList = FXCollections.observableArrayList();
    private final ObservableList<Hotel> hotelList = FXCollections.observableArrayList();

    public void initialize() {
        setupFlightTable();
        setupHotelTable();
        loadDummyData();
    }

    public void addListingWindow(ActionEvent actionEvent) throws IOException {
        loadNewWindow("Editlisting.fxml");
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


    private void setupFlightTable() {
        flightNumberColumn.setCellValueFactory(data -> data.getValue().flightNumberProperty());
        airlineColumn.setCellValueFactory(data -> data.getValue().airlineProperty());
        destinationColumn.setCellValueFactory(data -> data.getValue().destinationProperty());
        departureDateColumn.setCellValueFactory(data -> data.getValue().departureDateProperty());
        arrivalDateColumn.setCellValueFactory(data -> data.getValue().arrivalDateProperty());

        addEditAndDeleteIcons(flightTable, flightActionsColumn, flightList);
        flightTable.setItems(flightList);
    }

    private void setupHotelTable() {
        hotelIdColumn.setCellValueFactory(data -> data.getValue().hotelIdProperty());
        hotelNameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        locationColumn.setCellValueFactory(data -> data.getValue().locationProperty());

        addEditAndDeleteIcons(hotelTable, hotelActionsColumn, hotelList);
        hotelTable.setItems(hotelList);
    }

    private void loadDummyData() {
        flightList.addAll(
                new Flight("AA123", "American Airlines", "New York", "2024-12-15", "2024-12-16"),
                new Flight("BA456", "British Airways", "London", "2024-12-20", "2024-12-21"),
                new Flight("DL789", "Delta Airlines", "Tokyo", "2024-12-25", "2024-12-26")
        );

        hotelList.addAll(
                new Hotel("H001", "Grand Palace", "Paris"),
                new Hotel("H002", "Royal Inn", "Rome"),
                new Hotel("H003", "Seaside Hotel", "Barcelona")
        );
    }

    private <T> void addEditAndDeleteIcons(TableView<T> table, TableColumn<T, Void> actionsColumn, ObservableList<T> list) {
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                editButton.setOnAction(event -> {
                    T item = getTableView().getItems().get(getIndex());
                    System.out.println("Editing: " + item);
                });

                deleteButton.setOnAction(event -> {
                    T item = getTableView().getItems().get(getIndex());
                    list.remove(item);
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(5, editButton, deleteButton));
                }
            }
        });
    }
}

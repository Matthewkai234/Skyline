package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;



public class AdminHomeController  {


    @FXML private TableView<Booking> bookingTable;
    @FXML private TableColumn<Booking, String> bookingIdColumn;
    @FXML private TableColumn<Booking, String> typeColumn;
    @FXML private TableColumn<Booking, String> customerNameColumn;
    @FXML private TableColumn<Booking, String> detailsColumn;

    private final ObservableList<Booking> bookingList = FXCollections.observableArrayList();

    public void initialize() {
        setupBookingTable();
        loadStaticData();
    }



    private void setupBookingTable() {
        bookingIdColumn.setCellValueFactory(data -> data.getValue().bookingIdProperty());
        typeColumn.setCellValueFactory(data -> data.getValue().typeProperty());
        customerNameColumn.setCellValueFactory(data -> data.getValue().customerNameProperty());
        detailsColumn.setCellValueFactory(data -> data.getValue().detailsProperty());

        bookingTable.setItems(bookingList);
    }

    private void loadStaticData() {
        bookingList.addAll(
                new Booking("B001", "Flight", "John Doe", "Flight AA123 - New York"),
                new Booking("B002", "Hotel", "Jane Smith", "Grand Palace - Paris"),
                new Booking("B003", "Flight", "Mike Johnson", "Flight DL456 - London"),
                new Booking("B004", "Hotel", "Emily Davis", "Seaside Hotel - Barcelona")
        );
    }

    public static class Booking {
        private final StringProperty bookingId;
        private final StringProperty type;
        private final StringProperty customerName;
        private final StringProperty details;

        public Booking(String bookingId, String type, String customerName, String details) {
            this.bookingId = new SimpleStringProperty(bookingId);
            this.type = new SimpleStringProperty(type);
            this.customerName = new SimpleStringProperty(customerName);
            this.details = new SimpleStringProperty(details);
        }

        public StringProperty bookingIdProperty() {
            return bookingId;
        }

        public StringProperty typeProperty() {
            return type;
        }

        public StringProperty customerNameProperty() {
            return customerName;
        }

        public StringProperty detailsProperty() {
            return details;
        }
    }
}

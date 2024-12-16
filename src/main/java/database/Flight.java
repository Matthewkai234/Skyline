package database;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Flight {
    private final StringProperty flightNumber;
    private final StringProperty airline;
    private final StringProperty destination;
    private final StringProperty departureDate;
    private final StringProperty arrivalDate;

    public Flight(String flightNumber, String airline, String destination, String departureDate, String arrivalDate) {
        this.flightNumber = new SimpleStringProperty(flightNumber);
        this.airline = new SimpleStringProperty(airline);
        this.destination = new SimpleStringProperty(destination);
        this.departureDate = new SimpleStringProperty(departureDate);
        this.arrivalDate = new SimpleStringProperty(arrivalDate);
    }

    public StringProperty flightNumberProperty() {
        return flightNumber;
    }

    public StringProperty airlineProperty() {
        return airline;
    }

    public StringProperty destinationProperty() {
        return destination;
    }

    public StringProperty departureDateProperty() {
        return departureDate;
    }

    public StringProperty arrivalDateProperty() {
        return arrivalDate;
    }
}

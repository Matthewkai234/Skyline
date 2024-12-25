package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Booking {
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

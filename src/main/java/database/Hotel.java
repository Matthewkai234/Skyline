package database;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Hotel {
    private final StringProperty hotelId;
    private final StringProperty name;
    private final StringProperty location;

    public Hotel(String hotelId, String name, String location) {
        this.hotelId = new SimpleStringProperty(hotelId);
        this.name = new SimpleStringProperty(name);
        this.location = new SimpleStringProperty(location);
    }

    public StringProperty hotelIdProperty() {
        return hotelId;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty locationProperty() {
        return location;
    }

}

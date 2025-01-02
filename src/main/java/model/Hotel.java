package model;

import javax.persistence.*;

@Entity
@Table(name = "hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hotelId;
    private String name;
    private String location;

    @Transient
    private String actions;

    public Hotel() {

    }

    public Hotel(int hotelId, String name, String location) {
        this.hotelId = hotelId;
        this.name = name;
        this.location = location;
        this.actions = "Actions";
    }


    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotelId=" + hotelId +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
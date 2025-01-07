package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hotels")
public class HotelsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HotelId")
    private int HotelId;

    @Column(name = "Location")
    private String Location;

    @Column(name = "HotelName")
    private String HotelName;

    @Column(name = "HotelRate")
    private float HotelRate;

    @Column(name = "HotelPrice")
    private double HotelPrice;

    public int getHotelId() {
        return HotelId;
    }

    public void setHotelId(int hotelId) {
        this.HotelId = hotelId;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        this.Location = location;
    }

    public String getHotelName() {
        return HotelName;
    }

    public void setHotelName(String hotelName) {
        this.HotelName = hotelName;
    }

    public float getHotelRate() {
        return HotelRate;
    }

    public void setHotelRate(float hotelRate) {
        this.HotelRate = hotelRate;
    }

    public double getHotelPrice() {
        return HotelPrice;
    }

    public void setHotelPrice(double hotelPrice) {
        this.HotelPrice = hotelPrice;
    }
}
package database;

import javax.persistence.*;
import java.time.LocalDate;

// Flight class
@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer flightId;

    @Column(name = "Airline")
    private String Airline;

    @Column(name = "TakeoffContry")
    private String TakeoffContry;

    @Column(name = "LandingCountry")
    private String LandingCountry;

    @Column(name = "price")
    private int price;

    @Column(name = "StartDate")
    private LocalDate StartDate;

    @Column(name = "ArriveDate")
    private LocalDate ArriveDate;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLandingCountry() {
        return LandingCountry;
    }

    public void setLandingCountry(String landingCountry) {
        this.LandingCountry = landingCountry;
    }

    public String getTakeoffContry() {
        return TakeoffContry;
    }

    public void setTakeoffContry(String takeoffContry) {
        this.TakeoffContry = takeoffContry;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public String getAirline() {
        return Airline;
    }

    public void setAirline(String airline) {
        this.Airline = airline;
    }

    public LocalDate getStartDate() {
        return StartDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.StartDate = startDate;
    }

    public LocalDate getArriveDate() {
        return ArriveDate;
    }

    public void setArriveDate(LocalDate arriveDate) {
        this.ArriveDate = arriveDate;
    }
}

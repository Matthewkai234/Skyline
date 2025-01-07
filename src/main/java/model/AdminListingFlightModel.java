package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "flight")
public class AdminListingFlightModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flightId;

    @Column(name = "flightNumber")
    private String flightNumber;

    @Column(name = "Airline")
    private String airline;

    @Column(name = "destination")
    private String destination;

    @Column(name = "departureDate")
    private String departureDate;

    @Column(name = "ArriveDate")
    private String arrivalDate;

    @Column(name = "TakeoffContry")
    private String takeoffCountry;

    @Column(name = "LandingCountry")
    private String landingCountry;

    @Column(name = "price")
    private int price;

    @Column(name = "StartDate")
    private Date startDate;


    @Transient
    private String actions;

    public AdminListingFlightModel() {}

    public AdminListingFlightModel(String flightNumber, String airline, String destination, String departureDate,
                                   String arrivalDate, String takeoffCountry, String landingCountry,
                                   int price, Date startDate, Date arriveDate) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.destination = destination;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.takeoffCountry = takeoffCountry;
        this.landingCountry = landingCountry;
        this.price = price;
        this.startDate = startDate;
        this.actions = "Actions";
    }

    // Getters and Setters


    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getTakeoffCountry() {
        return takeoffCountry;
    }

    public void setTakeoffCountry(String takeoffCountry) {
        this.takeoffCountry = takeoffCountry;
    }

    public String getLandingCountry() {
        return landingCountry;
    }

    public void setLandingCountry(String landingCountry) {
        this.landingCountry = landingCountry;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightId=" + flightId +
                ", flightNumber='" + flightNumber + '\'' +
                ", airline='" + airline + '\'' +
                ", destination='" + destination + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", arrivalDate='" + arrivalDate + '\'' +
                ", takeoffCountry='" + takeoffCountry + '\'' +
                ", landingCountry='" + landingCountry + '\'' +
                ", price=" + price +
                ", startDate=" + startDate +
                '}';
    }
}

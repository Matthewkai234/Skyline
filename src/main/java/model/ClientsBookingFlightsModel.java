package model;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "clients_booking_flights")
public class ClientsBookingFlightsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "take_off")
    private String takeOff;

    @Column(name = "destination")
    private String destination;

    @Column(name = "airline")
    private String airline;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "status")
    private String status;

    public ClientsBookingFlightsModel() {
    }

    public ClientsBookingFlightsModel(int bookingId, LocalDate bookingDate, String takeOff, String destination, String airline, String customerName, String status) {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.takeOff = takeOff;
        this.destination = destination;
        this.airline = airline;
        this.customerName = customerName;
        this.status = status;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getTakeOff() {
        return takeOff;
    }

    public void setTakeOff(String takeOff) {
        this.takeOff = takeOff;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
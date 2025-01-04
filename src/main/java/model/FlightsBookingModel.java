package model;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "flights_booking")
public class FlightsBookingModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int booking_id;
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



    public FlightsBookingModel() {
    }
    public FlightsBookingModel(LocalDate bookingDate,String customerName, String takeOff, String destination, String airline, Status status) {
        this.bookingDate = bookingDate;
        this.customerName = customerName;
        this.takeOff = takeOff;
        this.destination = destination;
        this.airline = airline;
        this.status = status.toString();
    }

    public FlightsBookingModel(int booking_id, LocalDate bookingDate,String customerName, String takeOff, String destination, String airline, Status status) {
        this.booking_id = booking_id;
        this.bookingDate = bookingDate;
        this.customerName = customerName;
        this.takeOff = takeOff;
        this.destination = destination;
        this.airline = airline;
        this.status = status.toString();
    }

    public enum Status {
        PENDING,
        CANCELLED,
        BOOKED,
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int bookingId) {
        this.booking_id = bookingId;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status.toString();
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

}

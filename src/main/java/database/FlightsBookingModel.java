package database;

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
    @Column(name = "status")
    private String status;

    public FlightsBookingModel() {
    }

    public FlightsBookingModel(int booking_id, LocalDate bookingDate, Status status) {
        this.booking_id = booking_id;
        this.bookingDate = bookingDate;
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
}

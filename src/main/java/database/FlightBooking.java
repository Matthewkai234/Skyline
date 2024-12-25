package database;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "FlightBooking")
public class FlightBooking extends Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer flightBookingId;

    @Column(name = "startDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "arriveDate", nullable = false)
    private LocalDate arriveDate;

    @Column(name = "price", nullable = false)
    private Float price;

    @ManyToOne
    @JoinColumn(name = "flightId", nullable = false)
    private Flight flight;


    public Integer getFlightBookingId() {
        return flightBookingId;
    }

    public void setFlightBookingId(Integer flightBookingId) {
        this.flightBookingId = flightBookingId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(LocalDate arriveDate) {
        this.arriveDate = arriveDate;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}

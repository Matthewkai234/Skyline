package database;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "hotels_booking")
public class HotelsBookingModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int booking_id;
    @Column(name = "booking_date")
    private LocalDate bookingDate;
    @Column(name = "hotel_name")
    private String hotelName;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "location")
    private String location;
    @Column(name = "status")
    private String status;



    public HotelsBookingModel() {
    }
    public HotelsBookingModel(LocalDate bookingDate, String customerName, String hotelName, String location, Status status) {
        this.bookingDate = bookingDate;
        this.customerName = customerName;
        this.hotelName = hotelName;
        this.location = location;
        this.status = status.toString();
    }

    public HotelsBookingModel(int booking_id, LocalDate bookingDate, String customerName, String hotelName, String location, Status status) {
        this.booking_id = booking_id;
        this.bookingDate = bookingDate;
        this.customerName = customerName;
        this.hotelName = hotelName;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

}

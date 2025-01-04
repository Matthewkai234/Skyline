package model;
import java.time.LocalDate;

public class BookingHistoryModel {
    private int bookingId;
    private LocalDate bookingDate;
    private String type;
    private String airlineOrHotel;
    private String customerName;

    public BookingHistoryModel(int bookingId, LocalDate bookingDate, String type, String itemName, String customerName) {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.type = type;
        this.airlineOrHotel = itemName;
        this.customerName = customerName;
    }

    public int getBookingId() {
        return bookingId;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public String getType() {
        return type;
    }

    public String getItemName(){return airlineOrHotel;}

    public String getCustomerName() {
        return customerName;
    }
}

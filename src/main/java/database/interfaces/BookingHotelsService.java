package database.interfaces;

import model.BookingHotels;
import java.util.List;

public interface BookingHotelsService {
    void saveBooking(BookingHotels booking);
    List<BookingHotels> getAllBookings();
    BookingHotels getBookingById(int id);
}
package database.interfaces;

import model.ClientsBookingHotelsModel;

public interface ClientsHotelBookingsDAO {
    void saveBooking(ClientsBookingHotelsModel booking);
}
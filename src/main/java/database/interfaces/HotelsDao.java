package database.interfaces;

import database.Hotels;
import java.util.List;

public interface HotelsDao {
    List<Hotels> getAllHotels();
    Hotels findHotelById(int hotelId);
}

package database.interfaces;

import model.Hotels;
import java.util.List;

public interface HotelsDao {
    List<Hotels> getAllHotels();
    Hotels findHotelById(int hotelId);
    List<Hotels> getHotelsList(int limit);
    Hotels getRandomHotel();
}
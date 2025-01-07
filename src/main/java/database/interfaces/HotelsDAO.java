package database.interfaces;

import model.HotelsModel;
import java.util.List;

public interface HotelsDAO {
    List<HotelsModel> getAllHotels();
    HotelsModel findHotelById(int hotelId);
    List<HotelsModel> getHotelsList(int limit);
    HotelsModel getRandomHotel();
}
package database.interfaces;

import database.FlightsBookingModel;
import java.util.List;

public interface IFlightsBookingRepository {

    void insert(FlightsBookingModel flightsBookingModel);
    void update(FlightsBookingModel flightsBookingModel);
    void delete(FlightsBookingModel flightsBookingModel);
    List<FlightsBookingModel> getAll();
    FlightsBookingModel findBookingById(int bookingID);
}

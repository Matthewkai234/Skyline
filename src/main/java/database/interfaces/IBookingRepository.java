package database.interfaces;

import database.FlightsBookingModel;
import java.util.List;

public interface IBookingRepository <T> {

    void insert(T model);
    void update(T model);
    void delete(T model);
    List<T> getAll();
    T findBookingById(int bookingID);
}

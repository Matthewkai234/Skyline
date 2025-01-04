package database.interfaces;

import java.util.List;

public interface BookingDAO<T> {

    void insert(T model);
    void update(T model);
    void delete(T model);
    List<T> getAll();
    T findBookingById(int bookingID);
}

package database.services;

import database.interfaces.BookingHotelsService;
import model.BookingHotels;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class BookingHotelsServiceImp implements BookingHotelsService {

    @Override
    public void saveBooking(BookingHotels booking) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(booking);
            transaction.commit();
            System.out.println("Booking saved to database: " + booking);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<BookingHotels> getAllBookings() {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<BookingHotels> bookings = null;

        try {
            bookings = session.createQuery("from BookingHotels", BookingHotels.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return bookings;
    }

    @Override
    public BookingHotels getBookingById(int id) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        BookingHotels booking = null;

        try {
            booking = session.get(BookingHotels.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return booking;
    }
}
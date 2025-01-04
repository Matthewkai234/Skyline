package database.repositories;

import model.Booking;
import database.interfaces.BookingDOA;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

public class BookingDOAImp implements BookingDOA {

    HibernateUtil hibernateUtil;
    SessionFactory sessionFactory;

    public BookingDOAImp() {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();

    }
    @Override
    public void AddBooking(Booking booking) {

        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            session.save(booking);
            session.getTransaction().commit();

        }catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }finally {
            session.close();
        }


    }
}

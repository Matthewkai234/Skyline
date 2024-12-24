package database.repositories;

import database.FlightBooking;
import database.interfaces.FlightBookingDOA;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

public class FlightBookingDOAImp implements FlightBookingDOA {
    HibernateUtil hibernateUtil;
    SessionFactory sessionFactory;

    public FlightBookingDOAImp() {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }

    @Override
    public void save(FlightBooking booking) {

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

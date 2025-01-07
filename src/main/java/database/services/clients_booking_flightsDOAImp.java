package database.services;

import database.interfaces.clients_booking_flightsDOA;
import model.clients_booking_flights;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

import org.hibernate.Session;


public class clients_booking_flightsDOAImp implements clients_booking_flightsDOA {

    private final SessionFactory sessionFactory;

    public clients_booking_flightsDOAImp() {
        HibernateUtil hibernateUtility = HibernateUtil.getInstance();
        sessionFactory = hibernateUtility.getSessionFactory();
    }
    public void AddBooking(clients_booking_flights booking){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(booking);
            session.getTransaction().commit();

        }

    }

}
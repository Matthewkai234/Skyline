package database.services;

import database.interfaces.ClientsBookingFlightsDAO;
import model.ClientsBookingFlightsModel;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

import org.hibernate.Session;


public class ClientsBookingFlightsDAOImp implements ClientsBookingFlightsDAO {

    private final SessionFactory sessionFactory;

    public ClientsBookingFlightsDAOImp() {
        HibernateUtil hibernateUtility = HibernateUtil.getInstance();
        sessionFactory = hibernateUtility.getSessionFactory();
    }
    public void AddBooking(ClientsBookingFlightsModel booking){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(booking);
            session.getTransaction().commit();

        }

    }

}
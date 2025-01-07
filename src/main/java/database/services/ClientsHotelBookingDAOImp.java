package database.services;


import database.interfaces.ClientsHotelBookingsDAO;
import model.ClientsBookingHotelsModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ClientsHotelBookingDAOImp implements ClientsHotelBookingsDAO {

    private static SessionFactory sessionFactory;
    public ClientsHotelBookingDAOImp(){
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(ClientsBookingHotelsModel.class).buildSessionFactory();
        } catch (Exception e) {
            System.out.println("Error creating session factory " + e);
            throw new ExceptionInInitializerError(e);
        }

    }
    @Override
    public void saveBooking(ClientsBookingHotelsModel booking) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.persist(booking);
            transaction.commit();
            System.out.println("Booking data save succesfully");
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
            System.out.println("Error saving booking data " + e);
        }
    }
}
package util;

import database.AirLine;
import database.FlightsBookingModel;
import database.HotelsBookingModel;
import model.Listing;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import database.Flight;
import database.Booking;
import database.FlightsBookingModel;
import model.Listing;


public class HibernateUtil {
    private static HibernateUtil instance = null;
    private static SessionFactory sessionFactory;

    private HibernateUtil() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(FlightsBookingModel.class);
        configuration.addAnnotatedClass(HotelsBookingModel.class);
        configuration.addAnnotatedClass(Flight.class);
        configuration.addAnnotatedClass(AirLine.class);
        configuration.addAnnotatedClass(Booking.class);
        configuration.addAnnotatedClass(Listing.class); // Add the Listing model
        configuration.configure();
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static HibernateUtil getInstance() {
        if (instance == null) {
            instance = new HibernateUtil();
        }
        return instance;
    }

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            instance = new HibernateUtil();  // Ensure the session factory is initialized
        }
        return sessionFactory;
    }

}


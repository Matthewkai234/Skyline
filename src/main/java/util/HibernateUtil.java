package util;

import database.Users;
import database.Roles;
import database.Permissions;
import database.Flight;

import model.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static HibernateUtil instance = null;
    private static SessionFactory sessionFactory;

    // Private constructor for Singleton
    private HibernateUtil() {
        Configuration configuration = new Configuration();

        // Add your entity classes here (from the database package)
        configuration.addAnnotatedClass(Users.class);
        configuration.addAnnotatedClass(Roles.class);
        configuration.addAnnotatedClass(Permissions.class);
        configuration.addAnnotatedClass(Flight.class);
        configuration.addAnnotatedClass(Hotels.class);
        configuration.addAnnotatedClass(clients_booking_hotels.class);

        // Add model classes here
        configuration.addAnnotatedClass(FlightsBookingModel.class);
        configuration.addAnnotatedClass(HotelsBookingModel.class);
        configuration.addAnnotatedClass(AirBookingTotalModel.class);
        configuration.addAnnotatedClass(HotelBookingTotalModel.class);
        configuration.addAnnotatedClass(AdminListingFlightModel.class);

        // Load hibernate.cfg.xml configuration if it exists
        configuration.configure();

        // Build the service registry
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        // Create the SessionFactory from the configuration and service registry
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    // Singleton pattern to get the HibernateUtil instance
    public static HibernateUtil getInstance() {
        if (instance == null) {
            instance = new HibernateUtil();
        }
        return instance;
    }

    // Synchronized method to get the SessionFactory
    public synchronized SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

package util;
import database.Users;
import database.Roles;
import database.Permissions;
import database.Flight;
import database.Booking;

import model.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;



public class HibernateUtil {
    private static HibernateUtil instance = null;
    private static SessionFactory sessionFactory;

    private HibernateUtil() {
        Configuration configuration = new Configuration();

        // Add your entity classes here (from the database package)
        configuration.addAnnotatedClass(Users.class);
        configuration.addAnnotatedClass(Roles.class);
        configuration.addAnnotatedClass(Permissions.class);
        configuration.addAnnotatedClass(Flight.class);
        configuration.addAnnotatedClass(Booking.class);


        // Add model classes here
        configuration.addAnnotatedClass(FlightsBookingModel.class);
        configuration.addAnnotatedClass(HotelsBookingModel.class);
        configuration.addAnnotatedClass(AirBookingTotalModel.class);
        configuration.addAnnotatedClass(HotelBookingTotalModel.class);
        configuration.addAnnotatedClass(AdminListingFlightModel.class);
        configuration.addAnnotatedClass(AdminListingHotelModel.class);



        configuration.configure(); // Load hibernate.cfg.xml if exists
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
    public synchronized SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
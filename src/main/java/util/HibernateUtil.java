package util;

import database.FlightsBookingModel;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static HibernateUtil instance = null;
    private static SessionFactory sessionFactory;

    private HibernateUtil() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(FlightsBookingModel.class);
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

    public synchronized SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

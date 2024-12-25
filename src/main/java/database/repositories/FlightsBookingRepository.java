package database.repositories;

import database.FlightsBookingModel;
import database.interfaces.IBookingRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

import java.util.List;


public class FlightsBookingRepository implements IBookingRepository <FlightsBookingModel> {

    private final SessionFactory sessionFactory;

    public FlightsBookingRepository() {
        HibernateUtil hibernateUtility = HibernateUtil.getInstance();
        sessionFactory = hibernateUtility.getSessionFactory();
    }

    @Override
    public void insert(FlightsBookingModel flightsBookingModel) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(flightsBookingModel);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(FlightsBookingModel flightsBookingModel) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(flightsBookingModel);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(FlightsBookingModel flightsBookingModel) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(flightsBookingModel);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<FlightsBookingModel> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT u FROM FlightsBookingModel u", FlightsBookingModel.class).list();
        }
    }

    @Override
    public FlightsBookingModel findBookingById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(FlightsBookingModel.class, id);
        }
    }



}

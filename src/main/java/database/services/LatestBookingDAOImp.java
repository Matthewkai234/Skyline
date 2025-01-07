package database.services;

import database.interfaces.BookingDAO;
import model.HotelsBookingModel;
import model.LatestBookingModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

import java.util.List;

public class LatestBookingDAOImp implements BookingDAO <LatestBookingModel>{

    private final SessionFactory sessionFactory;

    public LatestBookingDAOImp() {
        HibernateUtil hibernateUtility = HibernateUtil.getInstance();
        sessionFactory = hibernateUtility.getSessionFactory();
    }

    @Override
    public void insert(LatestBookingModel latestBookingModel) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(latestBookingModel);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(LatestBookingModel latestBookingModel) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(latestBookingModel);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(LatestBookingModel latestBookingModel) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(latestBookingModel);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<LatestBookingModel> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT u FROM LatestBookingModel u", LatestBookingModel.class).list();
        }
    }

    @Override
    public LatestBookingModel findBookingById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(LatestBookingModel.class, id);
        }
    }

}


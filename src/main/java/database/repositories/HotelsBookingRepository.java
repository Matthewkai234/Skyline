package database.repositories;

import database.HotelsBookingModel;
import database.interfaces.IBookingRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

import java.util.List;

public class HotelsBookingRepository implements IBookingRepository <HotelsBookingModel> {

    private final SessionFactory sessionFactory;

    public HotelsBookingRepository() {
        HibernateUtil hibernateUtility = HibernateUtil.getInstance();
        sessionFactory = hibernateUtility.getSessionFactory();
    }

    @Override
    public void insert(HotelsBookingModel hotelsBookingModel) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(hotelsBookingModel);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(HotelsBookingModel hotelsBookingModel) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(hotelsBookingModel);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(HotelsBookingModel hotelsBookingModel) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(hotelsBookingModel);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<HotelsBookingModel> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT u FROM HotelsBookingModel u", HotelsBookingModel.class).list();
        }
    }

    @Override
    public HotelsBookingModel findBookingById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(HotelsBookingModel.class, id);
        }
    }



}

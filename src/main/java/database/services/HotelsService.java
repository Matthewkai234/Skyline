package database.services;

import database.Hotels;
import database.interfaces.HotelsDao;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class HotelsService implements HotelsDao {

    private final SessionFactory sessionFactory;

    public HotelsService() {
        this.sessionFactory = HibernateUtil.getInstance().getSessionFactory();
    }

    @Override
    public List<Hotels> getAllHotels() {
        Session session = sessionFactory.openSession();
        List<Hotels> hotels = null;
        try {
            session.beginTransaction();

            Query<Hotels> query = session.createQuery("from Hotels", Hotels.class);
            hotels = query.list();

            if (hotels != null) {
                System.out.println("Found " + hotels.size() + " hotels.");
            } else {
                System.out.println("No hotels found.");
            }

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return hotels;
    }

    @Override
    public Hotels findHotelById(int hotelId) {
        Session session = sessionFactory.openSession();
        Hotels hotel = null;
        try {
            session.beginTransaction();

            hotel = session.get(Hotels.class, hotelId);

            if (hotel != null) {
                System.out.println("Hotel Found: ");
            } else {
                System.out.println("Hotel not found with ID: " + hotelId);
            }

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return hotel;
    }

    public Hotels getRandomHotel() {
        Session session = sessionFactory.openSession();
        Hotels hotel = null;
        try {
            session.beginTransaction();

            Query<Hotels> query = session.createQuery("FROM Hotels ORDER BY rand()", Hotels.class);
            query.setMaxResults(1);
            hotel = query.uniqueResult();

            if (hotel != null) {
                System.out.println("Hotel Found: ");
                System.out.println("Hotel ID: " + hotel.getHotelId());
                System.out.println("Hotel Name: " + hotel.getHotelName());
                System.out.println("Location: " + hotel.getLocation());
                System.out.println("Rate: " + hotel.getHotelRate());
                System.out.println("Price: " + hotel.getHotelPrice());
            } else {
                System.out.println("No hotel found.");
            }

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return hotel;
    }

    public List<Hotels> getHotelsList(int limit) {
        Session session = sessionFactory.openSession();
        List<Hotels> hotels = null;
        try {
            session.beginTransaction();

            Query<Hotels> query = session.createQuery("FROM Hotels", Hotels.class);
            query.setMaxResults(limit);
            hotels = query.list();

            if (hotels != null) {
                System.out.println("Found " + hotels.size() + " hotels.");
            } else {
                System.out.println("No hotels found.");
            }

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return hotels;
    }
}

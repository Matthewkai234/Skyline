package database.services;

import model.HotelsModel;
import database.interfaces.HotelsDAO;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class HotelsDAOImp implements HotelsDAO {

    public List<HotelsModel> getHotelsListByLocation(String location) {
        Session session = sessionFactory.openSession();
        List<HotelsModel> hotels = null;
        try {
            session.beginTransaction();

            Query<HotelsModel> query = session.createQuery("FROM HotelsModel WHERE Location LIKE :location", HotelsModel.class);
            query.setParameter("location", "%" + location + "%");

            List<HotelsModel> hotelList = query.getResultList();

            if (hotelList != null && !hotelList.isEmpty()) {
                System.out.println("Found " + hotelList.size() + " hotels.");
                hotels = hotelList;
            } else {
                System.out.println("ghaith .");
            }

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return hotels != null ? hotels : new ArrayList<>();
    }




    private final SessionFactory sessionFactory;

    public HotelsDAOImp() {
        this.sessionFactory = HibernateUtil.getInstance().getSessionFactory();
    }

    @Override
    public List<HotelsModel> getAllHotels() {
        Session session = sessionFactory.openSession();
        List<HotelsModel> hotels = null;
        try {
            session.beginTransaction();

            Query<HotelsModel> query = session.createQuery("from HotelsModel", HotelsModel.class);
            hotels = query.list();

            if (hotels != null) {
                System.out.println("Found " + hotels.size() + " hotels.");
            } else {
                System.out.println("ghaith here");
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
    public HotelsModel findHotelById(int hotelId) {
        Session session = sessionFactory.openSession();
        HotelsModel hotel = null;
        try {
            session.beginTransaction();

            hotel = session.get(HotelsModel.class, hotelId);

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

    public HotelsModel getRandomHotel() {
        Session session = sessionFactory.openSession();
        HotelsModel hotel = null;
        try {
            session.beginTransaction();

            Query<HotelsModel> query = session.createQuery("FROM HotelsModel ORDER BY rand()", HotelsModel.class);
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

    public List<HotelsModel> getHotelsList(int limit) {
        Session session = sessionFactory.openSession();
        List<HotelsModel> hotels = null;
        try {
            session.beginTransaction();

            Query<HotelsModel> query = session.createQuery("FROM HotelsModel", HotelsModel.class);
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
package database.services;

import database.Flight;
import database.interfaces.SearshFlightDOA;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

import java.util.List;

public class SearshFlightDOAImp implements SearshFlightDOA {
    HibernateUtil hibernateUtil;
    SessionFactory sessionFactory;

    public SearshFlightDOAImp() {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }

    @Override

    public List<Flight> searchFlights(String TakeoffContry, String LandingCountry,String date){
        Session session = sessionFactory.openSession();

        List<Flight> flights = null;


        try {
            session.beginTransaction();
            String hql = "FROM Flight WHERE TakeoffContry = :TakeoffContry AND LandingCountry = :LandingCountry";

            flights = session.createQuery(hql, Flight.class)
                    .setParameter("TakeoffContry", TakeoffContry)
                    .setParameter("LandingCountry", LandingCountry)
                    .getResultList();
            session.getTransaction().commit();

        }catch (Exception e){
            session.getTransaction().rollback();
            throw e;
        }finally {
            session.close();
        }

        flights.stream().forEach(flight -> {
            System.out.println("Flight ID: " + flight.getFlightId());
            System.out.println("Takeoff Country: " + flight.getTakeoffContry());
            System.out.println("Landing Country: " + flight.getLandingCountry());
            System.out.println("-----------------------------------");
        });

        return flights;

    }


    @Override
    public Flight getFlight(int flightId){
        Flight flight = null;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            flight = session.get(Flight.class, flightId);
            session.getTransaction().commit();

        }catch (Exception e){
            session.getTransaction().rollback();
            throw e;
        }finally {
            session.close();
        }

        return flight;

    }


    public List<Flight> Filter(String from , String to,Integer startPrice, Integer endPrice, String Airline) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            StringBuilder hql = new StringBuilder("FROM Flight f WHERE 1=1");
            if (from != null && !from.isEmpty()) {
                hql.append(" AND f.TakeoffContry = :from");
            }
            if (to != null && !to.isEmpty()) {
                hql.append(" AND f.LandingCountry = :to");
            }
            /*
            if (date != null) {
                hql.append(" AND f.startDate = :date");
            }

             */
            if (startPrice != null) {
                hql.append(" AND f.price >= :startPrice");
            }
            if (endPrice != null) {
                hql.append(" AND f.price <= :endPrice");
            }
            if (Airline != null && !Airline.isEmpty()) {
                hql.append(" AND f.Airline = :Airline");
            }

            var query = session.createQuery(hql.toString(), Flight.class);

            if (from != null && !from.isEmpty()) query.setParameter("from", from);
            if (to != null && !to.isEmpty()) query.setParameter("to", to);
           // if (date != null) query.setParameter("date", date);
            if (startPrice != null) query.setParameter("startPrice", startPrice);
            if (endPrice != null) query.setParameter("endPrice", endPrice);
            if (Airline != null && !Airline.isEmpty()) query.setParameter("Airline", Airline);

            List<Flight> flights = query.getResultList();
            session.getTransaction().commit();

            flights.stream().forEach(flight -> {
                System.out.println("ddppooiill");
                System.out.println("Flight ID: " + flight.getFlightId());
                System.out.println("Takeoff Country: " + flight.getTakeoffContry());
                System.out.println("Landing Country: " + flight.getLandingCountry());

            });
            return flights;
        } catch (Exception e) {
            throw new RuntimeException("Error filtering flights", e);
        }
    }

}

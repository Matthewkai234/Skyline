package database.interfaces;

import database.Flight;

import java.util.Date;
import java.util.List;

public interface SearshFlightDOA {
    public List<Flight> searchFlights(String origin, String LandingCountry,String date);

    public Flight getFlight(int flightId);

    public List<Flight> Filter(String from , String to ,  Integer startPrice, Integer endPrice, String airLine);
}
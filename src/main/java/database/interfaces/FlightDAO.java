package database.interfaces;

import model.FlightModel;

import java.util.List;

public interface FlightDAO {
    public List<FlightModel> searchFlights(String origin, String LandingCountry, String date);

    public FlightModel getFlight(int flightId);

    public List<FlightModel> Filter(String from , String to , Integer startPrice, Integer endPrice, String airLine);
}
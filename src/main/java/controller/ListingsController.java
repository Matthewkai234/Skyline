package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Flight;
import model.Hotel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ListingsController {
    @FXML
    private TabPane tabPane;

    @FXML
    private TableView<Flight> flightTable;

    @FXML
    private TableColumn<Flight, String> flightNumberColumn;

    @FXML
    private TableColumn<Flight, String> airlineColumn;

    @FXML
    private TableColumn<Flight, String> destinationColumn;

    @FXML
    private TableColumn<Flight, String> departureDateColumn;

    @FXML
    private TableColumn<Flight, String> arrivalDateColumn;

    @FXML
    private TableColumn<Flight, String> flightActionsColumn;

    @FXML
    private TableView<Hotel> hotelTable;

    @FXML
    private TableColumn<Hotel, Integer> hotelIdColumn;

    @FXML
    private TableColumn<Hotel, String> hotelNameColumn;

    @FXML
    private TableColumn<Hotel, String> locationColumn;

    @FXML
    private TableColumn<Hotel, String> hotelActionsColumn;

    private SessionFactory sessionFactory;

    @FXML
    public void initialize() {
        //Configure hibernate Session
        try{
            Configuration config = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Flight.class).addAnnotatedClass(Hotel.class);
            sessionFactory = config.buildSessionFactory();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            System.out.println("failed to create the session");
        }

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null) {
                String tabText = newTab.getText();
                if ("Hotels".equals(tabText)) {
                    loadHotelData();
                } else if ("Flights".equals(tabText)) {
                    loadFlightData();
                }
            }
        });
    }
    @FXML
    private void addListingWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddListing.fxml"));
            Parent root = loader.load();

            AddListingController addListingController = loader.getController();
            addListingController.setParentController(this);

            Stage stage = new Stage();
            stage.setTitle("Add New Listing");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void loadHotelData() {
        // Set up the columns
        hotelIdColumn.setCellValueFactory(new PropertyValueFactory<>("hotelId"));
        hotelNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        hotelActionsColumn.setCellValueFactory(new PropertyValueFactory<>("actions"));

        // Load data from database
        ObservableList<Hotel> hotelList = loadHotelsFromDatabase();

        // Set items to the table
        hotelTable.setItems(hotelList);

    }

    private ObservableList<Hotel> loadHotelsFromDatabase() {
        ObservableList<Hotel> hotels = FXCollections.observableArrayList();
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Hotel> criteriaQuery = builder.createQuery(Hotel.class);
            Root<Hotel> root = criteriaQuery.from(Hotel.class);
            criteriaQuery.select(root);
            List<Hotel> hotelData = session.createQuery(criteriaQuery).getResultList();
            hotels.addAll(hotelData);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return hotels;

    }

    public void loadFlightData() {
        flightNumberColumn.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        airlineColumn.setCellValueFactory(new PropertyValueFactory<>("airline"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        departureDateColumn.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        arrivalDateColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
        flightActionsColumn.setCellValueFactory(new PropertyValueFactory<>("actions"));

        ObservableList<Flight> flightList = loadFlightsFromDatabase();
        flightTable.setItems(flightList);
    }

    private ObservableList<Flight> loadFlightsFromDatabase() {
        ObservableList<Flight> flights = FXCollections.observableArrayList();
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Flight> criteriaQuery = builder.createQuery(Flight.class);
            Root<Flight> root = criteriaQuery.from(Flight.class);
            criteriaQuery.select(root);
            List<Flight> flightData = session.createQuery(criteriaQuery).getResultList();
            flights.addAll(flightData);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return flights;
    }
}
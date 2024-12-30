package controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Flight;
import model.Hotel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
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
    private TableColumn<Flight, HBox> flightActionsColumn; // Changed to HBox

    @FXML
    private TableView<Hotel> hotelTable;

    @FXML
    private TableColumn<Hotel, Integer> hotelIdColumn;

    @FXML
    private TableColumn<Hotel, String> hotelNameColumn;

    @FXML
    private TableColumn<Hotel, String> locationColumn;

    @FXML
    private TableColumn<Hotel, HBox> hotelActionsColumn;  // Changed to HBox

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

        // Handle flight actions
        flightActionsColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(createActionButtonsForFlight(param.getValue())));
        // Handle hotel actions
        hotelActionsColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(createActionButtonsForHotel(param.getValue())));

        // Load initial data based on the selected tab (or default to Flights)
        if(tabPane.getSelectionModel().getSelectedItem() != null){
            String initialTabText = tabPane.getSelectionModel().getSelectedItem().getText();
            if ("Hotels".equals(initialTabText)) {
                loadHotelData();
            } else if ("Flights".equals(initialTabText)) {
                loadFlightData();
            }
        }
        else { // default to loading Flights if there are no tabs
            loadFlightData();
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

    // Updated methods to return an HBox containing both buttons
    private HBox createActionButtonsForFlight(Flight flight) {
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white;");
        deleteButton.setOnAction(event -> deleteFlight(flight));

        Button editButton = new Button("Edit");
        editButton.setStyle("-fx-background-color: #00affa; -fx-text-fill: white;");

        HBox buttons = new HBox(5); // 5 pixel spacing between buttons
        buttons.getChildren().addAll(deleteButton, editButton);
        return buttons;
    }

    private HBox createActionButtonsForHotel(Hotel hotel) {
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white;");
        deleteButton.setOnAction(event -> deleteHotel(hotel));

        Button editButton = new Button("Edit");
        editButton.setStyle("-fx-background-color: #00affa; -fx-text-fill: white;");


        HBox buttons = new HBox(5);
        buttons.getChildren().addAll(deleteButton, editButton);
        return buttons;
    }

    private void deleteFlight(Flight flight) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(flight);
            transaction.commit();
            loadFlightData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void deleteHotel(Hotel hotel) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(hotel);
            transaction.commit();
            loadHotelData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    @FXML
    private void addListingWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddListing.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            AddListingController controller = loader.getController();
            controller.setParentController(this);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadHotelData() {
        // Set up the columns
        hotelIdColumn.setCellValueFactory(new PropertyValueFactory<>("hotelId"));
        hotelNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        // Load data from database
        ObservableList<Hotel> hotelList = loadHotelsFromDatabase();
        hotelActionsColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(createActionButtonsForHotel(param.getValue())));
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

        // Load data from database
        ObservableList<Flight> flightList = loadFlightsFromDatabase();
        flightActionsColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(createActionButtonsForFlight(param.getValue())));

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
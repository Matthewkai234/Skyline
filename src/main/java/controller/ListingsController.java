// Updated ListingsController.java
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
import model.AdminListingFlightModel;
import model.HotelsModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.List;

public class ListingsController {
    @FXML
    private TabPane tabPane;

    @FXML
    private TableView<AdminListingFlightModel> flightTable;

    @FXML
    private TableColumn<AdminListingFlightModel, String> airlineColumn;

    @FXML
    private TableColumn<AdminListingFlightModel, String> takeoffCountryColumn;

    @FXML
    private TableColumn<AdminListingFlightModel, String> landingCountryColumn;

    @FXML
    private TableColumn<AdminListingFlightModel, Integer> priceColumn;

    @FXML
    private TableColumn<AdminListingFlightModel, HBox> flightActionsColumn;

    @FXML
    private TableView<HotelsModel> hotelTable;

    @FXML
    private TableColumn<HotelsModel, Integer> hotelIdColumn;

    @FXML
    private TableColumn<HotelsModel, String> hotelNameColumn;

    @FXML
    private TableColumn<HotelsModel, String> locationColumn;

    @FXML
    private TableColumn<HotelsModel, Double> hotelPriceColumn;

    @FXML
    private TableColumn<HotelsModel, Float> hotelRateColumn;

    @FXML
    private TableColumn<HotelsModel, HBox> hotelActionsColumn;

    @FXML
    public void initialize() {
        // Bind actions to flight and hotel tables
        flightActionsColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(createActionButtonsForFlight(param.getValue())));
        hotelActionsColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(createActionButtonsForHotel(param.getValue())));

        // Load default tab data
        if (tabPane.getSelectionModel().getSelectedItem() != null) {
            String initialTabText = tabPane.getSelectionModel().getSelectedItem().getText();
            if ("Hotels".equals(initialTabText)) {
                loadHotelData();
            } else {
                loadFlightData();
            }
        } else {
            loadFlightData();
        }

        // Add listener for tab changes
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null) {
                String tabText = newTab.getText();
                if ("Hotels".equals(tabText)) {
                    loadHotelData();
                } else {
                    loadFlightData();
                }
            }
        });
    }

    private HBox createActionButtonsForFlight(AdminListingFlightModel flight) {
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white;");
        deleteButton.setOnAction(event -> deleteFlight(flight));

        Button editButton = new Button("Edit");
        editButton.setStyle("-fx-background-color: #00affa; -fx-text-fill: white;");
        editButton.setOnAction(event -> openFlightEditor(flight));

        HBox buttons = new HBox(5);
        buttons.getChildren().addAll(deleteButton, editButton);
        return buttons;
    }

    private HBox createActionButtonsForHotel(HotelsModel hotel) {
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white;");
        deleteButton.setOnAction(event -> deleteHotel(hotel));

        Button editButton = new Button("Edit");
        editButton.setStyle("-fx-background-color: #00affa; -fx-text-fill: white;");
        editButton.setOnAction(event -> openHotelEditor(hotel));

        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(deleteButton, editButton);
        return buttons;
    }

    private void openFlightEditor(AdminListingFlightModel flight) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditFlight.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            EditFlightController controller = loader.getController();
            controller.setFlight(flight);
            controller.setParentController(this);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void openHotelEditor(HotelsModel hotel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditHotel.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            EditHotelController controller = loader.getController();
            controller.setHotel(hotel);
            controller.setParentController(this);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteFlight(AdminListingFlightModel flight) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(flight);
            transaction.commit();
            loadFlightData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteHotel(HotelsModel hotel) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(hotel);
            transaction.commit();
            loadHotelData();
        } catch (Exception e) {
            e.printStackTrace();
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

    void loadHotelData() {
        hotelIdColumn.setCellValueFactory(new PropertyValueFactory<>("hotelId"));
        hotelNameColumn.setCellValueFactory(new PropertyValueFactory<>("hotelName"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        hotelPriceColumn.setCellValueFactory(new PropertyValueFactory<>("hotelPrice"));
        hotelRateColumn.setCellValueFactory(new PropertyValueFactory<>("hotelRate"));

        ObservableList<HotelsModel> hotelList = loadHotelsFromDatabase();
        hotelTable.setItems(hotelList);
    }

    private ObservableList<HotelsModel> loadHotelsFromDatabase() {
        ObservableList<HotelsModel> hotels = FXCollections.observableArrayList();
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<HotelsModel> query = builder.createQuery(HotelsModel.class);
            Root<HotelsModel> root = query.from(HotelsModel.class);
            query.select(root);

            List<HotelsModel> hotelList = session.createQuery(query).getResultList();
            hotels.addAll(hotelList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hotels;
    }

    public void loadFlightData() {
        airlineColumn.setCellValueFactory(new PropertyValueFactory<>("airline"));
        takeoffCountryColumn.setCellValueFactory(new PropertyValueFactory<>("takeoffCountry"));
        landingCountryColumn.setCellValueFactory(new PropertyValueFactory<>("landingCountry"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        ObservableList<AdminListingFlightModel> flightList = loadFlightsFromDatabase();
        flightTable.setItems(flightList);
    }

    private ObservableList<AdminListingFlightModel> loadFlightsFromDatabase() {
        ObservableList<AdminListingFlightModel> flights = FXCollections.observableArrayList();
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<AdminListingFlightModel> criteriaQuery = builder.createQuery(AdminListingFlightModel.class);
            Root<AdminListingFlightModel> root = criteriaQuery.from(AdminListingFlightModel.class);
            criteriaQuery.select(root);

            List<AdminListingFlightModel> flightData = session.createQuery(criteriaQuery).getResultList();
            flights.addAll(flightData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flights;
    }
}

package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.AdminListingHotelModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class EditHotelController {

    @FXML
    private TextField hotelIdField;
    @FXML
    private TextField hotelNameField;
    @FXML
    private TextField locationField;
    @FXML
    private Button saveButton;
    private AdminListingHotelModel hotel;
    private ListingsController parentController;
    private SessionFactory sessionFactory;


    public void setParentController(ListingsController parentController) {
        this.parentController = parentController;
    }

    public void setHotel(AdminListingHotelModel hotel) {
        this.hotel = hotel;
        loadHotelData();
    }

    @FXML
    public void initialize() {
        try {
            Configuration config = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(AdminListingHotelModel.class);
            sessionFactory = config.buildSessionFactory();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("failed to create the session");
        }
        saveButton.setOnAction(event -> saveEdit());
    }

    private void loadHotelData() {
        if (hotel != null) {
            hotelIdField.setText(String.valueOf(hotel.getHotelId()));
            hotelNameField.setText(hotel.getName());
            locationField.setText(hotel.getLocation());
        }
    }

    private void saveEdit() {
        updateHotel();
        closeWindow();

    }
    private void updateHotel() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            hotel.setName(hotelNameField.getText());
            hotel.setLocation(locationField.getText());
            session.update(hotel);
            transaction.commit();
            parentController.loadHotelData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
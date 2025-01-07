package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.HotelsModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class EditHotelController {

    @FXML
    private TextField hotelNameField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField rateField;

    @FXML
    private Button saveButton;

    private HotelsModel hotel;

    private ListingsController parentController;

    public void setHotel(HotelsModel hotel) {
        this.hotel = hotel;
        loadHotelData();
    }

    public void setParentController(ListingsController parentController) {
        this.parentController = parentController;
    }

    private void loadHotelData() {
        if (hotel != null) {
            hotelNameField.setText(hotel.getHotelName());
            locationField.setText(hotel.getLocation());
            priceField.setText(String.valueOf(hotel.getHotelPrice()));
            rateField.setText(String.valueOf(hotel.getHotelRate()));
        }
    }

    @FXML
    private void saveEdit() {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Check and reattach the hotel object if necessary
            HotelsModel managedHotel = session.find(HotelsModel.class, hotel.getHotelId());
            if (managedHotel != null) {
                managedHotel.setHotelName(hotelNameField.getText());
                managedHotel.setLocation(locationField.getText());
                managedHotel.setHotelPrice(Double.parseDouble(priceField.getText()));
                managedHotel.setHotelRate(Float.parseFloat(rateField.getText()));
                session.update(managedHotel);
            }

            transaction.commit();
            parentController.loadHotelData();
            closeWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}

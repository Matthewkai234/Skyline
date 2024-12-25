package controller;

import database.Hotels;
import database.repositories.HotelsService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class page2ghcontroller {

    @FXML private Label hotelNameLabel1;
    @FXML private Label hotelPriceLabel1;
    @FXML private Label hotelLocationLabel1;

    @FXML private Label hotelNameLabel2;
    @FXML private Label hotelPriceLabel2;
    @FXML private Label hotelLocationLabel2;

    @FXML private Label hotelNameLabel3;
    @FXML private Label hotelPriceLabel3;
    @FXML private Label hotelLocationLabel3;

    @FXML private Label hotelNameLabel4;
    @FXML private Label hotelPriceLabel4;
    @FXML private Label hotelLocationLabel4;

    @FXML private Label hotelNameLabel5;
    @FXML private Label hotelPriceLabel5;
    @FXML private Label hotelLocationLabel5;

    @FXML private Label hotelNameLabel6;
    @FXML private Label hotelPriceLabel6;
    @FXML private Label hotelLocationLabel6;

    @FXML private Label hotelNameLabel7;
    @FXML private Label hotelPriceLabel7;
    @FXML private Label hotelLocationLabel7;

    private HotelsService hotelsService;

    public page2ghcontroller() {
        this.hotelsService = new HotelsService();
    }

    @FXML
    private void initialize() {
        loadHotelData();
    }
    int hotelsLimit = 7;
    private void loadHotelData() {
        List<Hotels> hotelsList = hotelsService.getHotelsList(hotelsLimit);
        if (hotelsList != null && hotelsList.size() >= 7) {
            setHotelData(hotelNameLabel1, hotelPriceLabel1, hotelLocationLabel1, hotelsList.get(0));
            setHotelData(hotelNameLabel2, hotelPriceLabel2, hotelLocationLabel2, hotelsList.get(1));
            setHotelData(hotelNameLabel3, hotelPriceLabel3, hotelLocationLabel3, hotelsList.get(2));
            setHotelData(hotelNameLabel4, hotelPriceLabel4, hotelLocationLabel4, hotelsList.get(3));
            setHotelData(hotelNameLabel5, hotelPriceLabel5, hotelLocationLabel5, hotelsList.get(4));
            setHotelData(hotelNameLabel6, hotelPriceLabel6, hotelLocationLabel6, hotelsList.get(5));
            setHotelData(hotelNameLabel7, hotelPriceLabel7, hotelLocationLabel7, hotelsList.get(6));
        }
    }

    private void setHotelData(Label nameLabel, Label priceLabel, Label locationLabel, Hotels hotel) {
        if (hotel != null) {
            nameLabel.setText(hotel.getHotelName());
            priceLabel.setText(String.valueOf(hotel.getHotelPrice()));
            locationLabel.setText(hotel.getLocation());
        }
    }

    @FXML
    private void handleMoreButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/page3gh.fxml"));
            Parent newPage = loader.load();

            Scene newScene = new Scene(newPage);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            currentStage.setScene(newScene);
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleReserveButtonAction(ActionEvent actionEvent) throws IOException {
        loadNewWindow("page3gh.fxml");
    }

    private void loadNewWindow(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + fxmlFile));
            Node content = loader.load();
            Stage newStage = new Stage();
            Scene scene = new Scene((Parent) content);
            newStage.setScene(scene);
            newStage.setTitle("Skyline");
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

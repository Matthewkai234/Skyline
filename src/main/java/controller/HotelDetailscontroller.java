package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Locale;

public class HotelDetailscontroller {
    @FXML
    private Label hotelNameLabel;
    @FXML
    private Label hotelPriceLabel;
    @FXML
    private Label hotelRateLabel;
    @FXML
    private Label hotelLocationLabel;

    private String Location;

    public void setHotelData(String name, double price, double rate, String location) {
        if (hotelNameLabel != null && hotelPriceLabel != null && hotelRateLabel != null && hotelLocationLabel != null) {
            hotelNameLabel.setText(name);
            hotelPriceLabel.setText(String.valueOf(price));

            DecimalFormat df = new DecimalFormat("#.##", new java.text.DecimalFormatSymbols(Locale.US));
            String formattedRate = df.format(rate);
            hotelRateLabel.setText(formattedRate);

            hotelLocationLabel.setText(location);

            this.Location = location;
        } else {
            System.out.println("Error");
        }
    }

    @FXML
    private void handleReserveButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BookingHotel.fxml"));
            Parent newPage = loader.load();

            BookingHotelcontroller page4Controller = loader.getController();

//            page4Controller.setHotelName(this.hotelNameLabel.getText());

            Scene newScene = new Scene(newPage);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HotelsResults.fxml"));
            Parent newPage = loader.load();

            HotelsResultscontroller page2Controller = loader.getController();

            page2Controller.setLocation(this.Location);
            page2Controller.loadHotelData();

            Scene newScene = new Scene(newPage);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
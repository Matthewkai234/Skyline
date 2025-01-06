package controller;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;
import model.Hotels;
import database.services.HotelsService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.text.DecimalFormat;
import java.util.Locale;

public class HotelsResultscontroller {
    private int totalResults = 0;
    private int currentPage = 0;
    private static final int RESULTS_PER_PAGE = 8;
    private String Location;

    @FXML
    private Button moreResultsButton;
    @FXML
    private Button backButton;

    public void setLocation(String location) {
        this.Location = location;
        loadHotelData();
    }

    @FXML
    private AnchorPane hotelCard1;
    @FXML
    private AnchorPane hotelCard2;
    @FXML
    private AnchorPane hotelCard3;
    @FXML
    private AnchorPane hotelCard4;
    @FXML
    private AnchorPane hotelCard5;
    @FXML
    private AnchorPane hotelCard6;
    @FXML
    private AnchorPane hotelCard7;
    @FXML
    private AnchorPane hotelCard8;

    @FXML
    private ComboBox<String> ratecomboBox;
    @FXML
    private ComboBox<String> pricecomboBox;
    @FXML
    private Label hotelNameLabel1;
    @FXML
    private Label hotelRateLabel1;
    @FXML
    private Label hotelPriceLabel1;
    @FXML
    private Label hotelLocationLabel1;
    @FXML
    private Label hotelNameLabel2;
    @FXML
    private Label hotelRateLabel2;
    @FXML
    private Label hotelPriceLabel2;
    @FXML
    private Label hotelLocationLabel2;
    @FXML
    private Label hotelNameLabel3;
    @FXML
    private Label hotelRateLabel3;
    @FXML
    private Label hotelPriceLabel3;
    @FXML
    private Label hotelLocationLabel3;
    @FXML
    private Label hotelNameLabel4;
    @FXML
    private Label hotelRateLabel4;
    @FXML
    private Label hotelPriceLabel4;
    @FXML
    private Label hotelLocationLabel4;
    @FXML
    private Label hotelNameLabel5;
    @FXML
    private Label hotelRateLabel5;
    @FXML
    private Label hotelPriceLabel5;
    @FXML
    private Label hotelLocationLabel5;
    @FXML
    private Label hotelNameLabel6;
    @FXML
    private Label hotelRateLabel6;
    @FXML
    private Label hotelPriceLabel6;
    @FXML
    private Label hotelLocationLabel6;
    @FXML
    private Label hotelNameLabel7;
    @FXML
    private Label hotelRateLabel7;
    @FXML
    private Label hotelPriceLabel7;
    @FXML
    private Label hotelLocationLabel7;
    @FXML
    private Label hotelNameLabel8;
    @FXML
    private Label hotelRateLabel8;
    @FXML
    private Label hotelPriceLabel8;
    @FXML
    private Label hotelLocationLabel8;

    private HotelsService hotelsService;
    @FXML
    private Button moreButton1;
    @FXML
    private Button moreButton2;
    @FXML
    private Button moreButton3;
    @FXML
    private Button moreButton4;
    @FXML
    private Button moreButton5;
    @FXML
    private Button moreButton6;
    @FXML
    private Button moreButton7;
    @FXML
    private Button moreButton8;

    public HotelsResultscontroller() {
        this.hotelsService = new HotelsService();
    }

    @FXML
    private void handleMoreResultsButtonAction(ActionEvent event) {
        currentPage++;
        loadMoreResults();
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        if (currentPage > 0) {

            currentPage = 0;
            loadMoreResults();
        } else {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error loading the main page.");
            }
        }
    }

    private List<Hotels> currentHotelsList;

    private void loadMoreResults() {
        if (currentHotelsList == null || currentHotelsList.isEmpty()) {
            return;
        }

        int startIndex = currentPage * RESULTS_PER_PAGE;
        int endIndex = Math.min(startIndex + RESULTS_PER_PAGE, currentHotelsList.size());

        for (int i = 0; i < RESULTS_PER_PAGE; i++) {
            AnchorPane hotelCard = getHotelCard(i);
            if (hotelCard != null) {
                hotelCard.setVisible(false);
            }
        }

        for (int i = startIndex; i < endIndex; i++) {
            Hotels hotel = currentHotelsList.get(i);
            int cardIndex = i % RESULTS_PER_PAGE;
            Label nameLabel = getHotelNameLabel(cardIndex);
            Label priceLabel = getHotelPriceLabel(cardIndex);
            Label locationLabel = getHotelLocationLabel(cardIndex);
            Label rateLabel = getHotelRateLabel(cardIndex);
            AnchorPane hotelCard = getHotelCard(cardIndex);

            if (nameLabel != null && priceLabel != null && locationLabel != null && rateLabel != null && hotelCard != null) {
                setHotelData(nameLabel, priceLabel, locationLabel, rateLabel, hotel);
                hotelCard.setVisible(true);
            }
        }

        updateButtonsVisibility();
    }

    private void updateButtonsVisibility() {
        if (moreResultsButton != null) {
            if (currentHotelsList == null || currentHotelsList.size() <= (currentPage + 1) * RESULTS_PER_PAGE) {
                moreResultsButton.setVisible(false);
            } else {
                moreResultsButton.setVisible(true);
            }
        }

        if (backButton != null) {
            backButton.setVisible(true);
        }
    }

    @FXML
    private void handleFilterButtonAction(ActionEvent event) {
        currentPage = 0;
        String selectedRate = ratecomboBox.getEditor().getText();
        String selectedPrice = pricecomboBox.getEditor().getText();

        currentHotelsList = filterHotels(selectedRate, selectedPrice);
        totalResults = currentHotelsList != null ? currentHotelsList.size() : 0;

        displayHotels(currentHotelsList);

        updateButtonsVisibility();
    }

    private List<Hotels> filterHotels(String selectedRate, String selectedPrice) {
        List<Hotels> allHotels = hotelsService.getHotelsListByLocation(Location);
        List<Hotels> filteredHotels = new ArrayList<>();
        double tolerance = 0.01;

        for (Hotels hotel : allHotels) {
            boolean matchesRate = true;
            if (selectedRate != null && !selectedRate.isEmpty()) {
                try {
                    if (selectedRate.equals("1 to 3")) {
                        matchesRate = hotel.getHotelRate() >= 1 && hotel.getHotelRate() <= 3;
                    } else if (selectedRate.equals("3 to 5")) {
                        matchesRate = hotel.getHotelRate() >= 3 && hotel.getHotelRate() <= 5;
                    } else {
                        double rate = Double.parseDouble(selectedRate);
                        matchesRate = Math.abs(hotel.getHotelRate() - rate) < tolerance;
                    }
                } catch (NumberFormatException e) {

                    matchesRate = true;
                }
            }

            boolean matchesPrice = true;
            if (selectedPrice != null && !selectedPrice.isEmpty()) {
                try {
                    if (selectedPrice.equals("100 or less")) {
                        matchesPrice = hotel.getHotelPrice() <= 100;
                    } else if (selectedPrice.equals("100 to 300")) {
                        matchesPrice = hotel.getHotelPrice() > 100 && hotel.getHotelPrice() <= 300;
                    } else if (selectedPrice.equals("Above 300")) {
                        matchesPrice = hotel.getHotelPrice() > 300;
                    } else {
                        double price = Double.parseDouble(selectedPrice);
                        matchesPrice = Math.abs(hotel.getHotelPrice() - price) < tolerance;
                    }
                } catch (NumberFormatException e) {
                    matchesPrice = true;
                }
            }

            if (matchesRate && matchesPrice) {
                filteredHotels.add(hotel);
            }
        }

        return filteredHotels;
    }

    private void setLabelVisibility(Label nameLabel, Label priceLabel, Label locationLabel, Label rateLabel, boolean visible) {
        nameLabel.setVisible(visible);
        priceLabel.setVisible(visible);
        locationLabel.setVisible(visible);
        rateLabel.setVisible(visible);
    }

    private void displayFilteredHotels(List<Hotels> filteredHotels) {
        int maxHotelsToShow = Math.min(filteredHotels != null ? filteredHotels.size() : 0, RESULTS_PER_PAGE);

        for (int i = 0; i < RESULTS_PER_PAGE; i++) {
            AnchorPane hotelCard = getHotelCard(i);
            if (hotelCard != null) {
                hotelCard.setVisible(false);
            }
        }

        for (int i = 0; i < maxHotelsToShow; i++) {
            Hotels hotel = filteredHotels.get(i);
            Label nameLabel = getHotelNameLabel(i);
            Label priceLabel = getHotelPriceLabel(i);
            Label locationLabel = getHotelLocationLabel(i);
            Label rateLabel = getHotelRateLabel(i);
            AnchorPane hotelCard = getHotelCard(i);

            if (nameLabel != null && priceLabel != null && locationLabel != null && rateLabel != null && hotelCard != null) {
                setHotelData(nameLabel, priceLabel, locationLabel, rateLabel, hotel);
                hotelCard.setVisible(true);
            }
        }

        if (maxHotelsToShow >= totalResults) {
            if (moreResultsButton != null) {
                moreResultsButton.setVisible(false);
            }
        } else {
            if (moreResultsButton != null) {
                moreResultsButton.setVisible(true);
            }
        }
    }

    @FXML
    private void initialize() {
        ratecomboBox.setItems(FXCollections.observableArrayList(
                "1 to 3",
                "3 to 5"
        ));

        pricecomboBox.setItems(FXCollections.observableArrayList(
                "100 or less",
                "100 to 300",
                "Above 300"
        ));

        moreButton1.setOnAction(event -> {
            Hotels hotel = getHotelData(0);
            handleMoreButtonAction(event, hotel);
        });

        moreButton2.setOnAction(event -> {
            Hotels hotel = getHotelData(1);
            handleMoreButtonAction(event, hotel);
        });

        moreButton3.setOnAction(event -> {
            Hotels hotel = getHotelData(2);
            handleMoreButtonAction(event, hotel);
        });

        moreButton4.setOnAction(event -> {
            Hotels hotel = getHotelData(3);
            handleMoreButtonAction(event, hotel);
        });

        moreButton5.setOnAction(event -> {
            Hotels hotel = getHotelData(4);
            handleMoreButtonAction(event, hotel);
        });

        moreButton6.setOnAction(event -> {
            Hotels hotel = getHotelData(5);
            handleMoreButtonAction(event, hotel);
        });

        moreButton7.setOnAction(event -> {
            Hotels hotel = getHotelData(6);
            handleMoreButtonAction(event, hotel);
        });

        moreButton8.setOnAction(event -> {
            Hotels hotel = getHotelData(7);
            handleMoreButtonAction(event, hotel);
        });
    }

    private Hotels getHotelData(int index) {
        if (currentHotelsList != null && index < currentHotelsList.size()) {
            return currentHotelsList.get(index);
        }
        return null;
    }

    public void loadHotelData() {
        currentHotelsList = hotelsService.getHotelsListByLocation(Location);
        totalResults = currentHotelsList != null ? currentHotelsList.size() : 0;

        if (currentHotelsList != null && !currentHotelsList.isEmpty()) {
            int endIndex = Math.min(RESULTS_PER_PAGE, currentHotelsList.size());

            displayHotels(currentHotelsList.subList(0, endIndex));

            updateButtonsVisibility();
        }
    }

    private void displayHotels(List<Hotels> hotels) {
        for (int i = 0; i < RESULTS_PER_PAGE; i++) {
            AnchorPane hotelCard = getHotelCard(i);
            if (hotelCard != null) {
                hotelCard.setVisible(false);
            }
        }

        for (int i = 0; i < hotels.size(); i++) {
            Hotels hotel = hotels.get(i);
            Label nameLabel = getHotelNameLabel(i);
            Label priceLabel = getHotelPriceLabel(i);
            Label locationLabel = getHotelLocationLabel(i);
            Label rateLabel = getHotelRateLabel(i);
            AnchorPane hotelCard = getHotelCard(i);

            if (nameLabel != null && priceLabel != null && locationLabel != null && rateLabel != null && hotelCard != null) {
                setHotelData(nameLabel, priceLabel, locationLabel, rateLabel, hotel);
                hotelCard.setVisible(true);
            }
        }
    }

    @FXML
    private void handleMoreButtonAction(ActionEvent event, Hotels hotel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HotelDetails.fxml"));
            Parent newPage = loader.load();

            HotelDetailscontroller nextPageController = loader.getController();

            nextPageController.setHotelData(hotel.getHotelName(), hotel.getHotelPrice(), hotel.getHotelRate(), hotel.getLocation());

            Scene newScene = new Scene(newPage);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Label getHotelNameLabel(int index) {
        switch (index) {
            case 0: return hotelNameLabel1;
            case 1: return hotelNameLabel2;
            case 2: return hotelNameLabel3;
            case 3: return hotelNameLabel4;
            case 4: return hotelNameLabel5;
            case 5: return hotelNameLabel6;
            case 6: return hotelNameLabel7;
            case 7: return hotelNameLabel8;
            default: return null;
        }
    }

    private Label getHotelPriceLabel(int index) {
        switch (index) {
            case 0: return hotelPriceLabel1;
            case 1: return hotelPriceLabel2;
            case 2: return hotelPriceLabel3;
            case 3: return hotelPriceLabel4;
            case 4: return hotelPriceLabel5;
            case 5: return hotelPriceLabel6;
            case 6: return hotelPriceLabel7;
            case 7: return hotelPriceLabel8;
            default: return null;
        }
    }

    private Label getHotelLocationLabel(int index) {
        switch (index) {
            case 0: return hotelLocationLabel1;
            case 1: return hotelLocationLabel2;
            case 2: return hotelLocationLabel3;
            case 3: return hotelLocationLabel4;
            case 4: return hotelLocationLabel5;
            case 5: return hotelLocationLabel6;
            case 6: return hotelLocationLabel7;
            case 7: return hotelLocationLabel8;
            default: return null;
        }
    }

    private AnchorPane getHotelCard(int index) {
        switch (index) {
            case 0: return hotelCard1;
            case 1: return hotelCard2;
            case 2: return hotelCard3;
            case 3: return hotelCard4;
            case 4: return hotelCard5;
            case 5: return hotelCard6;
            case 6: return hotelCard7;
            case 7: return hotelCard8;
            default: return null;
        }
    }

    private Label getHotelRateLabel(int index) {
        switch (index) {
            case 0: return hotelRateLabel1;
            case 1: return hotelRateLabel2;
            case 2: return hotelRateLabel3;
            case 3: return hotelRateLabel4;
            case 4: return hotelRateLabel5;
            case 5: return hotelRateLabel6;
            case 6: return hotelRateLabel7;
            case 7: return hotelRateLabel8;
            default: return null;
        }
    }

    private void setHotelData(Label nameLabel, Label priceLabel, Label locationLabel, Label rateLabel, Hotels hotel) {
        if (hotel != null) {
            nameLabel.setText(hotel.getHotelName());
            priceLabel.setText(String.valueOf(hotel.getHotelPrice()));
            locationLabel.setText(hotel.getLocation());

            DecimalFormat df = new DecimalFormat("#.##", new java.text.DecimalFormatSymbols(Locale.US));
            String formattedRate = df.format(hotel.getHotelRate());
            rateLabel.setText(formattedRate);
        } else {
            nameLabel.setText("");
            priceLabel.setText("");
            locationLabel.setText("");
            rateLabel.setText("");
        }
    }

    @FXML
    public void handleReserveButtonAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BookingHotel.fxml"));
            Parent newPage = loader.load();

            BookingHotelcontroller page4Controller = loader.getController();

//            page4Controller.setLocation(this.Location);

            Scene currentScene = ((Node) actionEvent.getSource()).getScene();
            currentScene.setRoot(newPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
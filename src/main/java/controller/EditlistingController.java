package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import java.io.File;

public class EditlistingController {

    @FXML private ComboBox<String> listingTypeComboBox;
    @FXML private TextField nameField;
    @FXML private TextArea descriptionField;
    @FXML private TextField promoOfferField;
    @FXML private TextField priceField;
    @FXML private Label successMessageLabel;
    @FXML private TextField locationField;
    @FXML private DatePicker takeOffDateField;
    @FXML private DatePicker arrivalDateField;
    @FXML private VBox hotelFieldsContainer;
    @FXML private VBox flightFieldsContainer;
    @FXML private Button uploadImageButton;
    @FXML private ImageView uploadedImageView;

    private File selectedImageFile;

    @FXML
    private void initialize() {
        listingTypeComboBox.setOnAction(event -> toggleFields());

        // Ensure only "Hotel" and "Flight" options are present
        listingTypeComboBox.getItems().clear();
        listingTypeComboBox.getItems().addAll("Hotel", "Flight");

        hotelFieldsContainer.setVisible(false);
        flightFieldsContainer.setVisible(false);
    }

    @FXML
    private void resetFormFields(ActionEvent event) {
        uploadedImageView.setImage(null);
        selectedImageFile = null;
        listingTypeComboBox.getSelectionModel().clearSelection();
        nameField.clear();
        descriptionField.clear();
        promoOfferField.clear();
        priceField.clear();
        locationField.clear();
        takeOffDateField.setValue(null);
        arrivalDateField.setValue(null);
        hotelFieldsContainer.setVisible(false);
        flightFieldsContainer.setVisible(false);
        successMessageLabel.setVisible(false);
    }



    private void toggleFields() {
        String listingType = listingTypeComboBox.getValue();

        if ("Hotel".equals(listingType)) {
            hotelFieldsContainer.setVisible(true);
            flightFieldsContainer.setVisible(false);
        } else if ("Flight".equals(listingType)) {
            flightFieldsContainer.setVisible(true);
            hotelFieldsContainer.setVisible(false);
        } else {
            hotelFieldsContainer.setVisible(false);
            flightFieldsContainer.setVisible(false);
        }
    }

    @FXML
    private void handleUploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        selectedImageFile = fileChooser.showOpenDialog(uploadImageButton.getScene().getWindow());

        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            uploadedImageView.setImage(image);
        }
    }
    @FXML
    private void handleBack(ActionEvent event) {
        // Logic to go back to the previous screen
        System.out.println("Back button clicked!");
    }


    @FXML
    private void handleSaveChanges(ActionEvent event) {
        String listingType = listingTypeComboBox.getValue();

        if (listingType == null) {
            showAlert("Validation Error", "Please select a listing type.");
            return;
        }

        String name = nameField.getText();
        String description = descriptionField.getText();
        String promoOffer = promoOfferField.getText();
        String price = priceField.getText();

        if (name.isEmpty() || price.isEmpty()) {
            showAlert("Validation Error", "Name and Price cannot be empty.");
            return;
        }

        if (!isNumeric(price)) {
            showAlert("Validation Error", "Price must be a valid number.");
            return;
        }

        // Print listing information to the console
        System.out.println("--- Updated Listing Information ---");
        System.out.println("Listing Type: " + listingType);
        System.out.println("Name/Number: " + name);
        System.out.println("Description: " + description);
        System.out.println("Promotional Offer: " + promoOffer);
        System.out.println("Price: " + price);

        if (selectedImageFile != null) {
            System.out.println("Updated Image: " + selectedImageFile.getAbsolutePath());
        }

        // Show a confirmation popup
        showAlert("Success", "Listing updated successfully!");

        successMessageLabel.setText("Listing updated successfully!");
        successMessageLabel.setStyle("-fx-text-fill: green;");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }}

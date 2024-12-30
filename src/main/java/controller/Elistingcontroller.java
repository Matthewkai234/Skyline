package controller;
import database.repositories.ListingDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Listing;
import java.io.File;

public class Elistingcontroller {

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
    private ListingDAO listingDAO = new ListingDAO();
    private Long listingId; // ID of the listing to edit (passed from another screen)

    @FXML
    private void initialize() {
        listingTypeComboBox.setOnAction(event -> toggleFields());
        listingTypeComboBox.getItems().clear();
        listingTypeComboBox.getItems().addAll("Hotel", "Flight");
        hotelFieldsContainer.setVisible(false);
        flightFieldsContainer.setVisible(false);
    }

    public void setListingId(Long id) {
        this.listingId = id;
        loadListingData(); // Load data for the listing when ID is set
    }

    private void loadListingData() {
        // Fetch the listing using the ID
        Listing listing = listingDAO.getListingById(listingId);

        if (listing != null) {
            // Fill the form fields with the data
            listingTypeComboBox.setValue(listing.getListingType());
            nameField.setText(listing.getName());
            descriptionField.setText(listing.getDescription());
            promoOfferField.setText(listing.getPromoOffer());
            priceField.setText(String.valueOf(listing.getPrice()));
            locationField.setText(listing.getLocation());
            takeOffDateField.setValue(listing.getTakeOffDate());
            arrivalDateField.setValue(listing.getArrivalDate());

            if (listing.getImagePath() != null) {
                Image image = new Image("file:" + listing.getImagePath());
                uploadedImageView.setImage(image);
            }

            toggleFields(); // Show relevant fields based on the listing type
        } else {
            showAlert("Error", "Listing not found.");
        }
    }

    public void handleExitButtonAction(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
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
        String priceText = priceField.getText();

        if (name.isEmpty() || priceText.isEmpty()) {
            showAlert("Validation Error", "Name and Price cannot be empty.");
            return;
        }

        if (!isNumeric(priceText)) {
            showAlert("Validation Error", "Price must be a valid number.");
            return;
        }

        Double price = Double.parseDouble(priceText);

        // Fetch the existing listing from the database
        Listing listing = listingDAO.getListingById(listingId);

        if (listing != null) {
            // Update the Listing object with the new values
            listing.setListingType(listingType);
            listing.setName(name);
            listing.setDescription(description);
            listing.setPromoOffer(promoOffer);
            listing.setPrice(price);
            listing.setLocation(locationField.getText());
            listing.setTakeOffDate(takeOffDateField.getValue());
            listing.setArrivalDate(arrivalDateField.getValue());

            if (selectedImageFile != null) {
                listing.setImagePath(selectedImageFile.getAbsolutePath());
            }

            // Save changes to the database using the DAO
            listingDAO.updateListing(listing);

            // Show a success message
            showAlert("Success", "Listing updated successfully!");
            successMessageLabel.setText("Listing updated successfully!");
            successMessageLabel.setStyle("-fx-text-fill: green;");
        } else {
            showAlert("Error", "Listing not found for update.");
        }
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
    }
}

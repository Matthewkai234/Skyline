package controller;

import application.SessionManager;
import database.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SidebarController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private HBox contentArea;

    @FXML
    private HBox listingsButtonBox; // VBox containing the Listings button

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adjustSidebarForRole();
        loadHomePage();
    }

    @FXML
    public void home(ActionEvent actionEvent) {
        loadHomePage();
    }

    @FXML
    public void bookings(ActionEvent actionEvent) throws IOException {
        loadBookings();
    }

    @FXML
    public void hotels(ActionEvent actionEvent) throws IOException {
        loadHotels();
    }

    @FXML
    public void listings(ActionEvent actionEvent) throws IOException {
        loadListings();
    }

    @FXML
    public void login(ActionEvent actionEvent) throws IOException {
        loadLogin();
    }

    @FXML
    public void flights(ActionEvent actionEvent) throws IOException {
        loadFlights();
    }

    private void loadListings() {
        loadPage("listings-view.fxml");
    }

    private void loadHotels() {
        loadPage("SearchHotel.fxml");
    }

    private void loadBookings() {
        loadPage("bookings.fxml");
    }

    private void loadLogin() {
        loadPageLogout("login_page.fxml");
    }

    private void loadFlights() {
        loadPage("SearchFlightBigFinal.fxml");
    }

    private void loadHomePage() {
        if (isUserAdmin()) {
            loadPage("adminHome.fxml");
        } else {
            loadPage("AgentHome.fxml");
        }
    }

    private void adjustSidebarForRole() {
        if (!isUserAdmin()) {
            listingsButtonBox.setVisible(false); // Hide Listings button for non-admins
        }
    }

    private void loadPage(String fxmlFile) {
        try {
            Node content = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/" + fxmlFile)));
            contentArea.getChildren().clear();
            contentArea.getChildren().add(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPageLogout(String fxmlFile) {
        try {
            SessionManager.getInstance().logout();
            Stage currentStage = (Stage) contentArea.getScene().getWindow();
            currentStage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + fxmlFile));
            Node content = loader.load();
            Stage newStage = new Stage();
            Scene newScene = new Scene((Parent) content);
            newStage.setScene(newScene);
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isUserAdmin() {
        Users loggedInUser = SessionManager.getInstance().getLoggedInUser();
        return loggedInUser != null && "Admin".equals(loggedInUser.getRole());
    }
}

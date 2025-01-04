package controller;

import application.SkylineApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SidebarController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private HBox contentArea;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadHome();
    }

    @FXML
    public void home(ActionEvent actionEvent) throws IOException {
        loadHome();
    }

    public void bookings(ActionEvent actionEvent) throws IOException {
        loadBookings();
    }

    public void hotels(ActionEvent actionEvent) throws IOException {
        loadHotels();
    }

    public void adminPanel(ActionEvent actionEvent) throws IOException {
        loadAdminPanel();
    }

    public void listings(ActionEvent actionEvent) throws IOException {
        loadListings();
    }

    public void login(ActionEvent actionEvent) throws IOException {
        loadLogin();
    }
    public void flights(ActionEvent actionEvent) throws IOException {
        loadFlights();
    }


    public void loadHome() {
        loadPage("HomeDashboard.fxml");
    }
    public void loadListings() {
        loadPage("listings-view.fxml");
    }
    public void loadHotels(){loadPage("page1gh.fxml");}
    public void loadBookings(){
        loadPage("bookings.fxml");
    }
    public void loadAdminPanel(){loadPage("adminHome.fxml");}
    public void loadLogin(){loadPageLogout("login_page.fxml");}
    public void loadFlights(){loadPage("SearchFlightBigFinal.fxml");}



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
}



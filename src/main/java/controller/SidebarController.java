package controller;

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
        loadHome();  // Load the home page when the "Home" button is clicked
    }

    public void bookings(ActionEvent actionEvent) throws IOException {
        loadBookings();  // Load the home page when the "Home" button is clicked
    }

    public void loadHome() {
        loadPage("home.fxml");
    }

    public void loadBookings(){
        loadPage("bookings.fxml");
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
}



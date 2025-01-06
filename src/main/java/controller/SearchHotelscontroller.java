package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SearchHotelscontroller {
    @FXML
    private TextField searchTextField;

    @FXML
    public void onSearchButtonClick(ActionEvent actionEvent) {
        String location = searchTextField.getText();

        if (location.isEmpty()) {
            System.out.println("Please enter a location.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HotelsResults.fxml"));
            Parent root = loader.load();
            HotelsResultscontroller controller = loader.getController();
            controller.setLocation(location);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading the page.");
        }
    }
}
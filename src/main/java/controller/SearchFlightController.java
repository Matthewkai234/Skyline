package controller;

import application.SkylineApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SearchFlightController {
    @FXML
    protected void handelSearchBtn(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SkylineApplication.class.getResource("/view/ResultFlight.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


            stage.setScene(scene);
            stage.setTitle("Home Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


}

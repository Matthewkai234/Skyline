package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class page4ghcontroller {

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
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




}

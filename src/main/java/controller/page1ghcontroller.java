package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class page1ghcontroller {

    public void onSearchButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/page2gh.fxml"));
            Parent nextPageParent = loader.load();

            Scene nextPageScene = new Scene(nextPageParent);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(nextPageScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

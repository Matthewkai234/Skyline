package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class page2ghcontroller {

    @FXML
    private void handleMoreButtonAction (ActionEvent event) {
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

    @FXML
        public void handleReserveButtonAction(ActionEvent actionEvent) throws IOException {
            loadNewWindow("page3gh.fxml");
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

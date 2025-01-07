package controller;

import application.SkylineApplication;
import model.FlightModel;
import database.services.SearshFlightDOAImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SearchFlightController {

    @FXML
    private TextField fromContry;

    @FXML
    private TextField toContry;

    @FXML
    private DatePicker dateFlight;

    SearshFlightDOAImp searshFlightDOAImp = new SearshFlightDOAImp();

    @FXML
    private TableView<FlightModel> Table;


    @FXML
    protected void handelSearchBtn(ActionEvent event) {
        String from = fromContry.getText();
        String to = toContry.getText();
        String date = dateFlight.getValue() != null ? dateFlight.getValue().toString() : "Not Selected";
        System.out.println("date: " + date);

        List<FlightModel> flights = searshFlightDOAImp.searchFlights(from, to, date);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SkylineApplication.class.getResource("/view/flightSearchResultTable.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            FlightSearchResultTableController resultController = fxmlLoader.getController();
            resultController.setSearchCriteria(from, to, flights);

            Stage resultStage = new Stage();
            resultStage.setScene(scene);
            resultStage.setTitle("Flight Search Results");
            resultStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
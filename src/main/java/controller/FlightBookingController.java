package controller;

import database.Booking;
import database.FlightBooking;
import database.repositories.BookingDOAImp;
import database.repositories.FlightBookingDOAImp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.awt.print.Book;

public class FlightBookingController {

    // TextField variables
    @FXML
    private TextField userName;

    @FXML
    private TextField FlightId;

    @FXML
    private TextField NumberOfPasenger;

    @FXML
    private TextField From;

    @FXML
    private TextField To;

    @FXML
    private TextField AgentId;

    @FXML
    private Button bookButton;

    /*
    // TableView and its columns
    @FXML
    private TableView<?> flightTable;

    @FXML
    private TableColumn<?, ?> flightIdColumn;

    @FXML
    private TableColumn<?, ?> fromCountryColumn;

    @FXML
    private TableColumn<?, ?> toCountryColumn;

    @FXML
    private TableColumn<?, ?> airLineColumn;

    @FXML
    private TableColumn<?, ?> userNameColumn;

    @FXML
    private TableColumn<?, ?> agentNameColumn;

    @FXML
    private TableColumn<?, ?> startDateColumn;

    @FXML
    private TableColumn<?, ?> endDateColumn;

     */

    @FXML
    public void Booking() {



        BookingDOAImp bookingDOAImp = new BookingDOAImp();
        FlightBookingDOAImp flightBookingDOAImp = new FlightBookingDOAImp();
        Booking book = new Booking();
        FlightBooking flightBooking = new FlightBooking();



        String userName = this.userName.getText();
        int flightId = Integer.parseInt(FlightId.getText());
        int numberOfPassengers = Integer.parseInt(NumberOfPasenger.getText());
        String from = From.getText();
        String to = To.getText();
        int agentId = Integer.parseInt(AgentId.getText());

        book.setUserName(userName);
        book.setAgentId(agentId);

        //flightBooking.setFlight();

















        bookingDOAImp.AddBooking(book);








    }
}

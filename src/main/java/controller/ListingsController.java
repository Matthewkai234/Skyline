    package controller;

    import javafx.beans.property.SimpleObjectProperty;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.control.cell.PropertyValueFactory;
    import javafx.scene.layout.HBox;
    import javafx.stage.Modality;
    import javafx.stage.Stage;
    import model.AdminListingFlightModel;
    import model.Hotels;
    import org.hibernate.Session;
    import org.hibernate.Transaction;
    import util.HibernateUtil;

    import javax.persistence.criteria.CriteriaBuilder;
    import javax.persistence.criteria.CriteriaQuery;
    import javax.persistence.criteria.Root;
    import java.io.IOException;
    import java.util.List;

    public class ListingsController {
        @FXML
        private TabPane tabPane;

        @FXML
        private TableView<AdminListingFlightModel> flightTable;

        @FXML
        private TableColumn<AdminListingFlightModel, String> flightNumberColumn;

        @FXML
        private TableColumn<AdminListingFlightModel, String> airlineColumn;

        @FXML
        private TableColumn<AdminListingFlightModel, String> destinationColumn;

        @FXML
        private TableColumn<AdminListingFlightModel, String> departureDateColumn;

        @FXML
        private TableColumn<AdminListingFlightModel, String> arrivalDateColumn;

        @FXML
        private TableColumn<AdminListingFlightModel, HBox> flightActionsColumn; // Changed to HBox

        @FXML
        private TableView<Hotels> hotelTable;

        @FXML
        private TableColumn<Hotels, Integer> hotelIdColumn;

        @FXML
        private TableColumn<Hotels, String> hotelNameColumn;

        @FXML
        private TableColumn<Hotels, String> locationColumn;

        @FXML
        private TableColumn<Hotels, Double> hotelPriceColumn;
        @FXML
        private TableColumn<Hotels, Float> hotelRateColumn;


        @FXML
        private TableColumn<Hotels, HBox> hotelActionsColumn;


        @FXML
        public void initialize() {
            // Handle flight actions
            flightActionsColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(createActionButtonsForFlight(param.getValue())));
            // Handle hotel actions
            hotelActionsColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(createActionButtonsForHotel(param.getValue())));

            // Load initial data based on the selected tab (or default to Flights)
            if(tabPane.getSelectionModel().getSelectedItem() != null){
                String initialTabText = tabPane.getSelectionModel().getSelectedItem().getText();
                if ("Hotels".equals(initialTabText)) {
                    loadHotelData();
                } else if ("Flights".equals(initialTabText)) {
                    loadFlightData();
                }
            }
            else { // default to loading Flights if there are no tabs
                loadFlightData();
            }

            tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
                if (newTab != null) {
                    String tabText = newTab.getText();
                    if ("Hotels".equals(tabText)) {
                        loadHotelData();
                    } else if ("Flights".equals(tabText)) {
                        loadFlightData();
                    }
                }
            });

        }

        // Updated methods to return an HBox containing both buttons
        private HBox createActionButtonsForFlight(AdminListingFlightModel flight) {
            Button deleteButton = new Button("Delete");
            deleteButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white;");
            deleteButton.setOnAction(event -> deleteFlight(flight));

            Button editButton = new Button("Edit");
            editButton.setStyle("-fx-background-color: #00affa; -fx-text-fill: white;");
            editButton.setOnAction(event -> openFlightEditor(flight));

            HBox buttons = new HBox(5); // 5 pixel spacing between buttons
            buttons.getChildren().addAll(deleteButton, editButton);
            return buttons;
        }

        private HBox createActionButtonsForHotel(Hotels hotel) {
            Button deleteButton = new Button("Delete");
            deleteButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white;");
            deleteButton.setOnAction(event -> deleteHotel(hotel));

            Button editButton = new Button("Edit");
            editButton.setStyle("-fx-background-color: #00affa; -fx-text-fill: white;");
            editButton.setOnAction(event -> openHotelEditor(hotel));

            HBox buttons = new HBox(10);
            buttons.getChildren().addAll(deleteButton, editButton);
            return buttons;
        }

        // Method to open Flight Editor Window
        private void openFlightEditor(AdminListingFlightModel flight) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditFlight.fxml")); // Make sure the path is correct
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.load()));
                EditFlightController controller = loader.getController();
                controller.setFlight(flight); //Pass the flight data to the controller
                controller.setParentController(this); // Pass the parent controller to refresh the table view after update
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Method to open Hotel Editor Window
        private void openHotelEditor(Hotels hotel) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditHotel.fxml")); // Correct FXML file
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.load()));

                EditHotelController controller = loader.getController(); // Correct controller
                controller.setHotel(hotel);
                controller.setParentController(this);

                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        private void deleteFlight(AdminListingFlightModel flight) {
            try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
                Transaction transaction = session.beginTransaction();
                session.delete(flight);
                transaction.commit();
                loadFlightData();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        private void deleteHotel(Hotels hotel) {
            try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
                Transaction transaction = session.beginTransaction();
                session.delete(hotel);
                transaction.commit();
                loadHotelData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @FXML
        private void addListingWindow() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddListing.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.load()));
                AddListingController controller = loader.getController();
                controller.setParentController(this);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void loadHotelData() {
            // Bind the data to the table columns
            hotelIdColumn.setCellValueFactory(new PropertyValueFactory<>("hotelId"));
            hotelNameColumn.setCellValueFactory(new PropertyValueFactory<>("hotelName"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            hotelPriceColumn.setCellValueFactory(new PropertyValueFactory<>("hotelPrice")); // Binding Price
            hotelRateColumn.setCellValueFactory(new PropertyValueFactory<>("hotelRate")); // Binding Rate

            // Load the data into the table
            ObservableList<Hotels> hotelList = loadHotelsFromDatabase();
            hotelTable.setItems(hotelList);
        }

        private ObservableList<Hotels> loadHotelsFromDatabase() {
            ObservableList<Hotels> hotels = FXCollections.observableArrayList();
            try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
                CriteriaBuilder builder = session.getCriteriaBuilder();
                CriteriaQuery<Hotels> query = builder.createQuery(Hotels.class);
                Root<Hotels> root = query.from(Hotels.class);
                query.select(root);

                List<Hotels> hotelList = session.createQuery(query).getResultList();
                hotels.addAll(hotelList);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return hotels;
        }

        public void loadFlightData() {
            flightNumberColumn.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
            airlineColumn.setCellValueFactory(new PropertyValueFactory<>("airline"));
            destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
            departureDateColumn.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
            arrivalDateColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));

            // Load data from database
            ObservableList<AdminListingFlightModel> flightList = loadFlightsFromDatabase();
            flightActionsColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(createActionButtonsForFlight(param.getValue())));

            flightTable.setItems(flightList);
        }

        private ObservableList<AdminListingFlightModel> loadFlightsFromDatabase() {
            ObservableList<AdminListingFlightModel> flights = FXCollections.observableArrayList();
            try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
                CriteriaBuilder builder = session.getCriteriaBuilder();
                CriteriaQuery<AdminListingFlightModel> criteriaQuery = builder.createQuery(AdminListingFlightModel.class);
                Root<AdminListingFlightModel> root = criteriaQuery.from(AdminListingFlightModel.class);
                criteriaQuery.select(root);
                List<AdminListingFlightModel> flightData = session.createQuery(criteriaQuery).getResultList();
                flights.addAll(flightData);
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
            return flights;
        }
    }

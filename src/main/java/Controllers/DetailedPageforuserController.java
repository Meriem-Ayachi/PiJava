package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import tn.esprit.controllers.reservationAdd;
import tn.esprit.models.Vols;
import tn.esprit.services.VolService;
import javafx.stage.Stage;

import java.io.IOException;

public class DetailedPageforuserController {

    @FXML
    private Label IdLabel;

    @FXML
    private Label destinationLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label departureLabel;

    @FXML
    private Label arrivalLabel;

    @FXML
    private Label durationLabel;

    @FXML
    private Label departureTimeLabel;

    @FXML
    private Label arrivalTimeLabel;

    @FXML
    private Label classLabel;

    @FXML
    private Label pointDepartLabel;

    @FXML
    private Label nbRescaleLabel;

    @FXML
    private Label nbPlaceLabel;


    private Vols currentFlight; // Add a field to store the current flight


    private Stage primaryStage;
    public void setPrimaryStage(Stage stage){
        primaryStage = stage;
    }

    public void initData(Vols vol) {
        currentFlight = vol;

        // Ensure that the ID is set properly
        System.out.println("Flight ID: " + vol.getId()); // Debugging statement


        IdLabel.setText("Id: " + vol.getId());
        destinationLabel.setText("Destination: " + vol.getDestination());
        priceLabel.setText("Price: $" + vol.getPrix());
        departureLabel.setText("Departure Date: " + vol.getDatedepart());
        arrivalLabel.setText("Arrival Date: " + vol.getDatearrive());
        durationLabel.setText("Duration: " + vol.getDuree());

        classLabel.setText("Class: " + vol.getClasse());
        pointDepartLabel.setText("Departure Point: " + vol.getPointdepart());
        nbRescaleLabel.setText("Number of Rescales: " + vol.getNbrescale());
        nbPlaceLabel.setText("Number of Places: " + vol.getNbrplace());
    }



    // Initialize the currentFlight field with the flight passed from the main page


    // Method to handle delete action


    // Method to close the detailed page after deletion
    private void closeDetailedPage() {
        // Close the detailed page
        Stage stage = (Stage) destinationLabel.getScene().getWindow();
        stage.close();
    }


    @FXML
    void reserverVol(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/reservationAdd.fxml"));
            AnchorPane detailsReclamationPane = loader.load();
            reservationAdd controller = loader.getController();

            // Appeler la méthode pour initialiser les détails de la réclamation
            controller.fillReservationInputs(
                currentFlight.getPointdepart(),
                currentFlight.getDestination(),
                currentFlight.getClasse(),
                String.valueOf(currentFlight.getNbrplace()),
                currentFlight.getDatedepart(),
                currentFlight.getDatearrive()
            );

            Stage stage = (Stage) pointDepartLabel.getScene().getWindow();
            // close current windows
            stage.close();
            // open reservation in the primary window
            primaryStage.setScene(new Scene(detailsReclamationPane));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
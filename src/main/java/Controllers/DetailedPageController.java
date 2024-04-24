package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tn.esprit.models.Vols;
import tn.esprit.services.VolService;
import javafx.stage.Stage;

public class DetailedPageController {

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

    public void initData(Vols vol) {
        currentFlight = vol;

        // Ensure that the ID is set properly
        System.out.println("Flight ID: " + vol.getId()); // Debugging statement

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
    @FXML
    private void deleteFlight(ActionEvent event) {
        if (currentFlight != null) {
            VolService volService = new VolService(){};

            System.out.println("Deleting flight with ID: " + currentFlight.getId()); // Debugging statement

            volService.delete(currentFlight);
            closeDetailedPage();
        }
    }

    // Method to close the detailed page after deletion
    private void closeDetailedPage() {
        // Close the detailed page
        Stage stage = (Stage) destinationLabel.getScene().getWindow();
        stage.close();
    }

}
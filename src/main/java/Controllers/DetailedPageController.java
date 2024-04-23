package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tn.esprit.models.Vols;

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

    public void initData(Vols vol) {
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
}

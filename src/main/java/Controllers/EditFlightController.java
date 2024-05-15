package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Vols;
import tn.esprit.services.VolService;

import java.time.LocalDate;

public class EditFlightController {

    @FXML
    private TextField destinationField;

    @FXML
    private TextField priceField;

    @FXML
    private DatePicker departureDateField;

    @FXML
    private DatePicker arrivalDateField;

    @FXML
    private TextField durationField;

    @FXML
    private TextField departureTimeField;

    @FXML
    private TextField arrivalTimeField;

    @FXML
    private ComboBox<String> classField;

    @FXML
    private TextField departurePointField;

    @FXML
    private TextField rescalesField;

    @FXML
    private TextField placesField;

    private Vols currentFlight;

    public void initData(Vols flight) {
        currentFlight = flight;
        // Initialize input fields with current flight data
        destinationField.setText(flight.getDestination());
        priceField.setText(String.valueOf(flight.getPrix()));
        departureDateField.setValue(LocalDate.parse(flight.getDatedepart()));
        arrivalDateField.setValue(LocalDate.parse(flight.getDatearrive()));
        durationField.setText(flight.getDuree());

        // Set the selected item in the class ComboBox
        classField.getSelectionModel().select(flight.getClasse());

        departurePointField.setText(flight.getPointdepart());
        rescalesField.setText(String.valueOf(flight.getNbrescale()));
        placesField.setText(String.valueOf(flight.getNbrplace()));
    }

    @FXML
    private void saveChanges() {
        if (currentFlight != null) {
            // Update flight object with new data
            currentFlight.setDestination(destinationField.getText());
            currentFlight.setPrix(Double.parseDouble(priceField.getText()));
            currentFlight.setDatedepart(departureDateField.getValue().toString());
            currentFlight.setDatearrive(arrivalDateField.getValue().toString());
            currentFlight.setDuree(durationField.getText());

            // Get the selected item from the class ComboBox
            currentFlight.setClasse(classField.getValue());

            currentFlight.setPointdepart(departurePointField.getText());
            currentFlight.setNbrescale(Integer.parseInt(rescalesField.getText()));
            currentFlight.setNbrplace(Integer.parseInt(placesField.getText()));

            // Call service to update flight in the database
            VolService volService = new VolService(){};
            volService.update(currentFlight);
            closeeditPage();

        }
    }

    private void closeeditPage() {
        // Close the detailed page
        Stage stage = (Stage) destinationField.getScene().getWindow();
        stage.close();
    }


}
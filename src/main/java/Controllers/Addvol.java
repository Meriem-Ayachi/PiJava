package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tn.esprit.models.Vols;
import tn.esprit.services.VolService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Addvol {

    @FXML
    private ComboBox<String> classeComboBox;

    @FXML
    private DatePicker datearriveeTF;

    @FXML
    private DatePicker datedepartTF;

    @FXML
    private TextField destinationTF;

    @FXML
    private TextField dureeTF;

    @FXML
    private TextField nbrescaleTF;

    @FXML
    private TextField nbrplaceTF;

    @FXML
    private TextField pointdepartTF;

    @FXML
    private TextField prixTF;

    private final VolService ps = new VolService() {

    };

    @FXML
    void ajoutervol(ActionEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate departureDate = datedepartTF.getValue();
        LocalDate arrivalDate = datearriveeTF.getValue();

        // Check if any date fields are empty
        if (departureDate == null || arrivalDate == null) {
            afficherErreur("Veuillez sélectionner des dates de départ et d'arrivée valides");
            return;
        }

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Check if the departure date is earlier than the current date
        if (departureDate.isBefore(currentDate)) {
            afficherErreur("La date de départ ne peut pas être antérieure à la date actuelle");
            return;
        }

        // Check if the arrival date is before or equal to the departure date
        if (arrivalDate.isBefore(departureDate) || arrivalDate.isEqual(departureDate)) {
            afficherErreur("La date d'arrivée doit être postérieure à la date de départ");
            return;
        }

        // Check for other fields if they are empty or not

        if (destinationTF.getText().isEmpty()) {
            afficherErreur("Veuillez saisir une destination");
            return;
        }
        if (dureeTF.getText().isEmpty()) {
            afficherErreur("Veuillez saisir une durée");
            return;
        }
        if (classeComboBox.getValue() == null) {
            afficherErreur("Veuillez sélectionner une classe");
            return;
        }
        if (pointdepartTF.getText().isEmpty()) {
            afficherErreur("Veuillez saisir un point de départ");
            return;
        }
        if (nbrescaleTF.getText().isEmpty()) {
            afficherErreur("Veuillez saisir un nombre d'escales");
            return;
        }
        if (nbrplaceTF.getText().isEmpty()) {
            afficherErreur("Veuillez saisir un nombre de places");
            return;
        }
        if (prixTF.getText().isEmpty()) {
            afficherErreur("Veuillez saisir un prix");
            return;
        }

        // Add the flight
        String formattedDatedepart = departureDate.format(formatter);
        String formattedDatearrivee = arrivalDate.format(formatter);
        ps.add(new Vols(Integer.parseInt(nbrescaleTF.getText()), Integer.parseInt(nbrplaceTF.getText()), dureeTF.getText(), formattedDatedepart, formattedDatearrivee, classeComboBox.getValue(), destinationTF.getText(), pointdepartTF.getText(), Double.parseDouble(prixTF.getText())));

        // Show alert after adding flight
        afficherConfirmation("Le vol a été ajouté avec succès !");
    }


    private void afficherConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void navigate(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/showvol.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        destinationTF.getScene().setRoot(root);
    }

    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
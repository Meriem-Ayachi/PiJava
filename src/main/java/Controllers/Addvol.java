package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tn.esprit.models.Vols;
import tn.esprit.services.VolService;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class Addvol {

    @FXML
    private TextField classeTF;

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
        @Override
        public void delete(int id) {

        }
    };

    @FXML
    void ajoutervol(ActionEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDatedepart = datedepartTF.getValue().format(formatter);
        String formattedDatearrivee = datearriveeTF.getValue().format(formatter);

        if (destinationTF.getText().isEmpty()){afficherErreur("veuillez saisir une destination");
        return;}
        if (dureeTF.getText().isEmpty()) {
            afficherErreur("veuillez saisir une durée");
            return;
        }
        if (classeTF.getText().isEmpty()) {
            afficherErreur("veuillez saisir une classe");
            return;
        }
        if (pointdepartTF.getText().isEmpty()) {
            afficherErreur("veuillez saisir un point de départ");
            return;
        }
        if (nbrescaleTF.getText().isEmpty()) {
            afficherErreur("veuillez saisir un nombre d'escales");
            return;
        }
        if (nbrplaceTF.getText().isEmpty()) {
            afficherErreur("veuillez saisir un nombre de places");
            return;
        }
        if (prixTF.getText().isEmpty()) {
            afficherErreur("veuillez saisir un prix");
            return;
        }

        ps.add(new Vols(Integer.parseInt(nbrescaleTF.getText()),Integer.parseInt(nbrplaceTF.getText()),dureeTF.getText(),formattedDatedepart, formattedDatearrivee,classeTF.getText(),destinationTF.getText(),pointdepartTF.getText(),Double.parseDouble(prixTF.getText())));

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

    private void afficherErreur(String message)
    {
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }

}

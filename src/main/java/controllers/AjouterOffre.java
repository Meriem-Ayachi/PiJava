package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.models.Offres;
import tn.esprit.services.OffresService;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class AjouterOffre {

    @FXML
    private TextField titleTFO;

    @FXML
    private TextField descriptionTFO;

    @FXML
    private TextField publishedTFO;

    @FXML
    private TextField prixTFO;

    @FXML
    private TextField lieuTFO;

    @FXML
    private TextField imageTFO;

    @FXML
    private TextField dateTFO;

    private final OffresService os = new OffresService();

    @FXML
    void AjouterO(ActionEvent event) {
        try {
            // Vérifier si la dateTFO est vide
            if (dateTFO.getText().isEmpty()) {
                throw new IllegalArgumentException("La date est vide.");
            }

            // Convertir la chaîne de texte en java.sql.Date
            Date date = Date.valueOf(dateTFO.getText());

            // Ajouter l'offre avec la date convertie
            os.add(new Offres(
                    titleTFO.getText(),
                    descriptionTFO.getText(),
                    Boolean.parseBoolean(publishedTFO.getText()),
                    Double.parseDouble(prixTFO.getText()),
                    lieuTFO.getText(),
                    imageTFO.getText(),
                    date
            ));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Format de date invalide.");
            alert.showAndWait();
        }
    }

    @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherOffre.fxml"));
            titleTFO.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

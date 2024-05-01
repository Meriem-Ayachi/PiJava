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
import java.time.LocalDate;

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



    private final OffresService os = new OffresService();

    @FXML
    void AjouterO(ActionEvent event) {
        try {

            if (descriptionTFO.getText().isEmpty()) {
                throw new SQLException("La description est vide.");
            }
            if (descriptionTFO.getText().length() > 20) {
                throw new SQLException("La description ne doit pas dépasser 20 caractères.");
            }
            if (titleTFO.getText().isEmpty()) {
                throw new SQLException("le titre est vide.");
            }
            if (imageTFO.getText().isEmpty()) {
                throw new SQLException("le image est vide.");
            }
            if (prixTFO.getText().isEmpty()) {
                throw new SQLException("le prix est vide.");
            }
            if (Double.parseDouble(prixTFO.getText()) == 0.0) {
                throw new SQLException("Le prix ne peut pas être égal à zéro.");
            }
            if (publishedTFO.getText().isEmpty()) {
                throw new SQLException("le published est vide.");
            }
            if (lieuTFO.getText().isEmpty()) {
                throw new SQLException("le lieu est vide.");
            }

            // Get today's date
            LocalDate today = LocalDate.now();

// Convert LocalDate to java.sql.Date
            Date date = Date.valueOf(today);
            os.add(new Offres(
                    titleTFO.getText(),
                    descriptionTFO.getText(),
                    Boolean.parseBoolean(publishedTFO.getText()),
                    Double.parseDouble(prixTFO.getText()),
                    lieuTFO.getText(),
                    imageTFO.getText(),
                    date
            ));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Offre ajouter");
            alert.showAndWait();
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

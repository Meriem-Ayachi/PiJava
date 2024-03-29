package tn.esprit.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.models.Reclamation;
import tn.esprit.services.ReclamationService;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AjouterReclamation {
    @FXML
    private TextField dateSouTF; // Remplacez le Label par un TextField

    @FXML
    private TextArea descriptionTF;

    @FXML
    private TextField sujetTF;

    @FXML
    private CheckBox estTraiteCB;

    @FXML
    private ComboBox<?> userIDCB;


    @FXML
    void AfficherReclamations(ActionEvent event) {

    }


    @FXML
    void initialize() {
        // Rendre le TextField non éditable
        dateSouTF.setEditable(false);

        // Récupérer la date et l'heure actuelles
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Formater la date et l'heure actuelles
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        // Définir le texte du TextField pour afficher la date et l'heure actuelles
        dateSouTF.setText(formattedDateTime);
    }
    @FXML
    void ajouterReclamation(ActionEvent event) {

        // Vérifier si le champ sujet est vide
        if (sujetTF.getText().isEmpty()) {
            afficherErreur("Veuillez saisir un sujet.");
            return;
        }
        if (sujetTF.getText().length() < 3) {
            afficherErreur("Le sujet doit comporter au moins 3 caractères.");
            return;
        }

        // Vérifier si le champ description est vide
        if (descriptionTF.getText().isEmpty()) {
            afficherErreur("Veuillez saisir une description.");
            return;
        }
        if (descriptionTF.getText().length() < 3) {
            afficherErreur("La description doit comporter au moins 3 caractères.");
            return;
        }
        // Récupérer la date et l'heure actuelles
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Formater la date et l'heure actuelles
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        // Définir le texte du libellé pour afficher la date et l'heure actuelles
        dateSouTF.setText(formattedDateTime);

        // Rendre le libellé en lecture seule
        // Note: Vous ne pouvez pas rendre un Label en lecture seule, mais vous pouvez le rendre non-éditable
        // Et il est préférable de ne pas modifier l'interface utilisateur après l'initialisation dans cette méthode


        ReclamationService reclamationService = new ReclamationService();
        Reclamation reclamation = new Reclamation();
        reclamation.setSujet(sujetTF.getText());
        reclamation.setDescription(descriptionTF.getText());
        reclamation.setDatesoummission(Timestamp.valueOf(currentDateTime)); // Utilisez directement currentDateTime
        reclamation.setEst_traite(estTraiteCB.isSelected()? (byte) 1 : (byte) 0);
        {
            reclamationService.add(reclamation);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setContentText("Réclamation ajoutée");
            alert.showAndWait();
        }
    }

    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setContentText(message);
        alert.showAndWait();
    }

}

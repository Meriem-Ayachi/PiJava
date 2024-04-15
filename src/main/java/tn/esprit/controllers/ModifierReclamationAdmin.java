package tn.esprit.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.models.Reclamation;
import tn.esprit.services.ReclamationService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ModifierReclamationAdmin {

    @FXML
    private TextField dateSouTF;

    @FXML
    private TextArea descriptionTF;

    @FXML
    private CheckBox estTraiteCB;

    @FXML
    private TextField sujetTF;

    @FXML
    private ComboBox<?> userIDCB;

    @FXML
    private TextField idTF;


    @FXML
    void AfficherReclamations(ActionEvent event) {

    }

    @FXML
    void recupB(ActionEvent event) {
        ReclamationService rs=new ReclamationService();
        int id= Integer.parseInt(idTF.getText());
        Reclamation r=rs.getOne(id);
        sujetTF.setText(r.getSujet());
        descriptionTF.setText(r.getDescription());
    }

    @FXML
    void modifierReclamation(ActionEvent event) {
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

        ReclamationService reclamationService = new ReclamationService();
        Reclamation reclamation = new Reclamation();
        reclamation.setId(Integer.parseInt(idTF.getText()));
        reclamation.setSujet(sujetTF.getText());
        reclamation.setDescription(descriptionTF.getText());
        reclamation.setDatesoummission(Timestamp.valueOf(currentDateTime)); // Utilisez directement currentDateTime
        reclamation.setEst_traite(estTraiteCB.isSelected()? (byte) 1 : (byte) 0);
        reclamation.setUser_id(1);
        System.out.println(reclamation);
        {
            reclamationService.update(reclamation);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setContentText("Réclamation modifiée avec succés");
            alert.showAndWait();
        }
    }

    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /* private void afficherMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setContentText(message);
        alert.showAndWait();
    }*/


}


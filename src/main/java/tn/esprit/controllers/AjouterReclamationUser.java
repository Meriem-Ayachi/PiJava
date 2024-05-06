package tn.esprit.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import tn.esprit.models.Reclamation;
import tn.esprit.models.session;
import tn.esprit.services.ReclamationService;
import tn.esprit.util.BadWordsChecker;
import tn.esprit.util.Navigator;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.UnsupportedEncodingException;



public class AjouterReclamationUser {
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

        // Vérifier si le sujet contient des mots inappropriés
        String sujet = sujetTF.getText();
        BadWordsChecker badWordsChecker = new BadWordsChecker();
        try {
            String encodedSujet = URLEncoder.encode(sujet, "UTF-8");
            if (badWordsChecker.containsBadWords(encodedSujet)) {
                afficherErreur("Le sujet contient des mots inappropriés.");
                return;
            }
        } catch (UnsupportedEncodingException e) {
            // Gérer l'exception d'encodage
            e.printStackTrace();
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

        // Vérifier si la description contient des mots inappropriés
        String description = descriptionTF.getText();

        try {
            String encodedDescription = URLEncoder.encode(description, "UTF-8");
            if (badWordsChecker.containsBadWords(encodedDescription)) {
                afficherErreur("La description contient des mots inappropriés.");
                return;
            }
        } catch (UnsupportedEncodingException e) {
            // Gérer l'exception d'encodage
            e.printStackTrace();
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
        reclamation.setUser_id(session.id_utilisateur);
        reclamation.setSujet(sujetTF.getText());
        reclamation.setDescription(descriptionTF.getText());
        reclamation.setDatesoummission(Timestamp.valueOf(currentDateTime)); // Utilisez directement currentDateTime
        reclamation.setEst_traite(estTraiteCB.isSelected()? (byte) 1 : (byte) 0);

        // Ajouter la réclamation
        reclamationService.add(reclamation);

        // Afficher une confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Succès");
        alert.setContentText("Réclamation ajoutée");
        alert.showAndWait();

        // Naviguer vers la page d'affichage des réclamations
        Navigator nav = new Navigator();
        nav.goToPage_WithEvent("/AfficherReclamationUser.fxml", event);
    }


    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void back(ActionEvent event) {
        Navigator nav =new Navigator();
        nav.goToPage_WithEvent("/AfficherReclamationUser.fxml" , event);
    }
}

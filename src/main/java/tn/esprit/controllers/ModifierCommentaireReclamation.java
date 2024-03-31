package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.esprit.models.Reclamation;
import tn.esprit.models.Reclamation_Commentaire;
import tn.esprit.services.ReclamationService;
import tn.esprit.services.Reclamation_CommentaireService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ModifierCommentaireReclamation {

    @FXML
    private TextField contenuTF;

    @FXML
    private TextField dateCreaTF;

    @FXML
    private TextField idTF;

    @FXML
    private ComboBox<Integer> reclamationIdCB;


    @FXML
    void recupB(ActionEvent event) {
        Reclamation_CommentaireService commentaireService = new Reclamation_CommentaireService();
        int id = Integer.parseInt(idTF.getText());
        Reclamation_Commentaire commentaire = commentaireService.getOne(id);
        if (commentaire != null) {
            contenuTF.setText(commentaire.getContenu());
            dateCreaTF.setText(commentaire.getDate_creation().toString()); // Assurez-vous que le format de date convient
            reclamationIdCB.setValue(commentaire.getReclamation_id());

        } else {
            // Gérer le cas où le commentaire n'est pas trouvé
            contenuTF.setText("");
            dateCreaTF.setText("");
            reclamationIdCB.setValue(null);
            afficherErreur("Commentaire non trouvé");
        }
    }

    @FXML
    void modifierCommentaire(ActionEvent event) {

        // Vérifier si le champ contenu est vide
        if (contenuTF.getText().isEmpty()) {
            afficherErreur("Veuillez saisir un contenu.");
            return;
        }
        if (contenuTF.getText().length() < 3) {
            afficherErreur("Le contenu doit comporter au moins 3 caractères.");
            return;
        }

        // Récupérer la date et l'heure actuelles
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Formater la date et l'heure actuelles
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        // Définir le texte du libellé pour afficher la date et l'heure actuelles
        dateCreaTF.setText(formattedDateTime);

        Reclamation_CommentaireService commentaireService = new Reclamation_CommentaireService();
        Reclamation_Commentaire commentaire = new Reclamation_Commentaire();
        commentaire.setId(Integer.parseInt(idTF.getText()));
        commentaire.setContenu(contenuTF.getText());
        commentaire.setDate_creation(Timestamp.valueOf(currentDateTime)); // Utilisez directement currentDateTime
        commentaire.setUser_id(1);
        commentaire.setReclamation_id(reclamationIdCB.getValue());



        {
            commentaireService.update(commentaire);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setContentText("Commentaire modifié avec succés");
            alert.showAndWait();
        }
    }
    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
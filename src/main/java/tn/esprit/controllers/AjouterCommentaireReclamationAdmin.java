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
import java.util.List;

public class AjouterCommentaireReclamationAdmin {

    @FXML
    private TextField DateCreaTF;

    @FXML
    private ComboBox<Integer> ReclamationIdCB;


    @FXML
    private TextField contenuTF;

    @FXML
    void initialize() {

        chargerReclamations();


        DateCreaTF.setEditable(false);

        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        DateCreaTF.setText(formattedDateTime);
    }

    private void chargerReclamations() {

        ReclamationService reclamationService = new ReclamationService();
        List<Reclamation> reclamations = reclamationService.getAll();

        for (Reclamation reclamation : reclamations) {
            ReclamationIdCB.getItems().add(reclamation.getId());
        }
    }

    @FXML
    void ajouterCommentaire(ActionEvent event) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        if (contenuTF.getText().isEmpty()) {
            afficherErreur("Veuillez saisir le contenu du commentaire.");
            return;
        }

        if (contenuTF.getText().length() < 3) {
            afficherErreur("Le contenu du commentaire doit comporter au moins 3 caractères.");
            return;
        }


        if (ReclamationIdCB.getValue() == null) {
            afficherErreur("Veuillez sélectionner une réclamation.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        DateCreaTF.setText(formattedDateTime);

        int reclamationId = ReclamationIdCB.getValue();



        Reclamation_CommentaireService commentaireService = new Reclamation_CommentaireService();
        Reclamation_Commentaire commentaire = new Reclamation_Commentaire();
        commentaire.setContenu(contenuTF.getText());
        commentaire.setReclamation_id(reclamationId);
        commentaire.setDate_creation(Timestamp.valueOf(currentDateTime));
        commentaire.setUser_id(1);

        {
            commentaireService.add(commentaire);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Commentaire ajouté");
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


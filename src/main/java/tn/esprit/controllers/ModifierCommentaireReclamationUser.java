package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Reclamation;
import tn.esprit.models.Reclamation_Commentaire;
import tn.esprit.services.ReclamationService;
import tn.esprit.services.Reclamation_CommentaireService;

import java.io.IOException;

public class ModifierCommentaireReclamationUser {

    @FXML
    private TextField contenuTF;

    Reclamation_CommentaireService commentaireService = new Reclamation_CommentaireService();
    ReclamationService rs = new ReclamationService();
    Reclamation currentRec ;
    Reclamation_Commentaire currentComm;
    @FXML
    void initialize(int commentaireID) {
        currentComm = commentaireService.getOne(commentaireID);
        contenuTF.setText(currentComm.getContenu());

        currentRec = rs.getOne(currentComm.getReclamation_id());
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



        currentComm.setContenu(contenuTF.getText());

        {
            commentaireService.update(currentComm);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setContentText("Commentaire modifié avec succés");
            alert.showAndWait();
        }
        try{
            GoToCommentaires(event);
        } catch (Exception e)
        {
            e.getMessage();
        }


    }
    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void GoToCommentaires(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsReclamationUser.fxml"));
        Parent root = loader.load();

        // Obtenir le contrôleur associé à l'interface
        DetailsReclamationUser controller = loader.getController();

        // Appeler la méthode pour initialiser les détails de la réclamation
        controller.initializeDetails(currentRec);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
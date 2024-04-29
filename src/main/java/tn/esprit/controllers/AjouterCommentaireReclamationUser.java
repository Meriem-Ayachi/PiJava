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
import tn.esprit.models.session;
import tn.esprit.services.Reclamation_CommentaireService;
import tn.esprit.util.BadWordsChecker;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AjouterCommentaireReclamationUser {

    @FXML
    private TextField contenuTF;

    private Reclamation currentRec;
    @FXML
    void initialize(Reclamation reclamation) {
        currentRec=reclamation;
    }



    @FXML
    void ajouterCommentaire(ActionEvent event) throws IOException {
        LocalDateTime currentDateTime = LocalDateTime.now();

        if (contenuTF.getText().isEmpty()) {
            afficherErreur("Veuillez saisir le contenu du commentaire.");
            return;
        }

        if (contenuTF.getText().length() < 3) {
            afficherErreur("Le contenu du commentaire doit comporter au moins 3 caractères.");
            return;
        }

        // Vérifier si le contenu du commentaire contient des mots inappropriés
        String contenu = contenuTF.getText();
        String encodedContenu = URLEncoder.encode(contenu, "UTF-8");

        BadWordsChecker badWordsChecker = new BadWordsChecker();

        if (badWordsChecker.containsBadWords(encodedContenu)) {
            afficherErreur("Le commentaire contient des mots inappropriés.");
            return;
        }

        Reclamation_CommentaireService commentaireService = new Reclamation_CommentaireService();
        Reclamation_Commentaire commentaire = new Reclamation_Commentaire();
        commentaire.setContenu(contenu);
        commentaire.setReclamation_id(currentRec.getId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        commentaire.setDate_creation(currentTimestamp);
        commentaire.setUser_id(session.id_utilisateur);

        {
            commentaireService.add(commentaire);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Commentaire ajouté");
            alert.showAndWait();
        }

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


    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }
}


package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import tn.esprit.models.Reclamation;
import tn.esprit.services.ReclamationService;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;


public class DetailsReclamation {

    @FXML
    private Label descriptionLabel;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private Label sujetLabel;

    @FXML
    private Label sujetLabel_modifier;

    @FXML
    private Label descriptionLabel_modifier;

    @FXML
    private TextField sujetTextField;
    private Reclamation reclamation;
    private ReclamationService reclamationService = new ReclamationService();

    @FXML
    private Button enregistrerButton;
    @FXML
    private Button modifierButton;
    @FXML
    private Button supprimerButton;
    @FXML
    private Button commentaireButton;


    // Méthode pour initialiser les détails de la réclamation dans l'interface
    public void initializeDetails(Reclamation reclamation) {
        this.reclamation = reclamation;
        sujetLabel.setText("Sujet : " + reclamation.getSujet());
        descriptionLabel.setText("Description : " + reclamation.getDescription());
    }

    @FXML
    void modifierReclamationBTN() {
        if (reclamation != null) {
            // Récupérer les anciennes valeurs de la réclamation
            String ancienSujet = reclamation.getSujet();
            String ancienneDescription = reclamation.getDescription();

            // Afficher les champs de texte avec les anciennes valeurs
            sujetTextField.setText(ancienSujet);
            descriptionTextField.setText(ancienneDescription);

            // Rendre les champs de texte visibles et éditables
            sujetTextField.setVisible(true);
            descriptionTextField.setVisible(true);
            sujetTextField.setEditable(true);
            descriptionTextField.setEditable(true);
            sujetLabel_modifier.setVisible(true);
            descriptionLabel_modifier.setVisible(true);

            // Masquer les labels et autres boutons
            sujetLabel.setVisible(false);
            descriptionLabel.setVisible(false);
            modifierButton.setVisible(false);
            supprimerButton.setVisible(false);
            commentaireButton.setVisible(false);

            // Afficher uniquement le bouton "Enregistrer"
            enregistrerButton.setVisible(true);
        } else {
            // Afficher un message d'erreur si la réclamation est nulle
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Aucune réclamation sélectionnée.");
            alert.showAndWait();
        }
    }


    @FXML
    void enregistrerModification() {
        if (reclamation != null) {
            // Récupérer les nouvelles valeurs des champs de texte
            String nouveauSujet = sujetTextField.getText();
            String nouvelleDescription = descriptionTextField.getText();

            // Mettre à jour la réclamation avec les nouvelles valeurs
            reclamation.setSujet(nouveauSujet);
            reclamation.setDescription(nouvelleDescription);

            // Appeler le service pour mettre à jour la réclamation dans la base de données
            reclamationService.update(reclamation);

            // Afficher un message de confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mise à jour réussie");
            alert.setHeaderText(null);
            alert.setContentText("La réclamation a été mise à jour avec succès.");
            alert.showAndWait();
            ((Stage) sujetLabel.getScene().getWindow()).close();

        }
    }

    @FXML
    void supprimerReclamationBTN() {
        if (reclamation != null) {
            // Supprimer la réclamation de la base de données
            reclamationService.delete(reclamation.getId());

            // Afficher un message de confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
            confirmationAlert.setTitle("Suppression réussie");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("La réclamation a été supprimée avec succès.");
            confirmationAlert.showAndWait();

            // Fermer l'interface "DetailsReclamation"
            ((Stage) sujetLabel.getScene().getWindow()).close();
        }
        else {
            // Afficher un message d'erreur si la réclamation est nulle
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Aucune réclamation sélectionnée.");
            alert.showAndWait();
    }

    }

    @FXML
    void Comments(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCommentaireReclamationUser.fxml"));
        AnchorPane detailsReclamationPane = loader.load();

        // Obtenir le contrôleur associé à l'interface
        AfficherCommentaireReclamationUser controller = loader.getController();

        // Appeler la méthode pour initialiser les détails de la réclamation
        controller.initialize(reclamation);

        // Afficher l'interface dans une nouvelle fenêtre
        Stage stage = new Stage();
        stage.setScene(new Scene(detailsReclamationPane));
        stage.show();

    }
}

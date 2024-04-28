package tn.esprit.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.interfaces.RefreshCallBack;
import tn.esprit.models.Reclamation;
import tn.esprit.services.ReclamationService;

public class ModifierReclamationUser {


    @FXML
    private TextArea DescriptionTF;

    @FXML
    private Button modifierButton;

    @FXML
    private TextField sujetTextField;

    Reclamation reclamation;
    ReclamationService rs = new ReclamationService();

    RefreshCallBack callback ;


    public void initialize(Reclamation rec , RefreshCallBack callback) {
        this.callback = callback;
        this.reclamation = rec;
        sujetTextField.setText(reclamation.getSujet());
        DescriptionTF.setText( reclamation.getDescription());
    }

    @FXML
    void update(ActionEvent event) {

            // Récupérer les nouvelles valeurs des champs de texte
        // Vérifier si le champ sujet est vide
        if (sujetTextField.getText().isEmpty()) {
            afficherErreur("Veuillez saisir un sujet.");
            return;
        }
        if (sujetTextField.getText().length() < 3) {
            afficherErreur("Le sujet doit comporter au moins 3 caractères.");
            return;
        }

            String nouveauSujet = sujetTextField.getText();


        // Vérifier si le champ description est vide
        if (DescriptionTF.getText().isEmpty()) {
            afficherErreur("Veuillez saisir une description.");
            return;
        }
        if (DescriptionTF.getText().length() < 3) {
            afficherErreur("La description doit comporter au moins 3 caractères.");
            return;
        }
            String nouvelleDescription = DescriptionTF.getText();

            // Mettre à jour la réclamation avec les nouvelles valeurs
            reclamation.setSujet(nouveauSujet);
            reclamation.setDescription(nouvelleDescription);

            // Appeler le service pour mettre à jour la réclamation dans la base de données
            rs.update(reclamation);

            // Afficher un message de confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mise à jour réussie");
            alert.setHeaderText(null);
            alert.setContentText("La réclamation a été mise à jour avec succès.");
            alert.showAndWait();
            ((Stage) DescriptionTF.getScene().getWindow()).close();
            this.callback.onRefreshComplete();

    }

    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }
}

package tn.esprit.controllers.locationVoiture;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.MainFX;
import tn.esprit.models.Voiture;
import tn.esprit.services.VoitureService;
import tn.esprit.util.Navigator;

public class AjouterVoiture {
    @FXML
    private TextField marqueTF;

    @FXML
    private TextField modelTF;
    
    @FXML
    private TextField couleurTF;

    @FXML
    private ComboBox<String> energyTF;

    @FXML
    private TextField capaciteTF;
    

    @FXML
    void AfficherVoitures(ActionEvent event) {
        Stage stage = MainFX.getPrimaryStage();
        Navigator nav = new Navigator(stage);
        nav.goToPage("/ListVoitures.fxml");
    }

    @FXML
    void initialize() {
        ObservableList<String> energyType = FXCollections.observableArrayList(
            "Gasoline"
            , "Diesel"
            , "Electricity"
        );
        energyTF.setItems(energyType);
    }

    @FXML
    void ajouterVoiture(ActionEvent event) {
            
        // marque control sasie
        if (marqueTF.getText().isEmpty()) {
            afficherErreur("Veuillez saisir une marque.");
            return;
        }
        if (marqueTF.getText().length() < 3) {
            afficherErreur("La marque doit comporter au moins 3 caractères.");
            return;
        }

        // model control sasie
        if (modelTF.getText().isEmpty()) {
            afficherErreur("Veuillez saisir une model.");
            return;
        }
        if (modelTF.getText().length() < 3) {
            afficherErreur("La model doit comporter au moins 3 caractères.");
            return;
        }

        // couleur control sasie
        if (couleurTF.getText().isEmpty()) {
            afficherErreur("Veuillez saisir une couleur.");
            return;
        }
        if (couleurTF.getText().length() < 3) {
            afficherErreur("La couleur doit comporter au moins 3 caractères.");
            return;
        }

        // energy control sasie
        if (energyTF.getValue() == null) {
            afficherErreur("Veuillez saisir une energy.");
            return;
        }

        // capacité control sasie
        try {
            double capacite = Double.parseDouble(capaciteTF.getText());
            if (capacite <= 0) {
                afficherErreur("La valeur du capacite doit être supérieure à 0.");
                return;
            }
        } catch (NumberFormatException e) {
            afficherErreur("La valeur du capacite doit être un nombre.");
            return;
        }


        VoitureService voitureService = new VoitureService();
        Voiture voiture = new Voiture();
        voiture.setMarque(marqueTF.getText());
        voiture.setModel(modelTF.getText());
        voiture.setCouleur(couleurTF.getText());
        voiture.setEnergy(energyTF.getValue());
        voiture.setCapacite(Integer.parseInt(capaciteTF.getText()));
        {
            // confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Voiture ajoutée");
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            // check if user has declined
            if (result != ButtonType.OK) {
                return;
            }
            // add item
            voitureService.add(voiture);
            // go to the list
            Stage stage = MainFX.getPrimaryStage();
            Navigator nav = new Navigator(stage);
            nav.goToPage("/ListVoitures.fxml");
        }
    }

    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /*private void afficherMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setContentText(message);
        alert.showAndWait();
    }*/

}
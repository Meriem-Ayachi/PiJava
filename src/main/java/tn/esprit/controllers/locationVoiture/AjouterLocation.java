package tn.esprit.controllers.locationVoiture;


import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.MainFX;
import tn.esprit.models.Location_Voiture;
import tn.esprit.models.Voiture;
import tn.esprit.services.LocationVoitureService;
import tn.esprit.services.VoitureService;
import tn.esprit.util.Navigator;

public class AjouterLocation {
    @FXML
    private TextField prixTF;

    @FXML
    private ComboBox<String> typeTF;

    @FXML
    private ComboBox<Voiture>  voitureTF;

    VoitureService voitureService = new VoitureService();

    @FXML
    void AfficherLocations(ActionEvent event) {
        Stage stage = MainFX.getPrimaryStage();
        Navigator nav = new Navigator(stage);
        nav.goToPage("/ListLocation.fxml");
    }


    @FXML
    void initialize() {
        List<Voiture> voitureList = voitureService.getAvailable();
        ObservableList<Voiture> voitureObservableList = FXCollections.observableArrayList(voitureList);
        voitureTF.setItems(voitureObservableList);

        ObservableList<String> typeList = FXCollections.observableArrayList(
            "Sports"
            , "Famille"
            , "Minivan"
            , "Luxe"
        );
        typeTF.setItems(typeList);
    }
    @FXML
    void ajouterLocation(ActionEvent event) {
            
        // Prix control sasie
        try {
            double prix = Double.parseDouble(prixTF.getText());
            if (prix <= 0) {
                afficherErreur("La valeur du prix doit être supérieure à 0.");
                return;
            }
        } catch (NumberFormatException e) {
            afficherErreur("La valeur du prix doit être un nombre.");
            return;
        }


        // type control 
        if (typeTF.getValue() == null) {
            afficherErreur("Veuillez saisir un type.");
            return;
        }

        // voiture control sasie
        if (voitureTF.getValue() == null) {
            afficherErreur("Veuillez saisir une voiture.");
            return;
        }



        LocationVoitureService locationService = new LocationVoitureService();
        Location_Voiture locationVoiture = new Location_Voiture();
        locationVoiture.setPrix(Integer.parseInt(prixTF.getText()));
        locationVoiture.setType(typeTF.getValue());
        locationVoiture.setStatus("disponible");
        locationVoiture.setVoiture_id(voitureTF.getValue().getId());
        {
            // confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Location ajoutée");
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            // check if user has declined
            if (result != ButtonType.OK) {
                return;
            }
            // add item
            locationService.add(locationVoiture);
            // go to the list
            Stage stage = MainFX.getPrimaryStage();
            Navigator nav = new Navigator(stage);
            nav.goToPage("/ListLocation.fxml");
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
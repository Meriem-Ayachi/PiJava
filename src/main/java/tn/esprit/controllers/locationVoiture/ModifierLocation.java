package tn.esprit.controllers.locationVoiture;

import java.sql.Date;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.MainFX;
import tn.esprit.models.Location_Voiture;
import tn.esprit.services.LocationVoitureService;
import tn.esprit.util.Navigator;

public class ModifierLocation {

    @FXML
    private TextField prixTF;

    @FXML
    private DatePicker dateDebutTF;
    
    @FXML
    private DatePicker dateFinTF;

    @FXML
    private ComboBox<String> typeTF;

    @FXML
    private ComboBox<String> statusTF;

    private int location_id;
    private int voiture_id;
    private int user_id;

    public void initialize(Location_Voiture locationVoiture) {
        // initialize comboBox
        ObservableList<String> typeList = FXCollections.observableArrayList(
            "Sports"
            , "Famille"
            , "Minivan"
            , "Luxe"
        );
        typeTF.setItems(typeList);

        ObservableList<String> statusList = FXCollections.observableArrayList(
            "disponible"
            , "réservé"
        );
        statusTF.setItems(statusList);
       
        // get static values
        location_id = locationVoiture.getId();
        voiture_id = locationVoiture.getVoiture_id();
        user_id = locationVoiture.getUser_id();
        // fill the UI with the voiture values
        prixTF.setText(String.valueOf(locationVoiture.getPrix()));
        dateDebutTF.setValue(locationVoiture.getDate_debut().toLocalDate());
        dateFinTF.setValue(locationVoiture.getDatefin().toLocalDate());
        typeTF.setValue(locationVoiture.getType());
        statusTF.setValue(String.valueOf(locationVoiture.getStatus()));
        
    }

    @FXML
    void AfficherLocations(ActionEvent event) {
        Stage stage = MainFX.getPrimaryStage();
        Navigator nav = new Navigator(stage);
        nav.goToPage("/ListLocation.fxml");
    }

    @FXML
    void modifierLocation(ActionEvent event) {
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

        // date debut control sasie
        if (dateDebutTF.getValue() == null) {
            afficherErreur("Veuillez saisir un date debut.");
            return;
        }

        // date fin control sasie
        if (dateFinTF.getValue() == null) {
            afficherErreur("Veuillez saisir un date fin.");
            return;
        }

        // date fin > date debut > today
        LocalDate debut = dateDebutTF.getValue();
        LocalDate fin = dateFinTF.getValue();
        LocalDate today = LocalDate.now();
        if (! fin.isAfter(debut)) {
            afficherErreur("la date fin doit être supérieure à la date du debut");
            return;
        }
        if (! debut.isAfter(today)) {
            afficherErreur("la date de début doit être supérieure à la date d'aujourd'hui");
            return;
        }

        // type control 
        if (typeTF.getValue() == null) {
            afficherErreur("Veuillez saisir un type.");
            return;
        }
        
        // status control sasie
        if (statusTF.getValue() == null) {
            afficherErreur("Veuillez saisir un status.");
            return;
        }


        LocationVoitureService locationService = new LocationVoitureService();
        Location_Voiture locationVoiture = new Location_Voiture();
        //set id
        locationVoiture.setId(location_id);
        locationVoiture.setVoiture_id(voiture_id);
        locationVoiture.setUser_id(user_id);

        locationVoiture.setPrix(Double.parseDouble(prixTF.getText()));
        locationVoiture.setDate_debut(Date.valueOf(dateDebutTF.getValue()));
        locationVoiture.setDatefin(Date.valueOf(dateFinTF.getValue()));
        locationVoiture.setType(typeTF.getValue());
        locationVoiture.setStatus(statusTF.getValue());
        {
            // confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Location Modifier");
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            // check if user has declined
            if (result != ButtonType.OK) {
                return;
            }
            // add item
            locationService.update(locationVoiture);
            
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
}

package tn.esprit.controllers.locationVoiture;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private ComboBox<String> typeTF;

    @FXML
    private ComboBox<String> statusTF;

    private int location_id;
    private int voiture_id;
    private int user_id;

    private LocationVoitureService lvs = new LocationVoitureService();

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
        typeTF.setValue(locationVoiture.getType());
        statusTF.setValue(String.valueOf(locationVoiture.getStatus()));
        
    }

    @FXML
    void AfficherLocations(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsLocation.fxml"));
            Parent root;
            root = loader.load();
            
            DetailsLocation controller = loader.getController();
            Location_Voiture currentLocation = lvs.getOne(location_id);
            controller.initialize(currentLocation);
            
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Location_Voiture locationVoiture = locationService.getOne(location_id);
        //set id
        locationVoiture.setVoiture_id(voiture_id);
        locationVoiture.setUser_id(user_id);

        locationVoiture.setPrix(Double.parseDouble(prixTF.getText()));
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
            AfficherLocations(event);
        }
    }

    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }
}

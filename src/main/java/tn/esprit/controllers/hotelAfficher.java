package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.models.hotel;
import tn.esprit.services.Hotelservices;

import java.io.IOException;

public class hotelAfficher {

    @FXML
    private Label nomLabel;

    @FXML
    private Label nbretoileLabel;

    @FXML
    private Label emplacementLabel;

    @FXML
    private Label avisLabel;

    private hotel selectedHotel;
    private Hotelservices hotelService = new Hotelservices();

    public void initialize() {
        afficherHotelSelectionne();
    }

    public void setSelectedHotel(hotel selectedHotel) {
        this.selectedHotel = selectedHotel;
        afficherHotelSelectionne();
    }

    private void afficherHotelSelectionne() {
        if (selectedHotel != null) {
            nomLabel.setText(selectedHotel.getNom());
            nbretoileLabel.setText(selectedHotel.getNbretoile());
            emplacementLabel.setText(selectedHotel.getEmplacement());
            avisLabel.setText(selectedHotel.getAvis());
        }
    }

    @FXML
    private void supprimerHotel() {
        if (selectedHotel != null) {
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation de suppression");
            confirmationDialog.setHeaderText("Voulez-vous vraiment supprimer cet hôtel ?");
            confirmationDialog.setContentText("Cette action est irréversible.");

            confirmationDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    hotelService.delete(selectedHotel);
                    afficherHotelSupprimeAvecSucces();
                    redirectToHotelList();
                }
            });
        } else {
            afficherAucunHotelSelectionneAlert();
        }
    }

    private void afficherHotelSupprimeAvecSucces() {
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Suppression réussie");
        successAlert.setHeaderText(null);
        successAlert.setContentText("L'hôtel a été supprimé avec succès.");
        successAlert.showAndWait();
    }

    private void afficherAucunHotelSelectionneAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aucun hôtel sélectionné");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner un hôtel à supprimer.");
        alert.showAndWait();
    }

    private void redirectToHotelList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/hotelList.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) nomLabel.getScene().getWindow(); // Obtenez la fenêtre actuelle
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement de la page hotelList.fxml
        }
    }






    @FXML
    private void updateHotel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/hotelModifier.fxml"));
            Parent root = loader.load();

            // Obtenez une référence vers le contrôleur hotelModifier
            hotelModifier controller = loader.getController();

            // Appelez initData avec les données de l'hôtel sélectionné
            controller.initData(selectedHotel);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement de la page hotelModifier.fxml
        }
    }





}

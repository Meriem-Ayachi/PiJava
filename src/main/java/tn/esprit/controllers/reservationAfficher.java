package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.models.Reservation;
import tn.esprit.models.hotel;
import tn.esprit.services.Reservationservices;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class reservationAfficher {

    @FXML
    private Label dateDepartLabel;

    @FXML
    private Label dateRetourLabel;

    @FXML
    private Label classeLabel;

    @FXML
    private Label destinationDepartLabel;

    @FXML
    private Label destinationRetourLabel;

    @FXML
    private Label nbrPersonnesLabel;

    private Reservation selectedReservation;
    private Reservationservices reservationService = new Reservationservices() {
        @Override
        public List<hotel> rechercherParNom(String nom) {
            return null;
        }

        @Override
        public void delete(Reservation reservation) {

        }

        @Override
        public void delete(int id) {

        }
    };

    public void initialize() {
        afficherReservationSelectionne();
    }

    public void setSelectedReservation(Reservation selectedReservation) {
        this.selectedReservation = selectedReservation;
        afficherReservationSelectionne();
    }

    @FXML
    private void updateReservation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/reservationModifier.fxml"));
            Parent root = loader.load();

            // Obtenez une référence vers le contrôleur reservationModifier
            reservationModifier controller = loader.getController();

            // Appelez initData avec les données de la réservation sélectionnée
            controller.initData(selectedReservation);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement de la page reservationModifier.fxml
        }
    }

    @FXML
    private void afficherReservationSelectionne() {
        if (selectedReservation != null) {
            dateDepartLabel.setText(selectedReservation.getDatedepart());
            dateRetourLabel.setText(selectedReservation.getDateretour());
            classeLabel.setText(selectedReservation.getClasse());
            destinationDepartLabel.setText(selectedReservation.getDestinationdepart());
            destinationRetourLabel.setText(selectedReservation.getDestinationretour());
            nbrPersonnesLabel.setText(String.valueOf(selectedReservation.getNbrdepersonne()));
        }
    }

    @FXML
    private void supprimerReservation() {
        if (selectedReservation != null) {
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation de suppression");
            confirmationDialog.setHeaderText("Voulez-vous vraiment supprimer cette réservation ?");
            confirmationDialog.setContentText("Cette action est irréversible.");

            Optional<ButtonType> result = confirmationDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                reservationService.delete(selectedReservation);
                afficherReservationSupprimeeAvecSucces();
                fermerFenetre();
            }
        } else {
            afficherAucuneReservationSelectionneeAlert();
        }
    }


    private void afficherReservationSupprimeeAvecSucces() {
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Suppression réussie");
        successAlert.setHeaderText(null);
        successAlert.setContentText("La réservation a été supprimée avec succès.");
        successAlert.showAndWait();
    }

    private void afficherAucuneReservationSelectionneeAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aucune réservation sélectionnée");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner une réservation à supprimer.");
        alert.showAndWait();
    }

    @FXML
    private void fermerFenetre() {
        Stage stage = (Stage) dateDepartLabel.getScene().getWindow();
        stage.close();
    }
}

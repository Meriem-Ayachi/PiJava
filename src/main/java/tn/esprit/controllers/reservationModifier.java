package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Reservation;
import tn.esprit.models.hotel;
import tn.esprit.services.Reservationservices;

import java.util.List;

public class reservationModifier {

    @FXML
    private TextField dateDepartTextField;

    @FXML
    private TextField dateRetourTextField;

    @FXML
    private TextField classeTextField;

    @FXML
    private TextField destinationDepartTextField;

    @FXML
    private TextField destinationRetourTextField;

    @FXML
    private TextField nbrPersonnesTextField;
    private Stage stage;

    private Reservation reservationToModify;
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

    public void initData(Reservation selectedReservation) {
        reservationToModify = selectedReservation;
        afficherDonneesInitiales();
        this.stage=stage;
    }

    private void afficherDonneesInitiales() {
        if (reservationToModify != null) {
            dateDepartTextField.setText(reservationToModify.getDatedepart());
            dateRetourTextField.setText(reservationToModify.getDateretour());
            classeTextField.setText(reservationToModify.getClasse());
            destinationDepartTextField.setText(reservationToModify.getDestinationdepart());
            destinationRetourTextField.setText(reservationToModify.getDestinationretour());
            nbrPersonnesTextField.setText(String.valueOf(reservationToModify.getNbrdepersonne()));
        }
    }

    @FXML
    void sauvegarderModification(ActionEvent event) {
        if (reservationToModify == null) {
            showAlert("Aucune réservation à modifier !");
            return;
        }

        // Récupérer les nouvelles valeurs des champs de texte
        String nouvelleDateDepart = dateDepartTextField.getText();
        String nouvelleDateRetour = dateRetourTextField.getText();
        String nouvelleClasse = classeTextField.getText();
        String nouvelleDestinationDepart = destinationDepartTextField.getText();
        String nouvelleDestinationRetour = destinationRetourTextField.getText();
        int nouveauNbrPersonnes = Integer.parseInt(nbrPersonnesTextField.getText());

        // Mettre à jour les valeurs de l'objet reservationToModify
        reservationToModify.setDatedepart(nouvelleDateDepart);
        reservationToModify.setDateretour(nouvelleDateRetour);
        reservationToModify.setClasse(nouvelleClasse);
        reservationToModify.setDestinationdepart(nouvelleDestinationDepart);
        reservationToModify.setDestinationretour(nouvelleDestinationRetour);
        reservationToModify.setNbrdepersonne(nouveauNbrPersonnes);

        // Appeler la méthode update de la classe ReservationService pour mettre à jour la réservation dans la base de données
        reservationService.update(reservationToModify);

        // Afficher un message de succès ou effectuer d'autres actions si nécessaire
        afficherMessageSucces();
    }

    private void afficherMessageSucces() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modification réussie");
        alert.setHeaderText(null);
        alert.setContentText("Les modifications ont été enregistrées avec succès !");
        alert.showAndWait();

        // Fermer la fenêtre après avoir affiché le message de succès
        fermerFenetre();
    }
    private void fermerFenetre() {
        // Récupérez le Stage associé à la fenêtre actuelle
        Stage stage = (Stage) dateDepartTextField.getScene().getWindow();
        // Fermez la fenêtre
        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Champs vides");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

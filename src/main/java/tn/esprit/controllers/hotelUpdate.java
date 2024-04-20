package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.models.Reservation;
import tn.esprit.models.hotel;
import tn.esprit.services.Hotelservices;
import javafx.event.ActionEvent;

import java.util.List;

public class hotelUpdate {

    @FXML
    private TextField Nomf ;

    @FXML
    private TextField nbetoilesf;

    @FXML
    private TextField emplacementf;

    @FXML
    private TextField avisf;

    // Instance du service de gestion des hôtels
    private final Hotelservices hotelservice = new Hotelservices() {
        @Override
        public void generatePDF(List<Reservation> reservations, String filePath) {

        }

        @Override
        public void delete(hotel hotel) {

        }
    };

    // Méthode appelée lors du clic sur le bouton de modification
    @FXML
    public void updateHotel(ActionEvent actionEvent) {
        // Créer un nouvel objet hotel pour stocker les modifications
        hotel hotelToUpdate = new hotel();

        // Récupérer les valeurs des champs de texte
        hotelToUpdate.setNom(Nomf.getText());
        hotelToUpdate.setEmplacement(emplacementf.getText());
        hotelToUpdate.setAvis(avisf.getText());

        try {
            // Convertir le nombre d'étoiles en entier
            int nbetoiles = Integer.parseInt(nbetoilesf.getText());
            // Définir le nombre d'étoiles dans l'objet hotelToUpdate
            hotelToUpdate.setNbretoile(String.valueOf(nbetoiles));
        } catch (NumberFormatException e) {
            // Afficher un message d'erreur si le nombre d'étoiles n'est pas un nombre valide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de saisie");
            alert.setContentText("Veuillez entrer un nombre valide pour le nombre d'étoiles.");
            alert.showAndWait();
            return;
        }

        // Appeler la méthode de mise à jour de l'hôtel dans le service
        hotelservice.update(hotelToUpdate);

        // Afficher un message de succès
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("Hôtel mis à jour avec succès!");
        alert.showAndWait();

        // Effacer les champs après la mise à jour
        Nomf.clear();
        emplacementf.clear();
        nbetoilesf.clear();
        avisf.clear();
    }
}

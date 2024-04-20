package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.models.Reservation;
import tn.esprit.models.hotel;
import tn.esprit.services.Hotelservices;

import java.awt.event.ActionEvent;
import java.util.List;

public class hotelDelete {

    @FXML
    private TextField hotelIdField;

    private final Hotelservices hotelservice = new Hotelservices() {
        @Override
        public void generatePDF(List<Reservation> reservations, String filePath) {

        }

        @Override
        public void delete(hotel hotel) {

        }
    };

    @FXML
    public void deleteHotel(ActionEvent actionEvent) {
        String hotelId = hotelIdField.getText();

        // Vérifiez si l'ID de l'hôtel est vide
        if (hotelId.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir l'ID de l'hôtel à supprimer.");
            alert.showAndWait();
            return;
        }

        // Convertissez l'ID de l'hôtel en un entier
        int id;
        try {
            id = Integer.parseInt(hotelId);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("L'ID de l'hôtel doit être un nombre entier.");
            alert.showAndWait();
            return;
        }

        // Créez un objet hotel avec l'ID spécifié
        hotel hotelToDelete = new hotel();
        hotelToDelete.setId(id);

        // Appelez la méthode de service pour supprimer l'hôtel
        hotelservice.delete(hotelToDelete);

        // Affichez un message de confirmation
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("L'hôtel a été supprimé avec succès!");
        alert.showAndWait();

        // Effacez le champ d'ID après la suppression
        hotelIdField.clear();
    }
}

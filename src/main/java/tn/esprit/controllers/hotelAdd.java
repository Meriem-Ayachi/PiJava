package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.esprit.models.Reservation;
import tn.esprit.models.hotel;
import tn.esprit.services.Hotelservices;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.List;

public class hotelAdd {
    @FXML
    private TextField Nomf;
    @FXML
    private ListView<hotel> listView;
    @FXML
    private TextField nbetoilesf;
    @FXML
    private TextField emplacementf;
    @FXML
    private TextField avisf;

    private final Hotelservices hotelservice = new Hotelservices() {
        @Override
        public void generatePDF(List<Reservation> reservations, String filePath) {

        }

        @Override
        public void delete(hotel hotel) {

        }
    };

    @FXML
    public void add(ActionEvent actionEvent) {
        hotel hotel1 = new hotel();

        hotel1.setNom(Nomf.getText());
        hotel1.setEmplacement(emplacementf.getText());
        hotel1.setAvis(avisf.getText());

        try {
            int nbetoiles = Integer.parseInt(nbetoilesf.getText());
            if (nbetoiles >= 1 && nbetoiles <= 5) { // Vérifie si le nombre d'étoiles est entre 1 et 5
                hotel1.setNbretoile(String.valueOf(nbetoiles));
            } else {
                throw new NumberFormatException(); // Lance une exception si le nombre d'étoiles est en dehors de la plage valide
            }
        } catch (NumberFormatException e) {
            System.out.println("La valeur du champ nbetoilesf n'est pas un nombre valide ou n'est pas entre 1 et 5.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de saisie");
            alert.setContentText("Veuillez entrer un nombre valide entre 1 et 5 pour le nombre d'étoiles.");
            alert.showAndWait();
            return;
        }

        hotelservice.add(hotel1);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("Hôtel ajouté avec succès!");
        alert.showAndWait();

        // Nettoyer les champs après l'ajout
        Nomf.clear();
        emplacementf.clear();
        nbetoilesf.clear();
        avisf.clear();
    }


    @FXML
    public void goToHotelList(ActionEvent event) {
        try {
            // Charger le fichier FXML de la liste des hôtels
            Parent root = FXMLLoader.load(getClass().getResource("/Fxml/hotelList.fxml"));
            // Modifier la racine de la scène actuelle pour afficher la liste des hôtels
            Nomf.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void deleteHotel(ActionEvent actionEvent) {
        hotel selectedHotel = listView.getSelectionModel().getSelectedItem();
        if (selectedHotel != null) {
            hotelservice.delete(selectedHotel);
            listView.getItems().remove(selectedHotel);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun hotel selectionne");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez selectionner un hotel a supprimer.");
            alert.showAndWait();
        }
    }
}

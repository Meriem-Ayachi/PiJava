package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import tn.esprit.models.hotel;
import tn.esprit.services.Hotelservices;

import java.io.IOException;

public class hotelAfficherF {

    public WebView webView;
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

    public void setSelectedHotelF(hotel selectedHotel) {
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

    private void redirectToHotelList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/hotelListF.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) nomLabel.getScene().getWindow(); // Obtenez la fenêtre actuelle
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement de la page hotelList.fxml
        }
    }

}

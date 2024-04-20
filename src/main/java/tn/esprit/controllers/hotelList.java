package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import tn.esprit.models.Reservation;
import tn.esprit.models.hotel;
import tn.esprit.services.Hotelservices;

import java.util.List;

public class hotelList {
    @FXML
    private ListView<hotel> listView;

    @FXML
    private Label nomid;

    @FXML
    private Label nbretoilesid;

    @FXML
    private Label emplacementid;

    @FXML
    private Label avisid;

    private final Hotelservices hotelservice = new Hotelservices() {
        @Override
        public void generatePDF(List<Reservation> reservations, String filePath) {

        }

        @Override
        public void delete(hotel hotel) {

        }
    };

    @FXML
    public void initialize() {
        List<hotel> hotels = Hotelservices.getAllHotels();
        assert hotels != null;
        ObservableList<hotel> hotelObservableList = FXCollections.observableList(hotels); // This line is causing the NullPointerException


        // Afficher les hôtels dans la ListView
        listView.setItems(hotelObservableList);

        // Définir un écouteur de changement de sélection pour afficher les détails de l'hôtel sélectionné
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                displayHotelDetails(newValue);
            }
        });
    }

    // Méthode pour afficher les détails de l'hôtel sélectionné
    private void displayHotelDetails(hotel hotel) {
        nomid.setText(hotel.getNom());
        nbretoilesid.setText(hotel.getNbretoile());
        emplacementid.setText(hotel.getEmplacement());
        avisid.setText(hotel.getAvis());
    }

    public void add(ActionEvent actionEvent) {
    }
}
package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.models.hotel;
import tn.esprit.services.Hotelservices;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class hotelListF {

    @FXML
    private ListView<hotel> hotelListView;

    @FXML
    private Label nomLabel;

    @FXML
    private Label nbretoileLabel;

    @FXML
    private Label emplacementLabel;

    @FXML
    private Label avisLabel;

    private boolean triAscendant = true;

    @FXML
    private TextField rechercheTextField;

    private Hotelservices hotelService = new Hotelservices();
    private ObservableList<hotel> hotels = FXCollections.observableArrayList();

    public void initialize() {
        // Charger les hôtels depuis le service
        hotels.addAll(hotelService.getAll());

        // Tri par nombre d'étoiles
        trierParNombreEtoiles(triAscendant);

        hotelListView.setItems(hotels);

        // Définir le comportement lorsque la sélection de la liste change
        hotelListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                afficherDetailsHotel(newValue);
            }
        });

        // Mettre en place la recherche par nom
        setupRecherche();
    }

    @FXML
    private void trierParEtoiles() {
        triAscendant = !triAscendant;
        trierParNombreEtoiles(triAscendant);
    }

    private void trierParNombreEtoiles(boolean ascendant) {
        List<hotel> sortedHotels = hotelService.getAll().stream()
                .sorted(ascendant ? Comparator.comparingInt(h -> Integer.parseInt(h.getNbretoile())) :
                        (h1, h2) -> Integer.parseInt(h2.getNbretoile()) - Integer.parseInt(h1.getNbretoile()))
                .collect(Collectors.toList());
        hotels.setAll(sortedHotels);
    }

    @FXML
    private void setupRecherche() {
        rechercheTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                // Si le champ de recherche est vide, afficher tous les hôtels
                hotelListView.setItems(hotels);
            } else {
                // Filtrer les hôtels selon le texte de recherche
                List<hotel> filteredHotels = hotelService.getAll().stream()
                        .filter(h -> h.getNom().toLowerCase().contains(newValue.toLowerCase()))
                        .collect(Collectors.toList());
                hotelListView.setItems(FXCollections.observableArrayList(filteredHotels));
            }
        });
    }

    private void afficherDetailsHotel(hotel hotel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/hotelAfficherF.fxml"));
            Parent root = loader.load();

            // Passer l'hôtel sélectionné au contrôleur de la vue hotelAfficherF.fxml
            hotelAfficherF controller = loader.getController();
            controller.setSelectedHotelF(hotel);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Ajouter votre logique d'affichage des détails de l'hôtel ici
    }

}

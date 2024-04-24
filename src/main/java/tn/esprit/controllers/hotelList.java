package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import tn.esprit.models.hotel;
import tn.esprit.services.Hotelservices;

import java.util.List;
import java.util.stream.Collectors;

public class hotelList {

    @FXML
    private ListView<hotel> hotelListView;

    @FXML
    private TextField rechercheTextField;

    private Hotelservices hotelService;

    public hotelList() {
        hotelService = new Hotelservices();
    }

    @FXML
    public void initialize() {
        // Charger les hôtels depuis le service
        List<hotel> hotels = hotelService.getAll();

        // Ajouter les hôtels à la liste de vue
        ObservableList<hotel> observableHotels = FXCollections.observableArrayList(hotels);
        hotelListView.setItems(observableHotels);

        // Ajouter un bouton "Afficher" devant chaque hôtel
        hotelListView.setCellFactory(param -> new ListCell<hotel>() {
            private final Button afficherButton = new Button("Afficher");

            @Override
            protected void updateItem(hotel item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(afficherButton);
                    setText(item.getNom()); // Modifier avec le texte approprié
                    afficherButton.setOnAction(event -> afficherDetailsHotel(item));
                }
            }
        });

        // Définir le comportement lorsque la sélection de la liste change
        hotelListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Mettre à jour les étiquettes avec les détails de l'hôtel sélectionné
                // Vous pouvez ajouter votre code ici si nécessaire
            }
        });

        // Mettre en place la fonctionnalité de recherche
        setupRecherche();
    }

    private void afficherDetailsHotel(hotel hotel) {
        // Ajouter votre logique d'affichage des détails de l'hôtel ici
    }

    private void setupRecherche() {
        rechercheTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                // Si le champ de recherche est vide, afficher tous les hôtels
                hotelListView.getItems().setAll(hotelService.getAll());
            } else {
                // Filtrer les hôtels selon le texte de recherche
                List<hotel> filteredHotels = hotelService.getAll().stream()
                        .filter(h -> h.getNom().toLowerCase().contains(newValue.toLowerCase()))
                        .collect(Collectors.toList());
                hotelListView.getItems().setAll(filteredHotels);
            }
        });
    }
}

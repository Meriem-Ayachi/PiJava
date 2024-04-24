package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.hotel;
import tn.esprit.services.Hotelservices;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class hotelList {

    @FXML
    private ListView<hotel> hotelListView;

    @FXML
    private Label nomid;

    @FXML
    private Label nbretoilesid;

    @FXML
    private Label emplacementid;

    @FXML
    private Label avisid;

    @FXML
    private TextField rechercheTextField; // Champ de recherche

    private Hotelservices hotelService;

    private hotel selectedHotel;

    public hotelList() {
        hotelService = new Hotelservices();
    }

    @FXML
    public void initialize() {
        // Charger les hôtels depuis le service
        List<hotel> hotels = hotelService.getAll();

        // Ajouter les hôtels à la liste de vue
        hotelListView.getItems().addAll(hotels);

        // Personnaliser l'affichage des cellules de la liste
        hotelListView.setCellFactory(param -> new ListCell<hotel>() {
            @Override
            protected void updateItem(hotel item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(null);

                    // Créer une HBox pour organiser les éléments horizontalement avec de l'espace entre eux
                    HBox container = new HBox();
                    container.setSpacing(300); // Ajouter de l'espace horizontal entre les éléments

                    // Créer une VBox pour organiser les éléments verticalement avec de l'espace entre eux
                    VBox infoContainer = new VBox();
                    infoContainer.setSpacing(5); // Ajouter de l'espace vertical entre les éléments

                    Label nomLabel = new Label("Nom: " + item.getNom());
                    Label nbreEtoilesLabel = new Label("Nombre d'étoiles: " + item.getNbretoile());
                    Label emplacementLabel = new Label("Emplacement: " + item.getEmplacement());
                    Label avisLabel = new Label("Avis: " + item.getAvis());

                    infoContainer.getChildren().addAll(nomLabel, nbreEtoilesLabel, emplacementLabel, avisLabel);

                    // Créer le bouton "Afficher" pour chaque hôtel
                    Button afficherButton = new Button("Afficher");
                    afficherButton.setOnAction(event -> afficherDetailsHotel(item));

                    // Ajouter la VBox et le bouton "Afficher" à la HBox
                   container.getChildren().addAll(infoContainer, afficherButton);

                    setGraphic(container);
                }
            }
        });

        // Définir le comportement lorsque la sélection de la liste change
        hotelListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Mettre à jour les étiquettes avec les détails de l'hôtel sélectionné
                nomid.setText(newValue.getNom());
                nbretoilesid.setText(newValue.getNbretoile());
                emplacementid.setText(newValue.getEmplacement());
                avisid.setText(newValue.getAvis());
                selectedHotel = newValue;
            }
        });

        // Ajouter un écouteur sur le champ de recherche
        rechercheTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            rechercherParNom(newValue);
        });
    }

    // Méthode pour afficher les détails de l'hôtel sélectionné
    private void afficherDetailsHotel(hotel hotel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/hotelAfficher.fxml"));
            Parent root = loader.load();

            // Passer l'hotel sélectionné au contrôleur de la vue hotelAfficher.fxml
            hotelAfficher controller = loader.getController();
            controller.setSelectedHotel(hotel);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour rechercher un hôtel par nom
    private void rechercherParNom(String nom) {
        List<hotel> hotels = hotelService.getAll();
        List<hotel> filteredHotels = hotels.stream()
                .filter(h -> h.getNom().toLowerCase().contains(nom.toLowerCase()))
                .collect(Collectors.toList());
        hotelListView.getItems().clear();
        hotelListView.getItems().addAll(filteredHotels);
    }
}

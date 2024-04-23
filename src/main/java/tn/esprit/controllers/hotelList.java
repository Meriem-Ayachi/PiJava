package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tn.esprit.models.hotel;
import tn.esprit.services.Hotelservices;

import java.io.IOException;
import java.util.List;

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

        // Ajouter un bouton "Afficher" devant chaque rendez-vous
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
                nomid.setText(newValue.getNom());
                nbretoilesid.setText(newValue.getNbretoile());
                emplacementid.setText(newValue.getEmplacement());
                avisid.setText(newValue.getAvis());
                selectedHotel = newValue;
            }
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
}

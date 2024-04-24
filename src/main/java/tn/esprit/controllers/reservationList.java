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
import tn.esprit.models.Reservation;
import tn.esprit.services.Reservationservices;

import java.io.IOException;
import java.util.List;

public class reservationList {

    @FXML
    private ListView<Reservation> reservationListView;

    @FXML
    private Label dateDepartLabel;

    @FXML
    private Label dateRetourLabel;

    @FXML
    private Label classeLabel;

    @FXML
    private Label destinationDepartLabel;

    @FXML
    private Label destinationRetourLabel;

    @FXML
    private Label nbrPersonnesLabel;

    private Reservationservices reservationService;

    private Reservation selectedReservation;

    public reservationList() {
        reservationService = new Reservationservices() {
            @Override
            public void delete(Reservation reservation) {

            }
        };
    }

    @FXML
    public void initialize() {
        // Charger les réservations depuis le service
        List<Reservation> reservations = reservationService.getAll();

        // Ajouter les réservations à la liste de vue
        reservationListView.getItems().addAll(reservations);

        // Définir la cellule personnalisée pour afficher les réservations
        reservationListView.setCellFactory(param -> new ListCell<Reservation>() {
            private final Button afficherButton = new Button("Afficher");

            @Override
            protected void updateItem(Reservation item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(afficherButton);
                    setText(item.getDatedepart());
                    afficherButton.setOnAction(event -> afficherDetailsReservation(item));
                }
            }
        });

        // Définir le comportement lorsque la sélection de la liste change
        reservationListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Mettre à jour les étiquettes avec les détails de la réservation sélectionnée
                dateDepartLabel.setText(newValue.getDatedepart());
                dateRetourLabel.setText(newValue.getDateretour());
                classeLabel.setText(newValue.getClasse());
                destinationDepartLabel.setText(newValue.getDestinationdepart());
                destinationRetourLabel.setText(newValue.getDestinationretour());
                nbrPersonnesLabel.setText(String.valueOf(newValue.getNbrdepersonne()));
                selectedReservation = newValue;
            }
        });
    }

    // Méthode pour afficher les détails de la réservation sélectionnée
    private void afficherDetailsReservation(Reservation reservation) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/reservationAfficher.fxml"));
            Parent root = loader.load();

            // Passer la réservation sélectionnée au contrôleur de la vue reservationAfficher.fxml
            reservationAfficher controller = loader.getController();
            controller.setSelectedReservation(reservation);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

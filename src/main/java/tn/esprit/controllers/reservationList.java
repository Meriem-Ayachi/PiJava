package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import tn.esprit.models.Reservation;
import tn.esprit.models.hotel;
import tn.esprit.services.Reservationservices;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

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

    @FXML
    private TextField rechercheDestinationTextField; // Champ de recherche par destination d'aller
    @FXML
    private TableView<rdv> rdvTableView;

    @FXML
    private Label moisLabel;

    @FXML
    private Label anneeLabel;

    @FXML
    private GridPane calendrierGridPane;

    private Reservationservices rdvService = new Reservationservices() {
        @Override
        public List<hotel> rechercherParNom(String nom) {
            return null;
        }

        @Override
        public void delete(Reservation reservation) {

        }
    };

    @FXML
    private int currentYear;
    @FXML
    private int currentMonth;


    private Reservationservices reservationService;

    private Reservation selectedReservation;

    public reservationList() {
        reservationService = new Reservationservices() {
            @Override
            public List<hotel> rechercherParNom(String nom) {
                return null;
            }

            @Override
            public void delete(Reservation reservation) {

            }
        };
    }
    private void afficherCalendrier(int year, int month) {
        calendrierGridPane.getChildren().clear();
        YearMonth yearMonth = YearMonth.of(year, month);
        int joursDansMois = yearMonth.lengthOfMonth();
        int jourDebutMois = yearMonth.atDay(1).getDayOfWeek().getValue(); // Jour de la semaine du premier jour du mois

        String[] joursSemaine = {"Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"};
        for (int i = 0; i < 7; i++) {
            Label labelJourSemaine = new Label(joursSemaine[i]);
            labelJourSemaine.setStyle("-fx-font-weight: bold");
            calendrierGridPane.add(labelJourSemaine, i, 0);
        }

        int row = 1;
        int jourCourant = 1;

        while (jourCourant <= joursDansMois) {
            for (int col = 0; col < 7; col++) {
                Button btnJour = new Button(Integer.toString(jourCourant));
                btnJour.setStyle("-fx-background-color: linear-gradient(#69bfa7, #b360ac); -fx-text-fill: #F2F2F2; -fx-font-size: 14px;");
                btnJour.setPrefWidth(40);
                btnJour.setPrefHeight(40);

                if (hasRendezVous(year, month, jourCourant)) {
                    Circle circle = new Circle(3, Color.RED); // Ajustez le rayon ici (par exemple, 2.5)
                    StackPane stack = new StackPane(btnJour, circle);
                    StackPane.setAlignment(circle, Pos.TOP_RIGHT);
                    calendrierGridPane.add(stack, col, row);
                } else {
                    calendrierGridPane.add(btnJour, col, row);
                }

                final int jourSelectionne = jourCourant;
                btnJour.setOnAction(event -> {
                    afficherRendezVousDuJour(year, month, jourSelectionne);
                });

                jourCourant++;

                if (jourCourant > joursDansMois) {
                    break;
                }
            }
            row++;
        }
    }

    private void afficherRendezVousDuJour(int annee, int mois, int jour) {
        // Construire la date sélectionnée
        LocalDate dateSelectionnee = LocalDate.of(annee, mois, jour);

        // Récupérer les rendez-vous pour la date sélectionnée
        List<rdv> rdvs = rdvService.getRdvByDate(dateSelectionnee);

        // Effacer la TableView actuelle
        rdvTableView.getItems().clear();

        // Afficher un message en fonction de la présence de rendez-vous
        if (rdvs.isEmpty()) {
            // Afficher une boîte de dialogue avec le message approprié
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aucun rendez-vous");
            alert.setHeaderText(null);
            alert.setContentText("Aucun rendez-vous n'est pris pour cette date.");
            alert.showAndWait();
        } else {
            // Ajouter les rendez-vous à la TableView
            rdvTableView.getItems().addAll(rdvs);
        }
    }

    @FXML
    private void moisPrecedent() {
        currentMonth--;
        if (currentMonth == 0) {
            currentMonth = 12;
            currentYear--;
        }
        afficherCalendrier(currentYear, currentMonth);
        updateMonthLabel(currentMonth);
        afficherRendezVous();
    }

    @FXML
    private void moisSuivant() {
        currentMonth++;
        if (currentMonth == 13) {
            currentMonth = 1;
            currentYear++;
        }
        afficherCalendrier(currentYear, currentMonth);
        updateMonthLabel(currentMonth);
        afficherRendezVous();
    }

    @FXML
    private void anneePrecedente() {
        currentYear--;
        afficherCalendrier(currentYear, currentMonth);
        updateYearLabel(currentYear);
        afficherRendezVous();
    }

    @FXML
    private void anneeSuivante() {
        currentYear++;
        afficherCalendrier(currentYear, currentMonth);
        updateYearLabel(currentYear);
        afficherRendezVous();
    }

    private boolean hasRendezVous(int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);
        List<reservation> rdvs = rdvService.getRdvByDate(date);
        return !rdvs.isEmpty();
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

        // Ajouter un écouteur sur le champ de recherche de destination d'aller
        rechercheDestinationTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            rechercherParDestination(newValue);
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

    // Méthode pour rechercher les réservations par destination d'aller
    private void rechercherParDestination(String destination) {
        List<Reservation> reservations = reservationService.getAll();
        List<Reservation> filteredReservations = reservations.stream()
                .filter(r -> r.getDestinationdepart().toLowerCase().contains(destination.toLowerCase()))
                .collect(Collectors.toList());
        reservationListView.getItems().clear();
        reservationListView.getItems().addAll(filteredReservations);
    }
}

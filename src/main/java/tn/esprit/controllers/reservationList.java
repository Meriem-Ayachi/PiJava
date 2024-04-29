package tn.esprit.controllers;

import javafx.collections.FXCollections;
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
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.event.MouseEvent;
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
    private Label moisAnneeLabel;

    @FXML
    private TextField rechercheDestinationTextField; // Champ de recherche par destination d'aller


    @FXML
    private Label moisLabel;

    @FXML
    private Label anneeLabel;

    @FXML
    private GridPane calendrierGridPane;

    private List<Reservation> reservationData;
    private YearMonth currentYearMonth;




    @FXML
    private int currentYear = LocalDate.now().getYear(); // Initialize currentYear
    @FXML
    private int currentMonth = LocalDate.now().getMonthValue(); // Initialize currentMonth


    private Reservationservices rdvService = new Reservationservices() {
        @Override
        public void generatePDF(List<Reservation> reservations, String filePath) {

        }

        @Override
        public List<hotel> rechercherParNom(String nom) {
            return null;
        }

        @Override
        public void delete(Reservation reservation) {


            
        }
    };




    private Reservationservices reservationService;

    private Reservation selectedReservation;

    public reservationList() {
        reservationService = new Reservationservices() {
            @Override
            public void generatePDF(List<Reservation> reservations, String filePath) {

            }

            @Override
            public List<hotel> rechercherParNom(String nom) {
                return null;
            }

            @Override
            public void delete(Reservation reservation) {

            }
        };
    }
    @FXML
    private void afficherCalendrier(int year, int month) {
        calendrierGridPane.getChildren().clear();

        // Logique pour obtenir le nombre de jours dans le mois et le jour de la semaine où commence le mois
        YearMonth yearMonth = YearMonth.of(year, month);
        int joursDansMois = yearMonth.lengthOfMonth();
        int jourDebutMois = yearMonth.atDay(1).getDayOfWeek().getValue(); // Jour de la semaine du premier jour du mois

        // Labels pour les jours de la semaine
        String[] joursSemaine = {"Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"};
        for (int i = 0; i < 7; i++) {
            Label labelJourSemaine = new Label(joursSemaine[i]);
            labelJourSemaine.setStyle("-fx-font-weight: bold");
            calendrierGridPane.add(labelJourSemaine, i, 0);
        }

        // Ajout des boutons pour les jours du mois
        int row = 1;
        int col = jourDebutMois;
        int jourCourant = 1;
        while (jourCourant <= joursDansMois) {
            for (int i = col; i < 7 && jourCourant <= joursDansMois; i++) {
                Button btnJour = new Button(Integer.toString(jourCourant));
                // Style et taille des boutons
                btnJour.setStyle("-fx-background-color: linear-gradient(#69bfa7, #18593b); -fx-text-fill: #F2F2F2; -fx-font-size: 14px;");
                btnJour.setPrefWidth(40);
                btnJour.setPrefHeight(40);

                // Vérifier si ce jour contient des réservations
                LocalDate date = LocalDate.of(year, month, jourCourant);
                if (hasRendezVous(year, month, jourCourant)) {
                    // Si oui, ajouter un cercle rouge sur le bouton
                    Circle circle = new Circle(4, Color.RED);
                    StackPane stackPane = new StackPane(btnJour, circle);
                    StackPane.setAlignment(circle, Pos.TOP_RIGHT);
                    calendrierGridPane.add(stackPane, i, row);
                } else {
                    // Si non, ajouter juste le bouton
                    calendrierGridPane.add(btnJour, i, row);
                }

                // Gestionnaire d'événements pour chaque bouton
                final int jourSelectionne = jourCourant;
                btnJour.setOnAction(event -> {
                    // Méthode pour afficher les rendez-vous du jour sélectionné
                    afficherRendezVousDuJour(year, month, jourSelectionne);
                });

                jourCourant++;
            }
            row++;
            col = 0; // Réinitialiser la colonne à 0 pour la prochaine ligne
        }

        // Mettre à jour le label du mois et de l'année
        moisLabel.setText(yearMonth.getMonth().toString());
        anneeLabel.setText(Integer.toString(year));
    }




    @FXML
    private void afficherRendezVousDuJour(int annee, int mois, int jour) {
        // Construire la date sélectionnée
        LocalDate dateSelectionnee = LocalDate.of(annee, mois, jour);

        // Effacer la ListView actuelle
        reservationListView.getItems().clear();

        // Récupérer les rendez-vous pour la date sélectionnée
        List<Reservation> rdvs = rdvService.getReservationByDate(dateSelectionnee);

        // Afficher un message en fonction de la présence de rendez-vous
        if (rdvs.isEmpty()) {
            // Afficher une boîte de dialogue avec le message approprié
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aucune reservation");
            alert.setHeaderText(null);
            alert.setContentText("Aucune reservation n'est pris pour cette date.");
            alert.showAndWait();
        } else {
            // Ajouter les rendez-vous à la ListView
            reservationListView.getItems().addAll(rdvs);
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
        List<Reservation> rdvs = rdvService.getReservationByDate(date);
        return !rdvs.isEmpty();
}



    @FXML
    public void initialize() {
        // Charger les réservations depuis le service
        List<Reservation> reservations = reservationService.getAll();
        reservationData = reservationService.getAll();
        reservationListView.setItems(FXCollections.observableArrayList(reservationData));

        afficherCalendrier(currentYear, currentMonth);
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

        afficherCalendrier(LocalDate.now().getYear(), LocalDate.now().getMonthValue());

        setCurrentMonth();
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



    private void updateMonthLabel(int month) {
        String[] months = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
        moisLabel.setText(months[month - 1]);
    }

    // Mettez à jour l'étiquette de l'année avec l'année sélectionnée
    private void updateYearLabel(int year) {
        anneeLabel.setText(Integer.toString(year));
    }


    private void afficherRendezVous() {
        // Vider la liste des éléments de la ListView
       reservationListView
.getItems().clear();

        // Récupérer la nouvelle liste des rendez-vous
        List<Reservation> reservations = reservationService.getAll();

        // Ajouter les nouveaux éléments à la ListView
        reservationListView
.getItems().addAll(reservations);
    }


    @FXML
    private void handlePreviousMonth(MouseEvent event) {
        currentYearMonth = currentYearMonth.minusMonths(1);
        loadReservationsForCurrentMonth();
    }

    @FXML
    private void handleNextMonth(MouseEvent event) {
        currentYearMonth = currentYearMonth.plusMonths(1);
        loadReservationsForCurrentMonth();
    }

    private void setCurrentMonth() {
        currentYearMonth = YearMonth.now();
        loadReservationsForCurrentMonth();
    }

    private void loadReservationsForCurrentMonth() {
        reservationData.clear();
        reservationData.addAll(reservationService.getReservationsForMonth(currentYearMonth));
    }

}

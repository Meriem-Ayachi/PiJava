package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.models.hotel;
import tn.esprit.services.Hotelservices;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class hotelList {

    public TextField rechercherParNom;
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
    @FXML
    private TextField setupRecherche;
    @FXML
    private void afficherStatistiques(ActionEvent actionEvent) {
        calculerEtAfficherStatistiques();
    }

    private Hotelservices hotelService = new Hotelservices();
    private ObservableList<hotel> hotels = FXCollections.observableArrayList();

    public void initialize() {
        // Charger les hôtels depuis le service
        hotels.addAll(hotelService.getAll());

        // Tri par nombre d'étoiles

        hotelListView.setItems(hotels);

        // Définir le comportement lorsque la sélection de la liste change
        hotelListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                afficherDetailsHotel(newValue);
            }
        });

        // Afficher les statistiques
        afficherStatistiques();

        // Mettre en place la recherche par nom
        setupRecherche();
    }


    private void afficherStatistiques() {
    }
    @FXML
    private void trierParEtoiles(ActionEvent actionEvent) {
        triAscendant = !triAscendant;
        trierParNombreEtoiles(triAscendant);
    }

    private void trierParNombreEtoiles(boolean ascendant) {
        List<hotel> hotels = hotelService.getAll();
        if (ascendant) {
            hotels.sort(Comparator.comparingInt(h -> Integer.parseInt(h.getNbretoile())));
        } else {
            hotels.sort((h1, h2) -> Integer.parseInt(h2.getNbretoile()) - Integer.parseInt(h1.getNbretoile()));
        }
        hotelListView.getItems().clear();
        hotelListView.getItems().addAll(hotels);
    }


    @FXML
    private void calculerEtAfficherStatistiques() {
        List<hotel> hotels = hotelService.getAll();
        int totalHotels = hotels.size();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Parcourir les hôtels et compter le nombre d'hôtels pour chaque nombre d'étoiles
        int[] nombreEtoiles = new int[6]; // Un tableau pour stocker le nombre d'hôtels pour chaque nombre d'étoiles (de 1 à 5)
        for (hotel h : hotels) {
            int nbreEtoiles = Integer.parseInt(h.getNbretoile());
            if (nbreEtoiles >= 1 && nbreEtoiles <= 5) {
                nombreEtoiles[nbreEtoiles]++;
            }
        }

        // Ajouter les données au graphique camembert
        for (int i = 1; i <= 5; i++) {
            int hotelsPourCetteEtoile = nombreEtoiles[i];
            double pourcentage = (double) hotelsPourCetteEtoile / totalHotels * 100;
            pieChartData.add(new PieChart.Data(hotelsPourCetteEtoile + " hôtels - " + i + " étoiles", hotelsPourCetteEtoile));
        }

        // Afficher les statistiques dans le graphique camembert
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Statistiques des hôtels par nombre d'étoiles");
        pieChart.setLegendVisible(true);
        pieChart.setLabelsVisible(true);

        // Créer une nouvelle fenêtre pour afficher le graphique camembert
        javafx.stage.Stage stage = new javafx.stage.Stage();
        javafx.scene.Scene scene = new javafx.scene.Scene(pieChart);
        stage.setScene(scene);
        stage.setTitle("Statistiques des hôtels");
        stage.show();
    }


    @FXML
    private void setupRecherche() {
        setupRecherche.textProperty().addListener((observable, oldValue, newValue) -> {
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
        // Ajouter votre logique d'affichage des détails de l'hôtel ici
    }
}

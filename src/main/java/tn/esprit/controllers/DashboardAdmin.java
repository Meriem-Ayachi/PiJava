package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.models.Reclamation;
import tn.esprit.models.Vols;
import tn.esprit.services.OffresService;
import tn.esprit.services.ReclamationService;
import tn.esprit.models.hotel;
import tn.esprit.services.Hotelservices;
import tn.esprit.services.VolService;
import tn.esprit.util.MaConnexion;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardAdmin implements Initializable {

    private final ReclamationService rs = new ReclamationService();
    private final Hotelservices hotelService = new Hotelservices();
    private final VolService volService = new VolService(){
        
    };
    private final OffresService offreService = new OffresService();

    @FXML
    private AnchorPane rootPane;

    @FXML
    private PieChart pieChartFlights;

    @FXML
    private PieChart pieChartOffres;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherDashboardReclamations();
        afficherDashboardHotels();
        afficherDashboardFlights(); // Appeler la méthode pour afficher le dashboard des vols
        afficherDashboardOffres();
    }

    private void afficherDashboardReclamations() {
        List<Reclamation> reclamations = rs.getAll();
        int totalReclamations = reclamations.size();
        int reclamationsTraitees = 0;
        int reclamationsNonTraitees = 0;

        // Parcourir les réclamations et compter le nombre de réclamations traitées et non traitées
        for (Reclamation r : reclamations) {
            byte estTraite = r.getEst_traite();
            if (estTraite == 1) {
                reclamationsTraitees++;
            } else {
                reclamationsNonTraitees++;
            }
        }

        // Créer les données pour le graphique en barres
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Statistiques des réclamations");
        series.getData().add(new XYChart.Data<>("Réclamations traitées", reclamationsTraitees));
        series.getData().add(new XYChart.Data<>("Réclamations non traitées", reclamationsNonTraitees));

        barChart.getData().add(series);

        // Vérifier si les données de la série sont non nulles avant de modifier le style des barres
        if (!series.getData().isEmpty()) {
            // Définir la couleur des barres
            for (XYChart.Data<String, Number> data : series.getData()) {
                Node node = data.getNode();
                if (node != null) {
                    if (data.getXValue().equals("Réclamations traitées")) {
                        node.setStyle("-fx-bar-fill: #27a227;");
                    } else {
                        node.setStyle("-fx-bar-fill: #ff1a1a;");
                    }
                }
            }
        }

        // Afficher les statistiques dans le graphique en barres
        barChart.setTitle("Statistiques des réclamations");
        xAxis.setLabel("Type de réclamation");
        yAxis.setLabel("Nombre de réclamations");

        barChart.setPrefSize(400, 300);

        // Ajouter le graphique au rootPane
        AnchorPane.setTopAnchor(barChart, 100.0);
        AnchorPane.setLeftAnchor(barChart, 50.0);
        rootPane.getChildren().add(barChart);
    }

    private void afficherDashboardHotels() {
        PieChart pieChart = new PieChart();
        pieChart.setTitle("Statistiques des hôtels par nombre d'étoiles");
        pieChart.setLegendVisible(true);
        pieChart.setLabelsVisible(true);

        pieChart.setPrefSize(400, 300);


        // Ajouter le graphique au rootPane
        AnchorPane.setTopAnchor(pieChart, 100.0);
        AnchorPane.setLeftAnchor(pieChart, 500.0);
        rootPane.getChildren().add(pieChart);

        afficherStatistiquesHotels(pieChart);
    }

    private void afficherStatistiquesHotels(PieChart pieChart) {
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

        // Ajouter les données au graphique camembert
        pieChart.setData(pieChartData);
    }


    private void afficherDashboardFlights() {
        List<Vols> flights = volService.getAll();
        int totalFlights = flights.size();
        int economyCount = 0;
        int businessCount = 0;
        int firstClassCount = 0;

        for (Vols flight : flights) {
            String flightClass = flight.getClasse();
            if (flightClass.equals("Economy")) {
                economyCount++;
            } else if (flightClass.equals("Business")) {
                businessCount++;
            } else if (flightClass.equals("First")) {
                firstClassCount++;
            }
        }

        double economyPercentage = (double) economyCount / totalFlights * 100;
        double businessPercentage = (double) businessCount / totalFlights * 100;
        double firstClassPercentage = (double) firstClassCount / totalFlights * 100;

        pieChartFlights.getData().addAll(
                new PieChart.Data("Economy (" + String.format("%.1f", economyPercentage) + "%)", economyCount),
                new PieChart.Data("Business (" + String.format("%.1f", businessPercentage) + "%)", businessCount),
                new PieChart.Data("First Class (" + String.format("%.1f", firstClassPercentage) + "%)", firstClassCount)
        );
    }

    private void afficherDashboardOffres() {
        try {
            String query = "SELECT title,COUNT(id) AS number_of_offres FROM offres GROUP BY title;";
            Statement st = MaConnexion.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                pieChartOffres.getData().add(new PieChart.Data("title:" + rs.getString("title"), rs.getInt("number_of_offres")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
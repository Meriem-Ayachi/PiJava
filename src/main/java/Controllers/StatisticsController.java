package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import tn.esprit.models.Vols;
import tn.esprit.services.VolService;

import java.util.List;

public class StatisticsController {

    @FXML
    private PieChart pieChart;

    private final VolService volService = new VolService(){};

    public void initialize() {
        // Retrieve flight data from the database
        List<Vols> flights = volService.getAll();

        // Analyze flight data and prepare statistics
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

        // Create data for the pie chart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Economy", economyCount),
                new PieChart.Data("Business", businessCount),
                new PieChart.Data("First Class", firstClassCount)
        );

        // Set the data to the pie chart
        pieChart.setData(pieChartData);
    }
}

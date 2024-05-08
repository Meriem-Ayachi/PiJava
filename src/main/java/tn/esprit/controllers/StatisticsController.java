package tn.esprit.controllers;

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

        // Calculate percentages
        double economyPercentage = (double) economyCount / totalFlights * 100;
        double businessPercentage = (double) businessCount / totalFlights * 100;
        double firstClassPercentage = (double) firstClassCount / totalFlights * 100;

        // Create data for the pie chart with percentages
        pieChart.getData().addAll(
                new PieChart.Data("Economy (" + String.format("%.1f", economyPercentage) + "%)", economyCount),
                new PieChart.Data("Business (" + String.format("%.1f", businessPercentage) + "%)", businessCount),
                new PieChart.Data("First Class (" + String.format("%.1f", firstClassPercentage) + "%)", firstClassCount)
        );
    }
}
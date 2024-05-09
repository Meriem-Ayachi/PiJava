package tn.esprit.controllers.locationVoiture;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.util.voitureApi;

public class rechercheVoiture {

    @FXML
    private ComboBox<Long> AnneeTF;

    @FXML
    private Label Capacity;

    @FXML
    private Label Energy;

    @FXML
    private ComboBox<String> MarqueTF;

    @FXML
    private ComboBox<String> ModelTF;

    @FXML
    private Text uploadedFileName;

    private String capacity,energy;

    void initialize(){
        ObservableList<Long> yearsList = FXCollections.observableArrayList();
        long startYear = 1950; 
        long currentYear = 2023; 
        
        for (long year = startYear; year <= currentYear; year++) {
            yearsList.add(year);
        }
        
        AnneeTF.setItems(yearsList);
    }

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void confirm(ActionEvent event) {
        String marque,model;
        marque = MarqueTF.getValue();
        model = ModelTF.getValue();

        if (capacity.isEmpty() || capacity == null) {
            afficherErreur("Veuillez saisir une couleur.");
            return;
        }
        if (energy.isEmpty() || energy == null) {
            afficherErreur("Veuillez saisir une couleur.");
            return;
        }
        if (model.isEmpty() || model == null) {
            afficherErreur("Veuillez saisir une couleur.");
            return;
        }
        if (marque.isEmpty() || marque == null) {
            afficherErreur("Veuillez saisir une couleur.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterVoiture.fxml"));
            AnchorPane rechercheVoitureAnchorPane = loader.load();
            AjouterVoiture controller = loader.getController();
    
            // Appeler la méthode pour initialiser les détails de la réclamation
            controller.initialize();
            controller.fillInputs(marque, model, energy, capacity);
    
            // Afficher l'interface dans une nouvelle fenêtre
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
    
            stage.setScene(new Scene(rechercheVoitureAnchorPane));
            stage.show();
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void marqueSelected(ActionEvent event) {
        List<String> models= voitureApi.getModelFromMarque(MarqueTF.getValue(), AnneeTF.getValue().intValue());
        
        ModelTF.getItems().clear();
        Capacity.setText("Capacité: ");
        Energy.setText("Energy: ");
        capacity = "";
        energy = "";
        
        ObservableList<String> modelList = FXCollections.observableArrayList();
        
        for (String model: models) {
            modelList.add(model);
        }
        
        ModelTF.setItems(modelList);
    }

    @FXML
    void modelSelected(ActionEvent event) {
        List<String> fuelAndSeats= voitureApi.getFuelAndSeats(MarqueTF.getValue(), ModelTF.getValue(), AnneeTF.getValue().intValue());
        
        String seats = fuelAndSeats.get(0);
        String fuel = fuelAndSeats.get(1);

        Capacity.setText("Capacité: " + seats);        
        Energy.setText("Energy: " + fuel);
        capacity = seats;
        energy = fuel;
    }

    @FXML
    void yearSelected(ActionEvent event) {
        List<String> marques= voitureApi.getMarqueOfYear(AnneeTF.getValue().intValue());
        
        MarqueTF.getItems().clear();
        ModelTF.getItems().clear();
        Capacity.setText("Capacité: ");
        Energy.setText("Energy: ");
        capacity = "";
        energy = "";

        ObservableList<String> marqueList = FXCollections.observableArrayList();
        
        for (String marque: marques) {
            marqueList.add(marque);
        }
        
        MarqueTF.setItems(marqueList);
    }

    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }
}

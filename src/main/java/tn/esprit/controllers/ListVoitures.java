package tn.esprit.controllers;

import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.MainFX;
import tn.esprit.models.Voiture;
import tn.esprit.services.VoitureService;
import tn.esprit.util.Navigator;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;


public class ListVoitures {
    @FXML
    private TableView<Voiture> voitureTableView;

    private Voiture selectedVoiture;
    
    private final VoitureService voitureService = new VoitureService();

    @FXML
    void initialize() {
        TableColumn<Voiture, String> marqueColumn = new TableColumn<>("Marque");
        TableColumn<Voiture, String> modelColumn = new TableColumn<>("Model");
        TableColumn<Voiture, String> couleurColumn = new TableColumn<>("Couleur");
        TableColumn<Voiture, String> energyColumn = new TableColumn<>("Energy");
        TableColumn<Voiture, String> capaciteColumn = new TableColumn<>("Capacite");
        
        marqueColumn.setCellValueFactory(new PropertyValueFactory<>("marque"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        couleurColumn.setCellValueFactory(new PropertyValueFactory<>("Couleur"));
        energyColumn.setCellValueFactory(new PropertyValueFactory<>("Energy"));
        capaciteColumn.setCellValueFactory(new PropertyValueFactory<>("Capacite"));
        
        // add columns
        List<TableColumn<Voiture, ?>> columns = Arrays.asList(marqueColumn, modelColumn, couleurColumn, energyColumn, capaciteColumn);
        voitureTableView.getColumns().addAll(columns);

        // fill table
        refreshTable();
    }


    @FXML
    void goToAjouter(ActionEvent event){
        Stage stage = MainFX.getPrimaryStage();
        Navigator nav = new Navigator(stage);
        nav.goToPage("/AjouterVoiture.fxml");
    }

    @FXML
    void supprimerSelectedVoiture() {
        if (selectedVoiture != null) {
            voitureService.delete(selectedVoiture.getId());
            refreshTable();
            selectedVoiture = null;
        }else{
            showError("Vous devez sélectionner une voiture");
        }
    }

    @FXML
    void goToModifier(ActionEvent event) {
        if (selectedVoiture != null) {
            Voiture voiture = voitureService.getOne(selectedVoiture.getId());
            
            Stage stage = MainFX.getPrimaryStage();
            Navigator nav = new Navigator(stage);
            nav.goTo_ModifierVoiture("/ModifierVoiture.fxml", voiture);
        }else{
            showError("Vous devez sélectionner une voiture");
        }
    }

    @FXML
    void onTableRowClicked() {
        selectedVoiture = voitureTableView.getSelectionModel().getSelectedItem();
    }

    private void refreshTable() {
        voitureTableView.setItems(FXCollections.observableArrayList(voitureService.getAll()));
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

}

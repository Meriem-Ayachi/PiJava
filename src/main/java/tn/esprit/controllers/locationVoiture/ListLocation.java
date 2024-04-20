package tn.esprit.controllers.locationVoiture;

import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.MainFX;
import tn.esprit.models.Location_Voiture;
import tn.esprit.services.LocationVoitureService;
import tn.esprit.util.Navigator;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;


public class ListLocation {
    @FXML
    private TableView<Location_Voiture> locationTableView;

    private Location_Voiture selectedLocation;
    
    private final LocationVoitureService LocationService = new LocationVoitureService();

    @FXML
    void initialize() {
        TableColumn<Location_Voiture, String> prixColumn = new TableColumn<>("Prix");
        TableColumn<Location_Voiture, String> data_debutColumn = new TableColumn<>("data debut");
        TableColumn<Location_Voiture, String> date_finColumn = new TableColumn<>("date fin");
        TableColumn<Location_Voiture, String> typeColumn = new TableColumn<>("type");
        TableColumn<Location_Voiture, String> statusColumn = new TableColumn<>("status");
        TableColumn<Location_Voiture, String> voitureColumn = new TableColumn<>("voiture");
        TableColumn<Location_Voiture, String> userColumn = new TableColumn<>("user");

        prixColumn.setCellValueFactory(new PropertyValueFactory<>("Prix"));
        data_debutColumn.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        date_finColumn.setCellValueFactory(new PropertyValueFactory<>("datefin"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        voitureColumn.setCellValueFactory(new PropertyValueFactory<>("voiture_id"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        
        // add columns
        List<TableColumn<Location_Voiture, ?>> columns = Arrays.asList(
            prixColumn,
            data_debutColumn,
            date_finColumn,
            typeColumn,
            statusColumn,
            voitureColumn,
            userColumn
        );
        locationTableView.getColumns().addAll(columns);

        // fill table
        refreshTable();
    }


    @FXML
    void goToAjouter(ActionEvent event){
        Stage stage = MainFX.getPrimaryStage();
        Navigator nav = new Navigator(stage);
        nav.goToPage("/AjouterLocation.fxml");
    }

    @FXML
    void supprimerSelectedLocation() {
        if (selectedLocation != null) {
            LocationService.delete(selectedLocation.getId());
            refreshTable();
            selectedLocation = null;
        }else{
            showError("Vous devez sélectionner une location");
        }
    }

    @FXML
    void goToModifier(ActionEvent event) {
        if (selectedLocation != null) {
            Location_Voiture locationVoiture = LocationService.getOne(selectedLocation.getId());
            
            Stage stage = MainFX.getPrimaryStage();
            Navigator nav = new Navigator(stage);
            nav.goTo_ModifierLocation("/ModifierLocation.fxml", locationVoiture);
        }else{
            showError("Vous devez sélectionner une location");
        }
    }

    @FXML
    void onTableRowClicked() {
        selectedLocation = locationTableView.getSelectionModel().getSelectedItem();
    }

    private void refreshTable() {
        locationTableView.setItems(FXCollections.observableArrayList(LocationService.getAll()));
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

}

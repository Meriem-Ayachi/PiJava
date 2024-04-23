package tn.esprit.controllers.locationVoiture;

import java.util.Arrays;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.MainFX;
import tn.esprit.models.Location_Voiture;
import tn.esprit.models.Voiture;
import tn.esprit.models.session;
import tn.esprit.services.LocationVoitureService;
import tn.esprit.services.VoitureService;
import tn.esprit.util.Navigator;

public class userLocationList_reserver {
    
    @FXML
    private TableView<Location_Voiture> locationTableView;

    private Location_Voiture selectedLocation;
    
    private final LocationVoitureService LocationService = new LocationVoitureService();
    private final VoitureService voitureService = new VoitureService();

    @FXML
    void initialize() {
        TableColumn<Location_Voiture, String> prixColumn = new TableColumn<>("Prix");
        TableColumn<Location_Voiture, String> data_debutColumn = new TableColumn<>("data debut");
        TableColumn<Location_Voiture, String> date_finColumn = new TableColumn<>("date fin");
        TableColumn<Location_Voiture, String> typeColumn = new TableColumn<>("type");
        TableColumn<Location_Voiture, String> statusColumn = new TableColumn<>("status");
        TableColumn<Location_Voiture, String> voitureColumn = new TableColumn<>("voiture");

        prixColumn.setCellValueFactory(new PropertyValueFactory<>("Prix"));
        data_debutColumn.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        date_finColumn.setCellValueFactory(new PropertyValueFactory<>("datefin"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        voitureColumn.setCellValueFactory(cellData -> {
            int voitureId = cellData.getValue().getVoiture_id();
            Voiture voiture = voitureService.getOne(voitureId);
            String marque = voiture.getMarque();
            String modele = voiture.getModel();
            return new SimpleStringProperty(marque + "(" + modele + ")");
        });

        
        // add columns
        List<TableColumn<Location_Voiture, ?>> columns = Arrays.asList(
            prixColumn,
            data_debutColumn,
            date_finColumn,
            typeColumn,
            statusColumn,
            voitureColumn
        );
        locationTableView.getColumns().addAll(columns);

        // fill table
        refreshTable();
    }


    @FXML
    void DownloadPdf(ActionEvent event) {
        if (selectedLocation != null) {

        }else{
            showError("Vous devez sélectionner une location");
        }
    }

    @FXML
    void EmailPdf(ActionEvent event) {
        if (selectedLocation != null) {

        }else{
            showError("Vous devez sélectionner une location");
        }
    }

    @FXML
    void GoToLocationList(ActionEvent event) {
        Stage stage = MainFX.getPrimaryStage();
        Navigator nav = new Navigator(stage);
        nav.goToPage("/userLocationList.fxml");
    }

    @FXML
    void onTableRowClicked() {
        selectedLocation = locationTableView.getSelectionModel().getSelectedItem();
    }

    private void refreshTable() {
        int userid = session.id_utilisateur;
        List<Location_Voiture> location_Voiture_List = LocationService.getAll_UserId(userid);
        locationTableView.setItems(FXCollections.observableArrayList(location_Voiture_List));
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}

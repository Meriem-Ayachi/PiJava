package tn.esprit.controllers.locationVoiture;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import tn.esprit.MainFX;
import tn.esprit.interfaces.RefreshCallBack;
import tn.esprit.models.Location_Voiture;
import tn.esprit.models.User;
import tn.esprit.models.Voiture;
import tn.esprit.services.LocationVoitureService;
import tn.esprit.services.UserService;
import tn.esprit.services.VoitureService;
import tn.esprit.util.Navigator;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;


public class ListLocation implements RefreshCallBack{
    @FXML
    private TableView<Location_Voiture> locationTableView;

    private Location_Voiture selectedLocation;
    
    private final LocationVoitureService LocationService = new LocationVoitureService();
    private final VoitureService voitureService = new VoitureService();
    private final UserService userService = new UserService();

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
        voitureColumn.setCellValueFactory(cellData -> {
            int voitureId = cellData.getValue().getVoiture_id();
            Voiture voiture = voitureService.getOne(voitureId);
            String marque = voiture.getMarque();
            String modele = voiture.getModel();
            return new SimpleStringProperty(marque + "(" + modele + ")");
        });
        userColumn.setCellValueFactory(cellData -> {
            int userid = cellData.getValue().getUser_id();
            if (userid != 0){
                User user = userService.getOne(userid);
                return new SimpleStringProperty(user.getPrenom() + " " + user.getNom());
            }
            return new SimpleStringProperty("");
        });
        
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
    void onTableRowClicked() {
        try {
            selectedLocation = locationTableView.getSelectionModel().getSelectedItem();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsLocation.fxml"));
            AnchorPane detailsVoitureAnchorPane = loader.load();
            DetailsLocation controller = loader.getController();

            // Appeler la méthode pour initialiser les détails de la réclamation
            controller.initialize(selectedLocation);
            controller.initializeCallback(this);

            // Afficher l'interface dans une nouvelle fenêtre
            Stage stage = new Stage();

            stage.setScene(new Scene(detailsVoitureAnchorPane));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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

    @Override
    public void onRefreshComplete() {
        refreshTable();
    }
}

package tn.esprit.controllers.locationVoiture;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import tn.esprit.MainFX;
import tn.esprit.interfaces.RefreshCallBack;
import tn.esprit.models.Voiture;
import tn.esprit.services.VoitureService;
import tn.esprit.util.Navigator;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;


public class ListVoitures implements RefreshCallBack{
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
    void onTableRowClicked() {
        try {
            selectedVoiture = voitureTableView.getSelectionModel().getSelectedItem();
            selectedVoiture = voitureService.getOne(selectedVoiture.getId());
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsVoiture.fxml"));
            AnchorPane detailsVoitureAnchorPane = loader.load();
            DetailsVoiture controller = loader.getController();

            // Appeler la méthode pour initialiser les détails de la réclamation
            controller.initialize(selectedVoiture);
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
        voitureTableView.setItems(FXCollections.observableArrayList(voitureService.getAll()));
    }


    @Override
    public void onRefreshComplete() {
        refreshTable();
    }


}

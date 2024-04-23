package tn.esprit.controllers.locationVoiture;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.MainFX;
import tn.esprit.models.Location_Voiture;
import tn.esprit.models.Voiture;
import tn.esprit.models.session;
import tn.esprit.services.LocationVoitureService;
import tn.esprit.services.VoitureService;
import tn.esprit.util.Navigator;

public class userLocation_ShowVoiture {
    
    @FXML
    private Label prixOutput;

    @FXML
    private Label typeOutput;

    @FXML
    private Label marqueOutput;

    @FXML
    private Label modelOutput;

    @FXML
    private Label energyOutput;

    @FXML
    private Label capaciteOutput;

    @FXML
    private Label totalOutput;

    private int locationVoitureId = 0;    
    private final LocationVoitureService LocationService = new LocationVoitureService();
    private final VoitureService voitureService = new VoitureService();

    @FXML
    public void initialize(Location_Voiture location_Voiture) {
        Voiture voiture = voitureService.getOne(location_Voiture.getVoiture_id());
        locationVoitureId = location_Voiture.getId();

        prixOutput.setText(String.valueOf(location_Voiture.getPrix()));
        typeOutput.setText(location_Voiture.getType());
        marqueOutput.setText(voiture.getMarque());
        modelOutput.setText(voiture.getModel());
        energyOutput.setText(voiture.getEnergy());
        capaciteOutput.setText(String.valueOf(voiture.getCapacite()));
        
        //calculate the total price
        LocalDate startDate = location_Voiture.getDate_debut().toLocalDate();
        LocalDate endDate = location_Voiture.getDatefin().toLocalDate();

        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

        totalOutput.setText(String.valueOf(daysBetween * location_Voiture.getPrix()));
    }

    @FXML
    void goBack(ActionEvent event) {
        Stage stage = MainFX.getPrimaryStage();
        Navigator nav = new Navigator(stage);
        nav.goToPage("/userLocationList.fxml");
    }

    @FXML
    void reserver(ActionEvent event) {
        try {
            //confirmation message
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Are you sure ?");
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            if (result != ButtonType.OK) {
                return;
            }
    
            //reserve it
            Location_Voiture location_Voiture = LocationService.getOne(locationVoitureId);
            location_Voiture.setUser_id(session.id_utilisateur);
            location_Voiture.setStatus("réservé");
            LocationService.update(location_Voiture);
            
            //change to my reservation page
            Stage stage = MainFX.getPrimaryStage();
            Navigator nav = new Navigator(stage);
            nav.goToPage("/userLocationList_reserver.fxml");
        } catch (Exception e) {
            showError("Unexpected error");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
